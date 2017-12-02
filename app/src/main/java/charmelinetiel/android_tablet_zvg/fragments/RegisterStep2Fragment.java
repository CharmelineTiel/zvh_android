package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener{


    View v;
    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =inflater.inflate(R.layout.fragment_register_step2, container, false);

        Button btn1 = v.findViewById(R.id.registerBtn);
        btn1.setOnClickListener(this);

        Button btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        Fragment fg;
        switch (v.getId()) {

            case R.id.registerBtn:

                fg = new RegisterCompletedFragment();
                setFragment(fg);
                break;

            case R.id.backBtn:

                fg = new RegisterStep1Fragment();
                setFragment(fg);
                break;
        }
    }


    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }
}
