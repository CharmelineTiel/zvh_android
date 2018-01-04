package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmelinetiel.android_tablet_zvg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMessageFragment extends Fragment {

    private View view;

    public NewMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nieuw_message, container, false);

        return view;
    }

}
