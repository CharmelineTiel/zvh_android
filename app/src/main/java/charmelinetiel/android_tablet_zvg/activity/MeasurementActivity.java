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
import charmelinetiel.android_tablet_zvg.fragments.MeasurementCompletedFragment;
import charmelinetiel.android_tablet_zvg.fragments.MeasurementStep1Fragment;
import charmelinetiel.android_tablet_zvg.fragments.MeasurementStep2Fragment;
import charmelinetiel.android_tablet_zvg.fragments.MeasurementStep3Fragment;

public class MeasurementActivity extends AppCompatActivity implements StepperLayout.StepperListener {

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

        setTitle("Meting afronden");
        Fragment fg = new MeasurementCompletedFragment();
        setFragment(fg);

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {


            Fragment fg;
        switch (newStepPosition) {
            case 0:
                    setTitle("Meting stap 1 van 3");
                    fg = new MeasurementStep1Fragment();
                    setFragment(fg);

                break;

            case 1:

                    setTitle("Meting stap 2 van 3");
                    fg = new MeasurementStep2Fragment();
                    setFragment(fg);
                break;
            case 2:

                setTitle("Meting stap 3 van 3");
                fg = new MeasurementStep3Fragment();
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