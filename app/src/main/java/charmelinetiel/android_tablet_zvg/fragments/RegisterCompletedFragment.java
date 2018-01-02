package charmelinetiel.android_tablet_zvg.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCompletedFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView email;

    public RegisterCompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register_completed, container, false);
        Button btn = view.findViewById(R.id.toLoginBtn);
        btn.setOnClickListener(this);

        email = view.findViewById(R.id.emailAdress);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            email.setText(bundle.getString("email"));
        }

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toLoginBtn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
