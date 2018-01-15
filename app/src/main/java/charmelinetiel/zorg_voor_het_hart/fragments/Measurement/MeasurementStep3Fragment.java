package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;

/**
 * Created by youp on 28-11-2017.
 */

public class MeasurementStep3Fragment extends Fragment implements View.OnClickListener {

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
        v = inflater.inflate(R.layout.fragment_measurement_step3, container, false);

        date = v.findViewById(R.id.dateTimeNow);
        extraRemarksInput = v.findViewById(R.id.extraRemarksInput);

        mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Meting stap 3 van 3");
        mainActivity.setDateOfToday(date);

        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 3 van 3");
        }else{
            mainActivity.setTitle("Meting stap 3 van 3");
        }

        v.findViewById(R.id.cancel_measurement3_button).setOnClickListener(this);
        v.findViewById(R.id.complete_measurement_button).setOnClickListener(this);

        extraRemarksInput.setText(mainActivity.getMeasurement().getComment());

        if (container != null) {
            container.removeAllViews();
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.complete_measurement_button:
                if (ExceptionHandler.isConnectedToInternet(getContext())) {
                    Measurement measurement = mainActivity.getMeasurement();
                    measurement.setComment(extraRemarksInput.getText().toString());

                    if (mainActivity.isEditingMeasurement()) {
                        mainActivity.putMeasurement();
                    } else {
                        mainActivity.postMeasurement();
                    }
                    mainActivity.openFragment(new MeasurementSavedFragment());

                }else{
                    mainActivity.makeSnackBar(getString(R.string.noInternetConnection), mainActivity);
                }
                break;
            case R.id.cancel_measurement3_button:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
