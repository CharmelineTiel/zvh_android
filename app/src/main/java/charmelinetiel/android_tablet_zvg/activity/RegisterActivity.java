package charmelinetiel.android_tablet_zvg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.adapters.RegisterAdapter;
import charmelinetiel.android_tablet_zvg.fragments.RegisterCompletedFragment;
import charmelinetiel.android_tablet_zvg.fragments.RegisterStep1Fragment;
import charmelinetiel.android_tablet_zvg.fragments.RegisterStep2Fragment;

public class RegisterActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;
    private RegisterAdapter mStepperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stepper);
        mStepperLayout =  findViewById(R.id.stepperLayout);
        mStepperAdapter = new RegisterAdapter(getSupportFragmentManager(), this);
        mStepperLayout.setAdapter(mStepperAdapter);
        mStepperLayout.setListener(this);

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


        //Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();

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
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

}