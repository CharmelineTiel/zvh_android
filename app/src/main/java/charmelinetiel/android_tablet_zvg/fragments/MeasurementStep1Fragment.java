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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandling;
import charmelinetiel.android_tablet_zvg.models.Measurement;


public class MeasurementStep1Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button nextButton;
    private EditText upperBloodPressure;
    private EditText lowerBloodPressure;
    private TextView dateTimeNow;
    private FormErrorHandling validateForm;
    private MainActivity mainActivity;

    public MeasurementStep1Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        mainActivity.setTitle("Meting stap 1 van 3");

        v = inflater.inflate(R.layout.fragment_measurement_step1, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        cancelButton = v.findViewById(R.id.cancel_measurement1_button);
        nextButton = v.findViewById(R.id.to_measurement_step2_button);

        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);

        upperBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureUpper());
        lowerBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureLower());

        dateTimeNow = v.findViewById(R.id.dateTimeNow);

        //set the date and time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date customDate = new Date();
        dateTimeNow.setText("Datum van vandaag: " + " " + simpleDateFormat.format(customDate));

        nextButton.setOnClickListener(v -> {
            Fragment step2 = new MeasurementStep2Fragment();
            MainActivity activity1 = (MainActivity) getActivity();
            Measurement measurement = activity1.getMeasurement();

            try{
                measurement.setBloodPressureUpper(Integer.parseInt(upperBloodPressure.getText().toString()));
                measurement.setBloodPressureLower(Integer.parseInt(lowerBloodPressure.getText().toString()));
            }catch (Exception e){

            }

            activity1.setMeasurement(measurement);

            if (validInput()) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, step2);
                fragmentTransaction.addToBackStack(String.valueOf(step2.getId()));
                fragmentTransaction.commit();
            }
        });

        cancelButton.setOnClickListener(v -> {

            getFragmentManager().popBackStack();
            getActivity().setTitle("Meting");

        });

        return v;

    }

    private boolean validInput()
    {
        validateForm = new FormErrorHandling();

        if(!validateForm.inputGiven(upperBloodPressure)){

            validateForm.showError("Bovendruk mag niet leeg zijn");
            return false;
        }
        if(!validateForm.inputGiven(lowerBloodPressure)){

            validateForm.showError("Onderdruk mag niet leeg zijn");
            return false;
        }
        return true;
    }

}
