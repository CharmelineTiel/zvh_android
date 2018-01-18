package charmelinetiel.zorg_voor_het_hart.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<User>, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_SAVE = 1;
    private static final int RC_READ = 3;
    private static final String IS_RESOLVING = "is_resolving";
    private static final String IS_REQUESTING = "is_requesting";

    private APIService apiService;
    private TextView forgotPassword;
    private EditText email, password;
    private CheckBox autoLoginCheckBox;
    private FormErrorHandling validateForm;
    private Uri data;
    private ProgressBar progressBar;
    private ScrollView loginPage;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving;
    private boolean mIsRequesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(IS_RESOLVING);
            mIsRequesting = savedInstanceState.getBoolean(IS_REQUESTING);
        }

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        progressBar = findViewById(R.id.progressBar);
        loginPage = findViewById(R.id.loginPage);


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

        validateForm = new FormErrorHandling();

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.title_activity_login);

            email = findViewById(R.id.username);
            password = findViewById(R.id.password);

            forgotPassword = findViewById(R.id.iForgot);
            forgotPassword.setOnClickListener(this);

            Button btn = findViewById(R.id.loginBtn);
            btn.setOnClickListener(this);

            Button btn2 = findViewById(R.id.cancelBtn);
            btn2.setOnClickListener(this);

        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, 0, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginBtn:

                if (ExceptionHandler.isConnectedToInternet(getApplicationContext())) {

                    User.getInstance().setPassword(password.getText().toString());
                    User.getInstance().setEmailAddress(email.getText().toString());

                    if(validInput()) {
                        showProgressBar();
                        apiService.login(User.getInstance()).enqueue(this);
                    }
                }else {
                    makeSnackBar("Geen Internet verbinding", LoginActivity.this);
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
                TextView iForgotLbl = dialog.findViewById(R.id.iForgotLbl);
                TextView forgotPasswordText = dialog.findViewById(R.id.forgotPasswordText);
                LinearLayout buttonsPanel = dialog.findViewById(R.id.buttonsPanel);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                LinearLayout emailSent = dialog.findViewById(R.id.emailSent);
                Button closeDialogButton = dialog.findViewById(R.id.closeDialogButton);

                cancelForgotPassword.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                closeDialogButton.setOnClickListener(view -> {
                    dialog.dismiss();
                });


                Button sendForgotPasswordEmail = dialog.findViewById(R.id.send_forgot_password_email);
                sendForgotPasswordEmail.setOnClickListener(view -> {

                    if (validateForm.inputValidString(forgotPasswordEmailInput)) {
                        iForgotLbl.setVisibility(View.INVISIBLE);
                        forgotPasswordText.setVisibility(View.INVISIBLE);
                        buttonsPanel.setVisibility(View.INVISIBLE);
                        forgotPasswordEmailInput.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        apiService.requestResetPasswordEmail(forgotPasswordEmailInput.getText().toString()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //TODO: toon message aan de hand van de response
                                if (response.body() != null && response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    emailSent.setVisibility(View.VISIBLE);
                                    closeDialogButton.setVisibility(View.VISIBLE);
                                }else {
                                    iForgotLbl.setVisibility(View.VISIBLE);
                                    forgotPasswordText.setVisibility(View.VISIBLE);
                                    buttonsPanel.setVisibility(View.VISIBLE);
                                    forgotPasswordEmailInput.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);

                                    forgotPasswordEmailInput.setError("Er is iets fout gegaan, controleer uw e-mail en probeer het opnieuw.");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                iForgotLbl.setVisibility(View.VISIBLE);
                                forgotPasswordText.setVisibility(View.VISIBLE);
                                buttonsPanel.setVisibility(View.VISIBLE);
                                forgotPasswordEmailInput.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);

                                try {
                                    ExceptionHandler.exceptionThrower(new Exception());
                                } catch (Exception e) {

                                    makeSnackBar(ExceptionHandler.getMessage(e), LoginActivity.this);
                                }
                            }
                        });

                    }else if(!validateForm.InputValidEmail(forgotPasswordEmailInput)){

                        forgotPasswordEmailInput.setError("Vul een geldig e-mail adres in");
                    }
                });
                dialog.show();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private boolean validInput(){

        if (!validateForm.inputValidString(email)) {
            email.setError("Vul uw e-mail in");
            return false;
        }else if(!validateForm.InputValidEmail(email)){

            email.setError("Geen geldige e-mail");
            return false;
        }
        if(!validateForm.inputValidString(password)){

            password.setError("Vul uw wachtwoord in");
            return false;
        }
        return true;
    }
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        Credential credential = new Credential.Builder(User.getInstance().getEmailAddress())
                .setPassword(User.getInstance().getPassword())
                .build();

        if (response.body() != null && response.isSuccessful()) {

            User.setInstance(response.body());
            goToMainActivity();
            try{
                saveCredential(credential);
            }catch (Exception e){

            }
        } else {
            //Credentials were invalid, hide progress bar/ delete credentials from google smart lock / show error message
            hideProgressBar();

            deleteCredential(credential);
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                makeSnackBar(jObjError.getString("error"), this);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        hideProgressBar();

        try {
            ExceptionHandler.exceptionThrower(new Exception());
        } catch (Exception e) {

            makeSnackBar(ExceptionHandler.getMessage(e), this);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current sign in state
        savedInstanceState.putBoolean(IS_RESOLVING, mIsResolving);
        savedInstanceState.putBoolean(IS_REQUESTING, mIsRequesting);

        // Always call the superclass to save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_READ) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                processRetrievedCredential(credential);
            } else {
                Log.e(TAG, "Credential Read: NOT OK");
                hideProgressBar();
            }
        } else if (requestCode == RC_SAVE) {
            Log.d(TAG, "Result code: " + resultCode);
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Credential Save: OK");
            } else {
                Log.e(TAG, "Credential Save Failed");
            }
        }
        mIsResolving = false;

    }

    public void openFragment(final Fragment fg)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fg, fg.toString());

            ft.addToBackStack(fg.toString());

        ft.commit();
    }

    public void showProgressBar(){
        loginPage.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        loginPage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    public void makeSnackBar(String messageText, Activity fg)
    {
        Snackbar snackbar = Snackbar.make(fg.findViewById(R.id.parentLayout),
                messageText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    protected void saveCredential(Credential credential) {
        // Credential is valid so save it.
        Auth.CredentialsApi.save(mGoogleApiClient,
                credential).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    Log.d(TAG, "Credential saved");
                } else {
                    Log.d(TAG, "Attempt to save credential failed " +
                            status.getStatusMessage() + " " +
                            status.getStatusCode());
                    resolveResult(status, RC_SAVE);
                }
            }
        });
    }

    private void deleteCredential(Credential credential) {
        Auth.CredentialsApi.delete(mGoogleApiClient,
                credential).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    Log.d(TAG, "Credential successfully deleted.");
                } else {
                    // This may be due to the credential not existing, possibly
                    // already deleted via another device/app.
                    Log.d(TAG, "Credential not deleted successfully.");
                }
            }
        });
    }

    private void resolveResult(Status status, int requestCode) {
        // We don't want to fire multiple resolutions at once since that can result
        // in stacked dialogs after rotation or another similar event.
        if (mIsResolving) {
            Log.w(TAG, "resolveResult: already resolving.");
            return;
        }

        Log.d(TAG, "Resolving: " + status);
        if (status.hasResolution()) {
            Log.d(TAG, "STATUS: RESOLVING");
            try {
                status.startResolutionForResult(this, requestCode);
                mIsResolving = true;
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "STATUS: Failed to send resolution.", e);
            }
        }
