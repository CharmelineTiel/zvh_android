package Youp.ZVH_Android.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import Youp.ZVH_Android.R;
import Youp.ZVH_Android.fragments.RegisterStep1Fragment;

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

        StepViewModel.Builder builder = new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title);
        switch (position) {
            case 0:
                builder
                        .setEndButtonLabel("Volgende")
                        .setBackButtonLabel("Terug")
                        .setTitle("Registeren Stap 1")
                        .setBackButtonStartDrawableResId(StepViewModel.NULL_DRAWABLE);
                break;
            case 1:
                builder
                        .setEndButtonLabel(R.string.go_to_summary)
                        .setTitle("Registeren Stap 2")
                        .setBackButtonLabel("Annuleren");
                break;
//            case 2:
//                builder
//                        .setTitle("Afronden")
//                        .setBackButtonLabel("Terug")
//                        .setEndButtonLabel("Afronden");
//                break;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
        return builder.create();
    }
}
