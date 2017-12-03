package charmelinetiel.android_tablet_zvg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.Measurement;


public class MeasurementStep1Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button nextButton;
    private EditText upperBloodPressure;
    private EditText lowerBloodPressure;

    public MeasurementStep1Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_measurement_step1, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        cancelButton = v.findViewById(R.id.cancel_measurement1_button);
        nextButton = v.findViewById(R.id.to_measurement_step2_button);

        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment step2 = new MeasurementStep2Fragment();

                MainActivity activity = (MainActivity) getActivity();
                Measurement measurement = activity.getMeasurement();

                measurement.setBloodPressureUpper(Integer.parseInt(upperBloodPressure.getText().toString()));
                measurement.setBloodPressureLower(Integer.parseInt(lowerBloodPressure.getText().toString()));

                activity.setMeasurement(measurement);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, step2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().onBackPressed();
            }
        });

        return v;

    }

}
