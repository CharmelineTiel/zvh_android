package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

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
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;


public class MeasurementStep1Fragment extends Fragment implements View.OnClickListener {

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
        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 1 van 3");
        }else{
            mainActivity.setTitle("Meting stap 1 van 3");
        }

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

        v.findViewById(R.id.cancel_measurement1_button).setOnClickListener(this);
        v.findViewById(R.id.to_measurement_step2_button).setOnClickListener(this);

        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);

        try {
            upperBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureUpper().toString());
            lowerBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureLower().toString());
        } catch (Exception e) {

        }

        dateTimeNow = v.findViewById(R.id.dateTimeNow);
        mainActivity.setDateOfToday(dateTimeNow);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_measurement1_button:
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
                break;
            case R.id.to_measurement_step2_button:
                mainActivity.openFragment(new HomeFragment());
                break;
        }
    }
}
