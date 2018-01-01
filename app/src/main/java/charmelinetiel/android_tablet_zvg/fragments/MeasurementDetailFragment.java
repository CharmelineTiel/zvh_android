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

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.helpers.FragmentChangeListener;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementDetailFragment extends Fragment implements View.OnClickListener, FragmentChangeListener {

    private View v;
    private Measurement m;
    public MeasurementDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m = getArguments().getParcelable("measurement");

        v = inflater.inflate(R.layout.fragment_measurement_detail, container, false);

        Button btn = v.findViewById(R.id.backBtn);
        btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                DiaryFragment previousFrag= new DiaryFragment();
                replaceFragment(previousFrag);
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
