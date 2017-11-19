package charmelinetiel.android_tablet_zvg.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.adapters.StepperAdapter;
import charmelinetiel.android_tablet_zvg.fragments.DiaryFragment;
import charmelinetiel.android_tablet_zvg.fragments.SuccessFragment;

public class StepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_stepper);


        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

        Fragment fg = new DiaryFragment();
        setFragment(fg);

    }

    @Override
    public void onCompleted(View completeButton) {

        //show success message, send email and show login button
        setTitle("Registratie Afronden");
        Fragment fg = new SuccessFragment();
        setFragment(fg);
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

               Fragment fg = new DiaryFragment();
               setFragment(fg);
        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReturn() {


        Toast.makeText(this, "One step back", Toast.LENGTH_SHORT).show();

    }


    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.stepperLayout, fg);
        fgTransition.addToBackStack(null);
        fgTransition.commit();
    }

}