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
import charmelinetiel.android_tablet_zvg.models.ExceptionHandler;
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


        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 1 van 3");
        }else{
            mainActivity.setTitle("Meting stap 1 van 3");
        }

        v = inflater.inflate(R.layout.fragment_measurement_step1, container, false);


        if (container != null) {
            container.removeAllViews();
        }

        cancelButton = v.findViewById(R.id.cancel_measurement1_button);
        nextButton = v.findViewById(R.id.to_measurement_step2_button);

        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);

        try {
            ExceptionHandler.exceptionThrower(new NumberFormatException());
            upperBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureUpper().toString());
            lowerBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureLower().toString());
        } catch (Exception e) {

            mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
        }

        dateTimeNow = v.findViewById(R.id.dateTimeNow);

        mainActivity.setDateOfToday(dateTimeNow);

        nextButton.setOnClickListener(v -> {

            Measurement measurement = mainActivity.getMeasurement();


            try {
                ExceptionHandler.exceptionThrower(new NumberFormatException());
                measurement.setBloodPressureUpper(Integer.parseInt(upperBloodPressure.getText().toString()));
                measurement.setBloodPressureLower(Integer.parseInt(lowerBloodPressure.getText().toString()));
            } catch (Exception e) {

                mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
            }

            mainActivity.setMeasurement(measurement);

            if (validInput()) {

                mainActivity.openFragment(new MeasurementStep2Fragment());
            }
        });

        cancelButton.setOnClickListener(v -> {

            mainActivity.openFragment(new HomeFragment());

            mainActivity.setTitle("Meting");

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
