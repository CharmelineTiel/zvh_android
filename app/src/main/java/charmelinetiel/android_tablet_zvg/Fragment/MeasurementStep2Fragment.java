package charmelinetiel.android_tablet_zvg.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.adapters.CheckboxAdapter;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandling;
import charmelinetiel.android_tablet_zvg.models.Measurement;

public class MeasurementStep2Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button nextButton;
    private EditText otherNamelyInput;
    private ListView checkboxList;
    private TextView otherNamelyLbl, noIssues, date;
    private RadioGroup measurementRadioGroup;
    private MainActivity mainActivity;
    private CheckboxAdapter measurementCheckboxAdapter;
    private FormErrorHandling errorHandling;
    private TextView warningMessage;

    public MeasurementStep2Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_measurement_step2, container, false);

        mainActivity = (MainActivity) getActivity();
        errorHandling = new FormErrorHandling();

        cancelButton = v.findViewById(R.id.cancel_measurement2_button);
        nextButton = v.findViewById(R.id.to_measurement_step3_button);
        otherNamelyInput = v.findViewById(R.id.otherNamelyInput);
        otherNamelyLbl = v.findViewById(R.id.otherNamely);
        noIssues = v.findViewById(R.id.noIssues);
        measurementRadioGroup = v.findViewById(R.id.measurementRadioGroup);
        date = v.findViewById(R.id.dateTimeNow);
        warningMessage = v.findViewById(R.id.warningMessage);



        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 2 van 3");
        }else{
            mainActivity.setTitle("Meting stap 2 van 3");
        }

        try {
            measurementCheckboxAdapter = new CheckboxAdapter(mainActivity,
                    R.layout.checkbox_listview_item,
                    mainActivity.getHealthIssues(),
                    mainActivity.getMeasurement().getHealthIssueIds());
                    checkboxList.setAdapter(measurementCheckboxAdapter);

        } catch (Exception e) {
            measurementCheckboxAdapter = new CheckboxAdapter(mainActivity,
                    R.layout.checkbox_listview_item, mainActivity.getHealthIssues(), null);
        }


        measurementRadioGroup.check(R.id.yesNamelyRadio);
        mainActivity.setDateOfToday(date);


        if (container != null) {
            container.removeAllViews();
        }


        CheckboxAdapter finalMeasurementCheckboxAdapter = measurementCheckboxAdapter;


        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                MainActivity activity = (MainActivity) getActivity();
                Measurement measurement = activity.getMeasurement();

                measurement.setHealthIssueIds(finalMeasurementCheckboxAdapter.getSelectedIssues());
                measurement.setHealthIssueOther(otherNamelyInput.getText().toString());
                boolean yesNamelySelected = measurementRadioGroup.getCheckedRadioButtonId() == R.id.yesNamelyRadio;

                if (finalMeasurementCheckboxAdapter.getSelectedIssues().size() == 0
                        && yesNamelySelected && !errorHandling.inputGiven(otherNamelyInput)){

                    mainActivity.makeSnackBar("Selecteer miminaal 1 gezondheidsklacht", mainActivity);

                }else
                    mainActivity.openFragment(new MeasurementStep3Fragment());
                }

        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getFragmentManager().popBackStack();
            }
        });

        measurementRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.noneRadio:

                        checkboxList.setVisibility(View.GONE);
                        otherNamelyLbl.setVisibility(View.GONE);
                        otherNamelyInput.setVisibility(View.GONE);
                        noIssues.setVisibility(View.VISIBLE);

                        break;

                    case R.id.yesNamelyRadio:

                        checkboxList.setVisibility(View.VISIBLE);
                        otherNamelyLbl.setVisibility(View.VISIBLE);
                        otherNamelyInput.setVisibility(View.VISIBLE);
                        noIssues.setVisibility(View.GONE);

                        break;
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkboxList.setOnClickListener(view -> {
            warningMessage.setVisibility(View.VISIBLE);
        });


    }


}
