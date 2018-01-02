package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.Measurement;
import charmelinetiel.android_tablet_zvg.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView greetUser;
    private User user;
    private MainActivity mainActivity;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);


        Button measurementBtn = view.findViewById(R.id.metingBtn);
        measurementBtn.setOnClickListener(this);

        greetUser = view.findViewById(R.id.greetingsText);

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();

        greetUser();

        return view;
    }

    public void greetUser()
    {
        String prefix;

        if (user.getGender().equals(1)){
            prefix = "meneer";
        }else{

            prefix = "mevrouw";
        }

        greetUser.setText( timeOfDay() + " " + prefix + " " + user.getLastname());

    }

    public String timeOfDay()
    {
        String greeting = null;

        Date date = new Date();

        if (date.getHours() < 12){
            greeting = "Goedemorgen,";
        }
        else if(date.getHours() <= 18){

            greeting = "Goedemiddag,";
        }
        else if(date.getHours() > 18 && date.getHours() < 24) {

            greeting = "Goedenavond,";
         }

         return greeting;
    }


    @Override
    public void onClick(View v) {

        Fragment fg;
        switch (v.getId()) {

            case R.id.metingBtn:

                mainActivity.setEditingMeasurement(false);
                mainActivity.setMeasurement(new Measurement());
                mainActivity.openFragment(new MeasurementStep1Fragment());

                break;

            case R.id.backHome:

                mainActivity.openFragment(new HomeFragment());

                break;
        }

    }
}
