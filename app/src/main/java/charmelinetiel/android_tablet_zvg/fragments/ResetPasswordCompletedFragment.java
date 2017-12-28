package charmelinetiel.android_tablet_zvg.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;


public class ResetPasswordCompletedFragment extends Fragment {

    private View v;
    private Button loginButton;

    public ResetPasswordCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reset_password_completed, container, false);

        loginButton = v.findViewById(R.id.toLoginBtn);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);

        });

        return v;
    }

}
