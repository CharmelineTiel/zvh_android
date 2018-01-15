package charmelinetiel.zorg_voor_het_hart.fragments.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactHostFragment extends Fragment
                    {

    private View view;
    private MainActivity mainActivity;
    private String screenResolution;
    private FragmentTabHost tabHost;

    public ContactHostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        screenResolution = getString(R.string.screen_type);
        mainActivity = (MainActivity)getActivity();

        if(screenResolution.equals("mobile")) {
            mainActivity.openFragment(new NewMessageFragment());

        }else if(screenResolution.equals("tablet")) {

            view = inflater.inflate(R.layout.fragment_new_message, container, false);

            tabHost = new FragmentTabHost(mainActivity);
            tabHost.setup(getActivity(), mainActivity.getSupportFragmentManager(), R.layout.fragment_new_message);

            tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Nieuw bericht"),
                    NewMessageFragment.class, null);

            tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Verzonden berichten"),
                    AllMessagesFragment.class, null);

            return tabHost;
        }

        return view;
    }

}
