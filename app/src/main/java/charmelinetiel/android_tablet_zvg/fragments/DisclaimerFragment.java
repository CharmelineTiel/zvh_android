package charmelinetiel.android_tablet_zvg.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmelinetiel.android_tablet_zvg.R;

/**
 * Created by youp on 4-1-2018.
 */

public class DisclaimerFragment extends Fragment {

    public View v;

    public DisclaimerFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_disclaimer, container, false);

        getActivity().setTitle("Disclaimer");

        return v;
    }
}
