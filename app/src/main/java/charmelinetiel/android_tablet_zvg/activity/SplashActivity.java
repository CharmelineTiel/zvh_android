package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.models.authToken;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity implements Callback<User> {


    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if Auto-login has been configured
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean autoLogin = sharedPref.getBoolean("autoLogin", false);
        String emailAddress = sharedPref.getString("emailAddress", "");
        String password = sharedPref.getString("password", "");


        //Check if user has been saved, if so go to mainactivity
//        if(autoLogin && !emailAddress.equals("") && !password.equals("")){
//            Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
//            apiService = retrofit.create(APIService.class);
//
//            User user = new User();
//            user.setEmailAddress(emailAddress);
//            user.setPassword(password);
//
//            apiService.login(user).enqueue(this);
//        }else{
            Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.RegisterActivity.class);
            startActivity(intent);
            finish();
//        }

    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.body() != null && response.isSuccessful()){

            Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.MainActivity.class);
            intent.putExtra("user", response.body());
            startActivity(intent);

            authToken.getInstance().setAuthToken(response.body().getAuthToken());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
}
