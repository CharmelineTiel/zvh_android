package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.fragments.RegisterStep1Fragment;

/**
 * Created by Tiel on 17-11-2017.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";
    Context context;
    FragmentManager fm;
    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);

        this.context = context;
        this.fm = fm;
    }

    @Override
    public Step createStep(int position) {

        final RegisterStep1Fragment step = new RegisterStep1Fragment();
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context)
                .setTitle(R.string.title_contact) //can be a CharSequence instead
                .create();
    }
}
