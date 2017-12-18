package charmelinetiel.android_tablet_zvg.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.fragments.DiaryFragment;
import charmelinetiel.android_tablet_zvg.fragments.LoginOrRegisterFragment;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.models.authToken;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<User> {

    private APIService apiService;
    private User user;
    private EditText email, password;
    private CheckBox autoLoginCheckBox;
    private Fragment fg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = findViewById(R.id.loginBtn);
        btn.setOnClickListener(this);

        Button btn2 = findViewById(R.id.cancelBtn);
        btn2.setOnClickListener(this);

        autoLoginCheckBox = findViewById(R.id.checkbox_autoLogin);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginBtn:

                try {
                    user = new User();
                    email = findViewById(R.id.username);
                    password = findViewById(R.id.password);

                    user.setPassword(password.getText().toString());
                    user.setEmailAddress(email.getText().toString());
                    if(user != null) {
                        apiService.login(user).enqueue(this);

                    }
                }
                catch(Exception e){

                    Toast.makeText(this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelBtn:

                finish();
                break;

            case R.id.Iforgot:

                Dialog dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                dialog.setTitle("Too damn bad");
                dialog.show();

                break;
        }


    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if (response.body() != null && response.isSuccessful()){

            Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.MainActivity.class);
            intent.putExtra("user", response.body());
            startActivity(intent);

            authToken.getInstance().setAuthToken(response.body().getAuthToken());

            if(autoLoginCheckBox.isChecked()){
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("autoLogin", true);
                editor.putString("emailAddress", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();
            }
        }else{

            Toast.makeText(this, "inloggen niet gelukt, controleer alle velden",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        Toast.makeText(this, "server error.. probeer het opnieuw",
                Toast.LENGTH_LONG).show();
    }

}
