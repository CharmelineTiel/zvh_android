package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.fragments.Service.ServiceFragment;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;
import charmelinetiel.zorg_voor_het_hart.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView greetUser, metingText;
    private MainActivity mainActivity;
    private String greeting = null;
    private Date date;
    String prefix;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();


        if(ServiceFragment.getPref(User.getInstance().getId() + "Date", this.getContext()) == null){

            metingText.setText("Start hier een nieuwe meting");


        } else if (ServiceFragment.getPref(User.getInstance().getId() + "Date",this.getContext()).equals(mainActivity.dateTimeNow())){

            metingText.setText("U heeft vandaag al een meting gedaan, wilt u nog een meting doen?");

        }else{

            PreferenceManager.getDefaultSharedPreferences(getContext());
            metingText.setText("Start hier een nieuwe meting");
        }

        initUserGreeting();

        return view;
    }

    public void initUserGreeting()
    {
        if (User.getInstance().getGender().equals(1)){
            prefix = getResources().getString(R.string.prefixMale);
        }else{

            prefix = getResources().getString(R.string.prefixFemale);
        }
        greetUser.setText( timeOfDay() + " " + prefix + " " + User.getInstance().getLastname());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.startMeasurementButton:

                mainActivity.setEditingMeasurement(false);
                mainActivity.setMeasurement(new Measurement());
                mainActivity.openFragment(new MeasurementStep1Fragment());
                break;

            case R.id.backHomeButton:

                mainActivity.getSupportFragmentManager().popBackStack();
                break;
        }

    }

    private void initViews(){

        view.findViewById(R.id.startMeasurementButton).setOnClickListener(this);
        metingText = view.findViewById(R.id.metingText);
        greetUser = view.findViewById(R.id.greetingsText);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle(getResources().getString(R.string.title_home));

    }

    public String timeOfDay()
    {
        greeting = null;
        date = new Date();

        if (date.getHours() < 12){
            greeting = getResources().getString(R.string.morningGreeting);
        }
        else if(date.getHours() <= 18){

            greeting = getResources().getString(R.string.afternoonGreeting);
        }
        else if(date.getHours() > 18 && date.getHours() < 24) {

            greeting = getResources().getString(R.string.nightGreeting);
        }

        return greeting;
    }

}
