package charmelinetiel.zorg_voor_het_hart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Consultant;
import charmelinetiel.zorg_voor_het_hart.models.Faq;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity implements Callback<User> {


    private APIService apiService;
    private Retrofit retrofit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        if (ExceptionHandler.getInstance().isConnectedToInternet(getApplicationContext())) {

            preloadContent();

        }else{

            this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show());
        }

        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.body() != null && response.isSuccessful()){

            User.setInstance(response.body());
            User.getInstance().setAuthToken(response.body().getAuthToken());

            intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        Toast.makeText(getApplicationContext(), getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {

        android.app.FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();

        } else {

            super.onBackPressed();
        }
    }

    private void preloadContent(){

        apiService.getFaq().enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Faq.setFaqList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {
            }
        });

        apiService.getAllConsultants().enqueue(new Callback<List<Consultant>>() {
            @Override
            public void onResponse(Call<List<Consultant>> call, Response<List<Consultant>> response) {
                Consultant.setConsultants(response.body());
            }

            @Override
            public void onFailure(Call<List<Consultant>> call, Throwable t) {

            }
        });

    }

}
