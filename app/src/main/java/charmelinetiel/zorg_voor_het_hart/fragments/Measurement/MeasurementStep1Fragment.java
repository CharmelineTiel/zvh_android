package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;


public class MeasurementStep1Fragment extends Fragment implements View.OnClickListener {

    private View v;
    private EditText upperBloodPressure;
    private EditText lowerBloodPressure;
    private TextView dateTimeNow;
    private MainActivity mainActivity;

    public MeasurementStep1Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_measurement_step1, container, false);

        mainActivity = (MainActivity) getActivity();
        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 1 van 2");
        }else{
            mainActivity.setTitle("Meting stap 1 van 2");
        }

        initViews();

        if (container != null) {
            container.removeAllViews();
        }
        return v;

    }

    private boolean validInput()
    {

        if(!mainActivity.formErrorHandler.inputValidBloodPressure(lowerBloodPressure, false)){
            lowerBloodPressure.setError("Ongeldige waarde, controleer uw onderdruk");
            return false;
        }

        if(!mainActivity.formErrorHandler.inputValidBloodPressure(upperBloodPressure, true)){
            upperBloodPressure.setError("Ongeldige waarde, controleer uw bovendruk");
            return false;
        }
        
        return true;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            upperBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureUpper().toString());
            lowerBloodPressure.setText(mainActivity.getMeasurement().getBloodPressureLower().toString());
        } catch (Exception e) {

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_measurement1_button:
                mainActivity.openFragment(new HomeFragment());
                break;
            case R.id.complete_measurement_button:
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
        }
    }

    private void initViews(){

        v.findViewById(R.id.cancel_measurement1_button).setOnClickListener(this);
        v.findViewById(R.id.complete_measurement_button).setOnClickListener(this);
        upperBloodPressure = v.findViewById(R.id.upperBloodPressure);
        lowerBloodPressure = v.findViewById(R.id.lowerBloodPressure);
        dateTimeNow = v.findViewById(R.id.dateTimeNow);
        mainActivity.setDateOfToday(dateTimeNow);
    }
}
