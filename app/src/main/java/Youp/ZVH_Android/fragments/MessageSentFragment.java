package Youp.ZVH_Android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Youp.ZVH_Android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageSentFragment extends Fragment implements View.OnClickListener {

    View view;

    public MessageSentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact, container, false);

        Button btn = view.findViewById(R.id.sendMessageBtn);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
