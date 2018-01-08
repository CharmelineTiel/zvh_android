package charmelinetiel.android_tablet_zvg.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


        return v;

    }

    private boolean validInput()
    {
        validateForm = new FormErrorHandling();

        if(!validateForm.inputValidBloodPressure(lowerBloodPressure, false)){
            lowerBloodPressure.setError("Ongeldige waarde, controleer uw onderdruk");
            return false;
        }

        if(!validateForm.inputValidBloodPressure(upperBloodPressure, true)){
            upperBloodPressure.setError("Ongeldige waarde, controleer uw bovendruk");
            return false;
        }
        
        return true;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 1 van 3");
        }else{
            mainActivity.setTitle("Meting stap 1 van 3");
        }



        cancelButton = v.findViewById(R.id.cancel_measurement1_button);
        nextButton = v.findViewById(R.id.to_measurement_step2_button);

        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);

        try {
            upperBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureUpper().toString());
            lowerBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureLower().toString());
        } catch (Exception e) {

        }

        dateTimeNow = v.findViewById(R.id.dateTimeNow);

        mainActivity.setDateOfToday(dateTimeNow);

        nextButton.setOnClickListener(v -> {

            Measurement measurement = mainActivity.getMeasurement();


            try {
                measurement.setBloodPressureUpper(Integer.parseInt(upperBloodPressure.getText().toString()));
                measurement.setBloodPressureLower(Integer.parseInt(lowerBloodPressure.getText().toString()));
            } catch (Exception e) {

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



    }
}
