package charmelinetiel.zorg_voor_het_hart.fragments.Diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import charmelinetiel.zorg_voor_het_hart.fragments.Diary.DiaryFragment;
import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.fragments.Measurement.MeasurementStep1Fragment;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.HealthIssue;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryDetailFragment extends Fragment implements View.OnClickListener {

    private final int BLOODPRESSURE_GOOD = 0;
    private final int BLOODPRESSURE_MEDIUM = 1;
    private final int BLOODPRESSURE_BAD = 2;
    private View v;
    private Measurement m;
    private TextView issues, extra, extraLbl, issuesLbl, date, bloodPressure, feedback;
    private MainActivity mainActivity;
    private List<HealthIssue> healthIssues;
    private RelativeLayout layout;

    public DiaryDetailFragment() {
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
        v.findViewById(R.id.backBtn).setOnClickListener(this);
        v.findViewById(R.id.editBtn).setOnClickListener(this);

        initViews(v);

        setHealthIssueText();
        setLabels();
        setTexts();
        setBloodPressure();

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

    private void initViews(View v) {
        date = v.findViewById(R.id.titleLbl);
        bloodPressure = v.findViewById(R.id.bloodPressureLbl);
        feedback = v.findViewById(R.id.feedbackLbl);
        layout = v.findViewById(R.id.measurement_list_item);

        issues = v.findViewById(R.id.issues);
        extra = v.findViewById(R.id.extra);
        extraLbl = v.findViewById(R.id.extraLbl);
        issuesLbl = v.findViewById(R.id.issuesLbl);
    }

    private void setHealthIssueText() {
        String healthIssuesText = "";

        for (int i = 0; i < m.getHealthIssueIds().size(); i++){
            for(int j = 0; j < healthIssues.size(); j++){
                if(m.getHealthIssueIds().get(i).equals(healthIssues.get(j).getHealthIssueId())){
                    healthIssuesText += healthIssues.get(j).getName();
                }
            }
            //go down a line if needed
            if(i != m.getHealthIssueIds().size()-1 || !m.getHealthIssueOther().equals("")){
                healthIssuesText += "\n ";
            }
        }
        issues.setText(healthIssuesText);
    }

    private void setLabels() {
        if(m.getHealthIssueIds().size() == 0 && (m.getHealthIssueOther().equals("") || m.getHealthIssueOther() == null)){
            issuesLbl.setVisibility(View.GONE);
            issues.setVisibility(View.GONE);
        }
        if(m.getHealthIssueOther().isEmpty()){
            extraLbl.setVisibility(View.GONE);
        }
    }

    private void setTexts() {
        extra.setText(m.getHealthIssueOther());
        date.setText(m.getMeasurementDateFormatted());
        bloodPressure.setText("Bovendruk: " + m.getBloodPressureUpper() + ", " + "Onderdruk: " + m.getBloodPressureLower());
        feedback.setText(m.getFeedback());
    }

    private void setBloodPressure() {
        if (m.getResult() == BLOODPRESSURE_GOOD) {
            feedback.setTextColor(getResources().getColor(R.color.positiveFeedback));
            layout.setBackgroundColor(getResources().getColor(R.color.positiveFeedback));
        }else if(m.getResult() == BLOODPRESSURE_MEDIUM){
            feedback.setTextColor(getResources().getColor(R.color.mediumFeedback));
            layout.setBackgroundColor(getResources().getColor(R.color.mediumFeedback));
        }else if(m.getResult() == BLOODPRESSURE_BAD){
            feedback.setTextColor(getResources().getColor(R.color.negativeFeedback));
            layout.setBackgroundColor(getResources().getColor(R.color.negativeFeedback));
        }
    }

}