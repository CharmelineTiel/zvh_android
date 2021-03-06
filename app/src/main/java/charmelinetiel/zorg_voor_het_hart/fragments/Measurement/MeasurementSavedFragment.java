package charmelinetiel.zorg_voor_het_hart.fragments.Measurement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;


public class MeasurementSavedFragment extends Fragment implements View.OnClickListener {

    private View v;
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
        v.findViewById(R.id.toDiary).setOnClickListener(this);
        v.findViewById(R.id.toHome).setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toDiary:
                //Navigate to diary by selecting the diary item in the bottom nav
                mainActivity.getBottomNavigationView().setSelectedItemId(R.id.diary);
                break;
            case R.id.toHome:
                mainActivity.openFragment(new HomeFragment());
                break;
        }
    }
}
