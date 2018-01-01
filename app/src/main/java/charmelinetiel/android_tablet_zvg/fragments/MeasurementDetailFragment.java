package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.helpers.FragmentChangeListener;
import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementDetailFragment extends Fragment implements View.OnClickListener, FragmentChangeListener {

    private View v;
    private Measurement m;
    private TextView issues, extra, extraLbl;
    private MainActivity mainActivity;
    private List<HealthIssue> healthIssues;

    public MeasurementDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m = getArguments().getParcelable("measurement");

        mainActivity = (MainActivity) getActivity();
        healthIssues = mainActivity.getHealthIssues();

        v = inflater.inflate(R.layout.fragment_measurement_detail, container, false);

        Button backButton = v.findViewById(R.id.backBtn);
        backButton.setOnClickListener(this);

        Button editButton = v.findViewById(R.id.editBtn);
        editButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                DiaryFragment previousFrag= new DiaryFragment();
                replaceFragment(previousFrag);
                break;
            case R.id.editBtn:
                mainActivity.setMeasurement(m);
                MeasurementStep1Fragment editMeasurementFrag = new MeasurementStep1Fragment();
                replaceFragment(editMeasurementFrag);
                break;
        }
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        TextView date = v.findViewById(R.id.titleLbl);
        TextView bloodPressure = v.findViewById(R.id.bloodPressureLbl);
        TextView feedback = v.findViewById(R.id.feedbackLbl);
        RelativeLayout layout = v.findViewById(R.id.measurement_list_item);

        issues = v.findViewById(R.id.issues);
        extra = v.findViewById(R.id.extra);
        extraLbl = v.findViewById(R.id.extraLbl);

        String healthIssuesText = "";

        for (int i = 0; i < healthIssues.size(); i++){
            healthIssuesText += healthIssues.get(i).getName();

            //Add a comma if needed
            if(i != healthIssues.size()-1 || m.getHealthIssueOther() != ""){
                healthIssuesText += ", ";
            }
        }
        healthIssuesText += m.getHealthIssueOther();

        issues.setText(healthIssuesText);

        if(m.getComment() == "" || m.getComment() == null){
            extraLbl.setVisibility(View.INVISIBLE);
        }

        extra.setText(m.getComment());

        date.setText(m.getMeasurementDateFormatted());
        bloodPressure.setText("Bovendruk: " + m.getBloodPressureUpper() + ", " + "Onderdruk: " + m.getBloodPressureLower());

        if (m.getBloodPressureLower() <= 80 && m.getBloodPressureUpper()
                <= 130) {

            feedback.setTextColor(getResources().getColor(R.color.positiveFeedbackTxt));
            feedback.setText("Uw bloeddruk is prima");
            layout.setBackgroundColor(getResources().getColor(R.color.positiveFeedback));
        }else{

            feedback.setTextColor(getResources().getColor(R.color.negativeFeedbackTxt));
            feedback.setText("Houd uw bloeddruk goed in de gaten");
            layout.setBackgroundColor(getResources().getColor(R.color.negativeFeedback));

        }

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}
