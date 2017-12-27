package charmelinetiel.android_tablet_zvg.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOrRegisterFragment extends Fragment implements View.OnClickListener{


    View v;
    Button btn1, btn2;
    public LoginOrRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle("Zorg voor het hart");

        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.login_or_register, container, false);

        btn1 = v.findViewById(R.id.loginBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.registerBtn);
        btn2.setOnClickListener(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
       return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginBtn:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.registerBtn:
                Fragment fg = new RegisterStep1Fragment();
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
