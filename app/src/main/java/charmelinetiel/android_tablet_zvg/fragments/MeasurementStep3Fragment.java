package charmelinetiel.android_tablet_zvg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView date;
    private MainActivity mainActivity;

    public MeasurementStep3Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        v = inflater.inflate(R.layout.fragment_measurement_step3, container, false);

        mainActivity.setTitle("Meting stap 3 van 3");

        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 3 van 3");
        }else{
            mainActivity.setTitle("Meting stap 3 van 3");
        }

        cancelButton = v.findViewById(R.id.cancel_measurement3_button);
        completeButton = v.findViewById(R.id.complete_measurement_button);
        extraRemarksInput = v.findViewById(R.id.extraRemarksInput);
        date = v.findViewById(R.id.dateTimeNow);
        mainActivity.setDateOfToday(date);

        extraRemarksInput.setText(mainActivity.getMeasurement().getComment());

        if (container != null) {
            container.removeAllViews();
        }

        completeButton.setOnClickListener(v -> {

        Measurement measurement = mainActivity.getMeasurement();
        measurement.setComment(extraRemarksInput.getText().toString());

        mainActivity.openFragment(new MeasurementSavedFragment());
            if(mainActivity.isEditingMeasurement()){
                mainActivity.putMeasurement();
            }else{
                mainActivity.postMeasurement();
            }
        MeasurementSavedFragment measurementSaved = new MeasurementSavedFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, measurementSaved)
                .addToBackStack(null)
                .commit();
        });

        cancelButton.setOnClickListener(v -> getFragmentManager().popBackStack());

        return v;
    }
}