//        else {
//            goToMainActivity();
//        }
    }

    private void processRetrievedCredential(Credential credential) {
        showProgressBar();

        User.getInstance().setEmailAddress(credential.getId());
        User.getInstance().setPassword(credential.getPassword());
        apiService.login(User.getInstance()).enqueue(this);

    }

    private void requestCredentials() {
        mIsRequesting = true;

        CredentialRequest request = new CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE, IdentityProviders.TWITTER)
                .build();

        Auth.CredentialsApi.request(mGoogleApiClient, request).setResultCallback(
                new ResultCallback<CredentialRequestResult>() {
                    @Override
                    public void onResult(CredentialRequestResult credentialRequestResult) {
//                        mIsRequesting = false;
                        Status status = credentialRequestResult.getStatus();
                        if (credentialRequestResult.getStatus().isSuccess()) {
                            // Successfully read the credential without any user interaction, this
                            // means there was only a single credential and the user has auto
                            // sign-in enabled.
                            Credential credential = credentialRequestResult.getCredential();
                            processRetrievedCredential(credential);
                        } else if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                            // This is most likely the case where the user has multiple saved
                            // credentials and needs to pick one.
                            resolveResult(status, RC_READ);
                        } else if (status.getStatusCode() == CommonStatusCodes.SIGN_IN_REQUIRED) {
                            // This is most likely the case where the user does not currently
                            // have any saved credentials and thus needs to provide a username
                            // and password to sign in.
                            Log.d(TAG, "Sign in required");
                        } else {
                            Log.w(TAG, "Unrecognized status code: " + status.getStatusCode());
                        }
                    }
                }
        );
    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");

        // Request Credentials once connected. If credentials are retrieved
        // the user will either be automatically signed in or will be
        // presented with credential options to be used by the application
        // for sign in.
        requestCredentials();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "onConnectionSuspended: " + cause);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
    }
}
