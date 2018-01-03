package charmelinetiel.android_tablet_zvg.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOrRegisterFragment extends Fragment implements View.OnClickListener{


    private View v;
    private Button loginButton, registerButton;
    private RegisterActivity registerActivity;

    public LoginOrRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.login_or_register, container, false);

        registerActivity = (RegisterActivity) getActivity();
        (getActivity()).setTitle("Zorg voor het hart");

        loginButton = v.findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);

        registerButton = v.findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(this);

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

                registerActivity.openFragment(new RegisterStep1Fragment());

                break;
        }
    }


}
