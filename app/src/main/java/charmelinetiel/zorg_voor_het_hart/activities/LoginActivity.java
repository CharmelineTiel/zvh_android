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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
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
    private TextView forgotPassword, iForgotLbl, forgotPasswordText;
    private EditText email, password;
    private FormErrorHandling validateForm;
    private Uri data;
    private ProgressBar progressBar;
    private ScrollView loginPage;
    private Button loginButton, backButton, closeDialogButton, goToLoginButton;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsResolving;
    private boolean mIsRequesting;
    private Toolbar toolbar;
    private Dialog dialog;
    private Button cancelForgotPassword, sendForgotPasswordEmail;
    private EditText forgotPasswordEmailInput;
    private LinearLayout buttonsPanel, emailSent;
    private Intent mainIntent, refreshIntent;
    private Retrofit retrofit;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            mIsResolving = savedInstanceState.getBoolean(IS_RESOLVING);
            mIsRequesting = savedInstanceState.getBoolean(IS_REQUESTING);
        }

        retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        initViews();


        //Get the intent and data to determine if the activity was entered by deep link
        Intent intent = getIntent();
        data = intent.getData();


        //If the activity was entered by deep link, show the 'account activated' page
        if(data != null){

            setContentView(R.layout.activity_login_account_activated);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.account_activated);

            goToLoginButton = findViewById(R.id.goToLoginButton);
            goToLoginButton.setOnClickListener(view -> {
                //Refresh the activity to get to the login page
                 refreshIntent = new Intent(this, LoginActivity.class);
                startActivity(refreshIntent);
                this.finish();
            });
        //Else show the normal login page
        }else {

        validateForm = new FormErrorHandling();

            setSupportActionBar(toolbar);
            setTitle(R.string.title_activity_login);

            email = findViewById(R.id.username);
            password = findViewById(R.id.password);

            forgotPassword = findViewById(R.id.iForgot);
            forgotPassword.setOnClickListener(this);

            loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(this);

            backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(this);

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
            case R.id.loginButton:

                if (ExceptionHandler.isConnectedToInternet(getApplicationContext())) {

                    User.getInstance().setPassword(password.getText().toString());
                    User.getInstance().setEmailAddress(email.getText().toString());

                    if(validInput()) {

                        showProgressBar();
                        apiService.login(User.getInstance()).enqueue(this);
                    }
                }else {

                    makeSnackBar(getResources().getString(R.string.noInternetConnection), LoginActivity.this);
                }
                break;
            case R.id.backButton:

                Intent Intent = new Intent(this, RegisterActivity.class);
                startActivity(Intent);

                break;

            case R.id.iForgot:

                attemptResetPassword();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();

        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private boolean validInput(){

        if (!validateForm.inputValidString(email)) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            return false;
        }else if(!validateForm.InputValidEmail(email)){

            email.setError(getResources().getString(R.string.error_invalid_email));
            return false;
        }
        if(!validateForm.inputValidString(password)){

            password.setError(getResources().getString(R.string.error_invalid_password));
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
        makeSnackBar(getResources().getString(R.string.noInternetConnection), this);
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

        if (requestCode == RC_READ) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                processRetrievedCredential(credential);
            } else {
                hideProgressBar();
            }
        } else if (requestCode == RC_SAVE) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Credential Save: OK");
            } else {
                Log.e(TAG, "Credential Save Failed");
            }
        }
        mIsResolving = false;

    }

    public void openFragment(final Fragment fg){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fg, fg.toString());
        fragmentTransaction.addToBackStack(fg.toString());
        fragmentTransaction.commit();
    }

    public void showProgressBar(){
        loginPage.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        loginPage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    public void makeSnackBar(String messageText, Activity fg){
        Snackbar snackbar = Snackbar.make(fg.findViewById(R.id.parentLayout),
                messageText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    protected void saveCredential(Credential credential) {
        // Credential is valid so save it.
        Auth.CredentialsApi.save(mGoogleApiClient,
                credential).setResultCallback(status -> {
                    if (status.isSuccess()) {
                        Log.d(TAG, "Credential saved");
                    } else {
                        Log.d(TAG, "Attempt to save credential failed " +
                                status.getStatusMessage() + " " +
                                status.getStatusCode());
                        resolveResult(status, RC_SAVE);
                    }
                });
    }


    private void deleteCredential(Credential credential) {
        Auth.CredentialsApi.delete(mGoogleApiClient,
                credential).setResultCallback(status -> {
                    if (status.isSuccess()) {
                        Log.d(TAG, "Credential successfully deleted.");
                    } else {
                        // This may be due to the credential not existing, possibly
                        // already deleted via another device/app.
                        Log.d(TAG, "Credential not deleted successfully.");
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
                credentialRequestResult -> {
                        mIsRequesting = false;
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

                        makeSnackBar("U dient zich eerst in te loggen",this);

                    } else {

                        makeSnackBar(status.getStatusMessage(),this);

                    }
                }
        );
    }

    public void goToMainActivity(){

        mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Request Credentials once connected. If credentials are retrieved
        // the user will either be automatically signed in or will be
        // presented with credential options to be used by the application
        // for sign in.
        requestCredentials();
    }

    @Override
    public void onConnectionSuspended(int cause)
    {
        Log.d(TAG, "onConnectionSuspended: " + cause);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        makeSnackBar(connectionResult.getErrorMessage(),this);
    }

    private void initViews()
    {
        progressBar = findViewById(R.id.progressBar);
        loginPage = findViewById(R.id.loginPage);
    }

    //view for sending email in progress
    public void sendEmailInProgress(){

        iForgotLbl.setVisibility(View.INVISIBLE);
        forgotPasswordText.setVisibility(View.INVISIBLE);
        buttonsPanel.setVisibility(View.INVISIBLE);
        forgotPasswordEmailInput.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }

    //view for send email completed
    public void sendEmailCompleted(){

        progressBar.setVisibility(View.GONE);
        emailSent.setVisibility(View.VISIBLE);
        closeDialogButton.setVisibility(View.VISIBLE);
    }

    //view for send email failed
    public void sendEmailFailed(){

        iForgotLbl.setVisibility(View.VISIBLE);
        forgotPasswordText.setVisibility(View.VISIBLE);
        buttonsPanel.setVisibility(View.VISIBLE);
        forgotPasswordEmailInput.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    //initialize new dialog
    private void initIForgotDialog(){
        dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        dialog.setContentView(R.layout.forgot_password_dialog);
        forgotPasswordEmailInput = dialog.findViewById(R.id.forgotPasswordEmailInput);
        cancelForgotPassword = dialog.findViewById(R.id.cancel_forgot_password);
        iForgotLbl = dialog.findViewById(R.id.iForgotLbl);
        forgotPasswordText = dialog.findViewById(R.id.forgotPasswordText);
        buttonsPanel = dialog.findViewById(R.id.buttonsPanel);
        progressBar = dialog.findViewById(R.id.progressBar);
        emailSent = dialog.findViewById(R.id.emailSent);
        closeDialogButton = dialog.findViewById(R.id.closeDialogButton);

        cancelForgotPassword.setOnClickListener(view -> {
            dialog.dismiss();
        });

        closeDialogButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

        sendForgotPasswordEmail = dialog.findViewById(R.id.send_forgot_password_email);

    }

    private void attemptResetPassword(){

        initIForgotDialog();

        sendForgotPasswordEmail.setOnClickListener(view -> {

            if (validateForm.inputValidString(forgotPasswordEmailInput)) {

                sendEmailInProgress();

                apiService.requestResetPasswordEmail(forgotPasswordEmailInput.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.body() != null && response.isSuccessful()) {

                            sendEmailCompleted();

                        }else {

                            sendEmailFailed();

                            forgotPasswordEmailInput.setError(getResources().getString(R.string.emailNotFound));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        sendEmailFailed();

                        makeSnackBar(getResources().getString(R.string.noInternetConnection), LoginActivity.this);

                    }
                });

            }else if(!validateForm.InputValidEmail(forgotPasswordEmailInput)){

                forgotPasswordEmailInput.setError(getResources().getString(R.string.error_invalid_email));
            }
        });
        dialog.show();

    }
}
