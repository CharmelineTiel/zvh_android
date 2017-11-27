package Youp.ZVH_Android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

import Youp.ZVH_Android.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends PreferenceFragmentCompat {


    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.app_preferences);
    }

}
