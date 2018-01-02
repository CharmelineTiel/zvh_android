package charmelinetiel.android_tablet_zvg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.adapters.CheckboxAdapter;
import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;


/**
 * Created by youp on 28-11-2017.
 */

public class MeasurementStep2Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button nextButton;
    private List<String> selectedIssues;
    private EditText otherNamelyInput;
    private ListView checkboxList;
    private TextView otherNamelyLbl, noIssues, date;
    private RadioGroup measurementRadioGroup;
    private MainActivity mainActivity;
    private CheckboxAdapter measurementCheckboxAdapter;


    public MeasurementStep2Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_measurement_step2, container, false);

        mainActivity = (MainActivity) getActivity();

        if(mainActivity.isEditingMeasurement()){
            mainActivity.setTitle("Meting bewerken stap 2 van 3");
        }else{
            mainActivity.setTitle("Meting stap 2 van 3");
        }

        if(mainActivity.getMeasurement() != null){
            measurementCheckboxAdapter = new CheckboxAdapter(mainActivity, R.layout.checkbox_listview_item, mainActivity.getHealthIssues(), mainActivity.getMeasurement().getHealthIssueIds());
        }else{
            measurementCheckboxAdapter = new CheckboxAdapter(mainActivity, R.layout.checkbox_listview_item, mainActivity.getHealthIssues(), null);
        }
        final ListView listView = v.findViewById(R.id.checkboxList);
        listView.setAdapter(measurementCheckboxAdapter);



        cancelButton = v.findViewById(R.id.cancel_measurement2_button);
        nextButton = v.findViewById(R.id.to_measurement_step3_button);
        otherNamelyInput = v.findViewById(R.id.otherNamelyInput);
        otherNamelyLbl = v.findViewById(R.id.otherNamely);
        checkboxList = v.findViewById(R.id.checkboxList);
        noIssues = v.findViewById(R.id.noIssues);
        measurementRadioGroup = v.findViewById(R.id.measurementRadioGroup);
        measurementRadioGroup.check(R.id.yesNamelyRadio);
        date = v.findViewById(R.id.dateTimeNow);

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
                        noIssues.setVisibility(View.VISIBLE);

                        break;

                    case R.id.yesNamelyRadio:

                        checkboxList.setVisibility(View.VISIBLE);
                        noIssues.setVisibility(View.GONE);
                        break;
                }
            }
        });

        return v;
    }

}
