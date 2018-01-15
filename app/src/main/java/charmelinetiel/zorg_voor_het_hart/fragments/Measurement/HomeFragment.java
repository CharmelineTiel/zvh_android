package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;
import charmelinetiel.zorg_voor_het_hart.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView greetUser;
    private MainActivity mainActivity;
    private ProgressBar progressBar;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar_cyclic);

        view.findViewById(R.id.metingBtn).setOnClickListener(this);
        greetUser = view.findViewById(R.id.greetingsText);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Meting");

        greetUser();

        return view;
    }

    public void greetUser()
    {
        String prefix;

        if (User.getInstance().getGender().equals(1)){
            prefix = "meneer";
        }else{

            prefix = "mevrouw";
        }
        greetUser.setText( timeOfDay() + " " + prefix + " " + User.getInstance().getLastname());
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

        switch (v.getId()) {

            case R.id.metingBtn:

                mainActivity.setEditingMeasurement(false);
                mainActivity.setMeasurement(new Measurement());
                mainActivity.openFragment(new MeasurementStep1Fragment());
                break;

            case R.id.backHome:

                mainActivity.getSupportFragmentManager().popBackStack();
                break;
        }

    }


}
