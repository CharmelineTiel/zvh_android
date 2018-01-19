package charmelinetiel.zorg_voor_het_hart.fragments.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import charmelinetiel.zorg_voor_het_hart.fragments.Measurement.HomeFragment;
import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageSentFragment extends Fragment implements View.OnClickListener {

    private View view;
    private MainActivity mainActivity;

    public MessageSentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_message_sent, container, false);

        mainActivity = (MainActivity) getActivity();

        Button backButton = view.findViewById(R.id.backHomeButton);
        backButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backHomeButton:

                mainActivity.openFragment(new HomeFragment());

                break;
        }
    }
}
