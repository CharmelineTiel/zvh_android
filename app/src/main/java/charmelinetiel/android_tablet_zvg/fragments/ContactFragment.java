package charmelinetiel.android_tablet_zvg.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.android_tablet_zvg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener  {

    View view;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact, container, false);

        Button btn = view.findViewById(R.id.sendBtn);
        btn.setOnClickListener(this);

        Button btn2 = view.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendBtn:

                Dialog dialog=new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                dialog.setContentView(R.layout.fragment_message_sent);

                dialog.show();

                break;

            case R.id.backBtn:


                Fragment fg = new HomeFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fg)
                        .addToBackStack(fg.toString())
                        .commit();
                break;

        }

    }
}
