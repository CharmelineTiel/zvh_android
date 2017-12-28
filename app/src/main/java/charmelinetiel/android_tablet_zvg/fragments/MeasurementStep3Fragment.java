package charmelinetiel.android_tablet_zvg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * Created by youp on 28-11-2017.
 */

public class MeasurementStep3Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button completeButton;
    private EditText extraRemarksInput;

    public MeasurementStep3Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_measurement_step3, container, false);

        (getActivity()).setTitle("Meting stap 3 van 3");

        cancelButton = v.findViewById(R.id.cancel_measurement3_button);
        completeButton = v.findViewById(R.id.complete_measurement_button);
        extraRemarksInput = v.findViewById(R.id.extraRemarksInput);

        if (container != null) {
            container.removeAllViews();
        }

        completeButton.setOnClickListener(v -> {

        MainActivity activity = (MainActivity) getActivity();
        Measurement measurement = activity.getMeasurement();

        measurement.setHealthIssueOther(extraRemarksInput.getText().toString());

        activity.postMeasurement();

                MeasurementSavedFragment measurementSaved = new MeasurementSavedFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, measurementSaved)
                        .addToBackStack(null)
                        .commit();

        });

        cancelButton.setOnClickListener(v -> getActivity().onBackPressed());

        return v;

    }
}
