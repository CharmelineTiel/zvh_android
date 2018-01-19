package charmelinetiel.zorg_voor_het_hart.fragments.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.LoginActivity;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.fragments.Register.RegisterStep1Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOrRegisterFragment extends Fragment implements View.OnClickListener{

    private View v;
    private RegisterActivity registerActivity;

    public LoginOrRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       v = inflater.inflate(R.layout.login_or_register, container, false);

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.getSupportActionBar().hide();

        //ik sta nog gewoon hier hoor
        v.findViewById(R.id.loginButton).setOnClickListener(this);
        v.findViewById(R.id.registerButton).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginButton:

                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.registerButton:

                registerActivity.openFragment(new RegisterStep1Fragment());
                break;
        }
    }

}
