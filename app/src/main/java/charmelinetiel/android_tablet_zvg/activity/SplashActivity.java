package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import charmelinetiel.android_tablet_zvg.models.AuthToken;
import charmelinetiel.android_tablet_zvg.models.Consultant;
import charmelinetiel.android_tablet_zvg.models.Faq;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity implements Callback<User> {


    private APIService apiService;
    private List<Faq> faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if Auto-login has been configured
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean autoLogin = sharedPref.getBoolean("autoLogin", false);
        String emailAddress = sharedPref.getString("emailAddress", "");
        String password = sharedPref.getString("password", "");

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        apiService.getFaq().enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if(response.isSuccessful() && response.body() != null){
                    faqList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {

            }
        });

        apiService.getAllConsultants().enqueue(new Callback<List<Consultant>>() {
            @Override
            public void onResponse(Call<List<Consultant>> call, Response<List<Consultant>> response) {

            }

            @Override
            public void onFailure(Call<List<Consultant>> call, Throwable t) {

            }
        });



        //Check if user has been saved, if so go to mainactivity
        if(autoLogin && !emailAddress.equals("") && !password.equals("")){
            User user = new User();
            user.setEmailAddress(emailAddress);
            user.setPassword(password);

            apiService.login(user).enqueue(this);
        }else{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.body() != null && response.isSuccessful()){

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", response.body());
            startActivity(intent);

            AuthToken.getInstance().setAuthToken(response.body().getAuthToken());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        finish();
    }
}
