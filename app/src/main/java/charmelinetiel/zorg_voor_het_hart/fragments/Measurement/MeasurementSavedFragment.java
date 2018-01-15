package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.zorg_voor_het_hart.fragments.Diary.DiaryFragment;
import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;


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
        mainActivity.setTitle("Meting afgerond");
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
