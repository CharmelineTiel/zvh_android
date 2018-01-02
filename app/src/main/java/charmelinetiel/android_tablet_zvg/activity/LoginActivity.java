package charmelinetiel.android_tablet_zvg.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandling;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<User> {

    private APIService apiService;
    private TextView forgotPassword;
    private EditText email, password;
    private CheckBox autoLoginCheckBox;
    private FormErrorHandling validateForm;
    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Check if Auto-login has been configured
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean autoLogin = sharedPref.getBoolean("autoLogin", false);
        String emailAddress = sharedPref.getString("emailAddress", "");
        String password = sharedPref.getString("password", "");


        //Check if user has been saved, if so go to mainactivity
        if(autoLogin && !emailAddress.equals("") && !password.equals("")){
            Retrofit retrofit = RetrofitClient.getClient();
            apiService = retrofit.create(APIService.class);

            User user = new User();
            user.setEmailAddress(emailAddress);
            user.setPassword(password);

            apiService.login(user).enqueue(this);
        }else{
            Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        //Get the intent and data to determine if the activity was entered by deep link
        Intent intent = getIntent();
        data = intent.getData();

        //If the activity was entered by deep link, show the 'account activated' page
        if(data != null){

            setContentView(R.layout.activity_login_account_activated);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.account_activated);

            Button toLoginBtn = findViewById(R.id.toLoginBtn);
            toLoginBtn.setOnClickListener(view -> {
                //Refresh the activity to get to the login page
                Intent refresh = new Intent(this, LoginActivity.class);
                startActivity(refresh);
                this.finish();
            });
        //Else show the normal login page
        }else {


        autoLoginCheckBox = findViewById(R.id.checkbox_autoLogin);

        validateForm = new FormErrorHandling();

            setContentView(R.layout.activity_login);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.title_activity_login);

            forgotPassword = findViewById(R.id.iForgot);
            forgotPassword.setOnClickListener(this);

            Button btn = findViewById(R.id.loginBtn);
            btn.setOnClickListener(this);

            Button btn2 = findViewById(R.id.cancelBtn);
            btn2.setOnClickListener(this);

            autoLoginCheckBox = findViewById(R.id.checkbox_autoLogin);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginBtn:

                try {
                    User user = new User();
                    email = findViewById(R.id.username);
                    password = findViewById(R.id.password);

                    user.setPassword(password.getText().toString());
                    user.setEmailAddress(email.getText().toString());

                    if(user != null && validInput()) {

                        apiService.login(user).enqueue(this);
                    }
                }
                catch(Exception e){

                    Toast.makeText(this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelBtn:
                Intent Intent = new Intent(this, RegisterActivity.class);
                startActivity(Intent);
                break;

            case R.id.iForgot:
                Dialog dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                dialog.setContentView(R.layout.forgot_password_dialog);
                EditText forgotPasswordEmailInput = dialog.findViewById(R.id.forgotPasswordEmailInput);
                Button cancelForgotPassword = dialog.findViewById(R.id.cancel_forgot_password);

                cancelForgotPassword.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                Button sendForgotPasswordEmail = dialog.findViewById(R.id.send_forgot_password_email);
                sendForgotPasswordEmail.setOnClickListener(view -> {

                    if (validateForm.inputGiven(forgotPasswordEmailInput)) {

                        apiService.requestResetPasswordEmail(forgotPasswordEmailInput.getText().toString()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //TODO: toon message aan de hand van de response
                                if (response.body() != null && response.isSuccessful()) {

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        dialog.dismiss();

                    }else if(!validateForm.InputValidEmail(forgotPasswordEmailInput)){

                        validateForm.showError("Vul een geldige email in");
                    }
                });
                dialog.show();

                break;
        }

    }

    private boolean validInput(){

        if (!validateForm.inputGiven(email)) {
            validateForm.showError("Vul uw email in");
            return false;
        }else if(!validateForm.InputValidEmail(email)){

            validateForm.showError("Geen geldige email");
            return false;
        }
        if(!validateForm.inputGiven(password)){

            validateForm.showError("Vul uw wachtwoord in");
            return false;
        }
        return true;
    }
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.body() != null && response.isSuccessful()) {

            User loggedInUser = response.body();

            loggedInUser.setAuthToken(response.body().getAuthToken());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", loggedInUser);
            startActivity(intent);

            if (autoLoginCheckBox.isChecked()) {

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("autoLogin", true);
                editor.putString("emailAddress", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();
            }
        } else {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(this, jObjError.getString("error"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        Toast.makeText(this, "server error.. probeer het opnieuw",
                Toast.LENGTH_LONG).show();
    }

    public void openFragment(final Fragment fg)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fg, fg.toString());
        ft.commit();
    }

}
