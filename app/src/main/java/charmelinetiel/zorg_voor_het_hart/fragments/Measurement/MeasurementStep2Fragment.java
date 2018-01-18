package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.adapters.CheckboxAdapter;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;

public class MeasurementStep2Fragment extends Fragment implements View.OnClickListener {

    private View v;
    private EditText otherNamelyInput;
    private ListView checkboxList;
    private TextView otherNamelyLbl, noIssues, date;
    private RadioGroup measurementRadioGroup;
    private MainActivity mainActivity;
    private CheckboxAdapter measurementCheckboxAdapter;
    private FormErrorHandling errorHandling;
    private TextView warningMessage;
    private CheckboxAdapter finalMeasurementCheckboxAdapter;

    public MeasurementStep2Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_measurement_step2, container, false);

        mainActivity = (MainActivity) getActivity();
        errorHandling = new FormErrorHandling();

        initViews();

        measurementRadioGroup.check(R.id.noneRadio);
        hideIssues();

        if(mainActivity.isEditingMeasurement()){

            mainActivity.setTitle("Meting bewerken stap 2 van 2");

            if(mainActivity.getMeasurement().getHealthIssueIds().size() > 0 ||
                    !mainActivity.getMeasurement().getHealthIssueOther().isEmpty()){
                measurementRadioGroup.check(R.id.yesNamelyRadio);
                showIssues();
            }
        }else{
            mainActivity.setTitle("Meting stap 2 van 2");
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

        mainActivity.setDateOfToday(date);

        if (container != null) {
            container.removeAllViews();
        }

        v.findViewById(R.id.complete_measurement_button).setOnClickListener(this);
        v.findViewById(R.id.cancel_measurement2_button).setOnClickListener(this);

        configureRadioGroup();

        return v;
    }

    private void initViews() {
        otherNamelyInput = v.findViewById(R.id.otherNamelyInput);
        otherNamelyLbl = v.findViewById(R.id.otherNamely);
        noIssues = v.findViewById(R.id.noIssues);
        measurementRadioGroup = v.findViewById(R.id.measurementRadioGroup);
        date = v.findViewById(R.id.dateTimeNow);
        warningMessage = v.findViewById(R.id.warningMessage);
        checkboxList = v.findViewById(R.id.checkboxList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete_measurement_button:

                Measurement measurement = mainActivity.getMeasurement();
                if(measurementCheckboxAdapter.getSelectedIssues() != null){
                    measurement.setHealthIssueIds(measurementCheckboxAdapter.getSelectedIssues());
                }else{
                    measurement.setHealthIssueIds(new ArrayList<>());
                }
                measurement.setHealthIssueOther(otherNamelyInput.getText().toString());
                boolean yesNamelySelected = measurementRadioGroup.getCheckedRadioButtonId() == R.id.yesNamelyRadio;

                if (yesNamelySelected && measurementCheckboxAdapter.getSelectedIssues().size() == 0
                    && !errorHandling.inputValidString(otherNamelyInput)) {

                      mainActivity.makeSnackBar("Selecteer miminaal 1 gezondheidsklacht", mainActivity);

                } else {

                    if (mainActivity.isEditingMeasurement()) {
                        mainActivity.putMeasurement();
                    } else {
                        mainActivity.postMeasurement();
                    }
                    mainActivity.openFragment(new MeasurementSavedFragment());

                }
                    break;
            case R.id.cancel_measurement2_button:
                    getFragmentManager().popBackStack();
                    break;
                }
        }

    private void configureRadioGroup() {
        measurementRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.noneRadio:
                        hideIssues();

                        break;

                    case R.id.yesNamelyRadio:
                        showIssues();

                        break;
                }
            }
        });
    }

    public void hideIssues(){
        checkboxList.setVisibility(View.GONE);
        otherNamelyLbl.setVisibility(View.GONE);
        otherNamelyInput.setVisibility(View.GONE);
        noIssues.setVisibility(View.VISIBLE);
    }

    public void showIssues(){
        checkboxList.setVisibility(View.VISIBLE);
        otherNamelyLbl.setVisibility(View.VISIBLE);
        otherNamelyInput.setVisibility(View.VISIBLE);
        noIssues.setVisibility(View.GONE);
    }
}
