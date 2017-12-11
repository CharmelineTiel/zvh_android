package charmelinetiel.android_tablet_zvg.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;


public class MeasurementSavedFragment extends Fragment {

    private View v;
    private Button toDiary;

    public MeasurementSavedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_measurement_saved, container, false);

        toDiary = v.findViewById(R.id.toDiary);

        toDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                DiaryFragment diary = new DiaryFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, diary)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;

    }

}
