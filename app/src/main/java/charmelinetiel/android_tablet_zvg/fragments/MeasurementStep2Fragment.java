package youp.zvh_android.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import youp.zvh_android.models.HealthIssue;

import youp.zvh_android.adapters.CheckboxAdapter;
import youp.zvh_android.R;
import youp.zvh_android.activity.MainActivity;
import youp.zvh_android.models.Measurement;

/**
 * Created by youp on 28-11-2017.
 */

public class MeasurementStep2Fragment extends Fragment {

    private View v;
    private Button cancelButton;
    private Button nextButton;
    private List<String> selectedIssues;
    CheckboxAdapter measurementCheckboxAdapter = null;


    public MeasurementStep2Fragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();

        v = inflater.inflate(R.layout.fragment_measurement_step2, container, false);

        measurementCheckboxAdapter = new CheckboxAdapter(activity, R.layout.checkbox_listview_item, activity.getHealthIssues());
        final ListView listView = (ListView) v.findViewById(R.id.checkboxList);
        listView.setAdapter(measurementCheckboxAdapter);

        selectedIssues = new ArrayList<>();

        cancelButton = v.findViewById(R.id.cancel_measurement2_button);
        nextButton = v.findViewById(R.id.to_measurement_step3_button);

        if (container != null) {
            container.removeAllViews();
        }

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                MainActivity activity = (MainActivity) getActivity();
                Measurement measurement = activity.getMeasurement();

                measurement.setHealthIssueIds(measurementCheckboxAdapter.getSelectedIssues());

                MeasurementStep3Fragment step3 = new MeasurementStep3Fragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, step3)
                        .addToBackStack(null)
                        .commit();
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
