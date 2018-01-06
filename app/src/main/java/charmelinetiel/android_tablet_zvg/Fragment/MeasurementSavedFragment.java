package charmelinetiel.android_tablet_zvg.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;


public class MeasurementSavedFragment extends Fragment {

    private View v;
    private Button toDiary, toHome;
    private MainActivity mainActivity;

    public MeasurementSavedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_measurement_saved, container, false);

        mainActivity = (MainActivity) getActivity();
        toDiary = v.findViewById(R.id.toDiary);
        toHome = v.findViewById(R.id.toHome);

        toDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mainActivity.openFragment(new DiaryFragment());

            }
        });

        toHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mainActivity.openFragment(new HomeFragment());

            }
        });
        return v;

    }

}
