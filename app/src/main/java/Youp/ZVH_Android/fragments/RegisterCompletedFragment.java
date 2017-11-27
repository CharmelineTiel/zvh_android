package Youp.ZVH_Android.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Youp.ZVH_Android.R;
import Youp.ZVH_Android.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCompletedFragment extends Fragment implements View.OnClickListener{

    View view;
    public RegisterCompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register_completed, container, false);
        Button btn = view.findViewById(R.id.toLoginBtn);
        btn.setOnClickListener(this);

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
