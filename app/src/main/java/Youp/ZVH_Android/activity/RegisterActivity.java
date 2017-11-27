package Youp.ZVH_Android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.List;

import Youp.ZVH_Android.R;
import Youp.ZVH_Android.adapters.StepperAdapter;
import Youp.ZVH_Android.fragments.RegisterCompletedFragment;
import Youp.ZVH_Android.fragments.RegisterStep1Fragment;
import Youp.ZVH_Android.fragments.RegisterStep2Fragment;

import Youp.ZVH_Android.models.Consultant;
import Youp.ZVH_Android.webservice.APIService;
import Youp.ZVH_Android.webservice.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;



public class RegisterActivity extends AppCompatActivity implements Callback<List<Consultant>>, StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;
    private StepperAdapter mStepperAdapter;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stepper);
        mStepperLayout =  findViewById(R.id.stepperLayout);
        mStepperAdapter = new StepperAdapter(getSupportFragmentManager(), this);
        mStepperLayout.setAdapter(mStepperAdapter);
        mStepperLayout.setListener(this);


        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com");

        apiService = retrofit.create(APIService.class);

        fetchContent();
    }

    private void fetchContent() {
        apiService.getAllConsultants().enqueue(this);
    }

    @Override
    public void onCompleted(View completeButton) {

        //show success message, send email and show login button
        setTitle("Registratie Afronden");
        Fragment fg = new RegisterCompletedFragment();
        setFragment(fg);

    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStepSelected(int newStepPosition) {


            Fragment fg;
        switch (newStepPosition) {
            case 0:
                    setTitle("Registreren stap 1 van 2");
                    fg = new RegisterStep1Fragment();
                    setFragment(fg);

                break;

            case 1:

                    setTitle("Registreren stap 2 van 2");
                    fg = new RegisterStep2Fragment();
                    setFragment(fg);
                break;
        }

    }

    @Override
    public void onReturn() {

        finish();

    }


    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.stepperLayout, fg);
        fgTransition.addToBackStack(null);
        fgTransition.commit();
    }

    @Override
    public void onResponse(Call<List<Consultant>> call, Response<List<Consultant>> response) {
        if (response.isSuccessful() && response.body() != null) {
            List<Consultant> con = response.body();
        }
    }

    @Override
    public void onFailure(Call<List<Consultant>> call, Throwable t) {

    }
}