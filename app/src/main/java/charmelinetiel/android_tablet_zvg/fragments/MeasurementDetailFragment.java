package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.ExceptionHandler;
import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementDetailFragment extends Fragment implements View.OnClickListener {

    private View v;
    private Measurement m;
    private TextView issues, extra, extraLbl, issuesLbl;
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

        if (ExceptionHandler.isConnectedToInternet(getContext())) {

            healthIssues = mainActivity.getHealthIssues();
        }else{

            mainActivity.makeSnackBar(getString(R.string.noInternetConnection), mainActivity);
        }

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

                mainActivity.openFragment(new DiaryFragment());

                break;
            case R.id.editBtn:

                //Set the measurement in the mainactivity so the values can be set
                if (ExceptionHandler.isConnectedToInternet(getContext())) {
                    mainActivity.setMeasurement(m);
                    mainActivity.setEditingMeasurement(true);
                    mainActivity.openFragment(new MeasurementStep1Fragment());
                }else{

                    mainActivity.makeSnackBar(String.valueOf(R.string.noInternetConnection), mainActivity);
                }

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
        issuesLbl = v.findViewById(R.id.issuesLbl);

        String healthIssuesText = "";

        for (int i = 0; i < m.getHealthIssueIds().size(); i++){
            for(int j = 0; j < healthIssues.size(); j++){
                if(m.getHealthIssueIds().get(i).equals(healthIssues.get(j).getHealthIssueId())){
                    healthIssuesText += healthIssues.get(j).getName();

                }
            }
            //Add a comma if needed
            if(i != m.getHealthIssueIds().size()-1 || !m.getHealthIssueOther().equals("")){
                healthIssuesText += ", ";
            }
        }
        healthIssuesText += m.getHealthIssueOther();

        issues.setText(healthIssuesText);

        if(m.getComment().equals("") || m.getComment() == null){
            extraLbl.setVisibility(View.GONE);
            extra.setVisibility(View.GONE);
        }
        if(m.getHealthIssueIds().size() == 0 && (m.getHealthIssueOther().equals("") || m.getHealthIssueOther() == null)){
            issuesLbl.setVisibility(View.GONE);
            issues.setVisibility(View.GONE);
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

}
