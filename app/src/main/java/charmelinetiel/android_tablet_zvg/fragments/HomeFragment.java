package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import charmelinetiel.android_tablet_zvg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn = view.findViewById(R.id.metingBtn);
        btn.setOnClickListener(this);
        ImageView icon = view.findViewById(R.id.info);
        icon.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        Fragment fg;
        switch (v.getId()) {

            case R.id.metingBtn:
                fg= new MeasurementStep1Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fg)
                        .addToBackStack(fg.toString())
                        .commit();
                break;
            case R.id.info:
                fg = new FAQFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fg)
                        .addToBackStack(fg.toString())
                        .commit();
                break;

            case R.id.backHome:
                fg= new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fg)
                        .addToBackStack(fg.toString())
                        .commit();
                break;
        }

    }
}
