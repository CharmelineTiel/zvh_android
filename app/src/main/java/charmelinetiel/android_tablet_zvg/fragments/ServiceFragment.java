package charmelinetiel.android_tablet_zvg.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.app_preferences);


        Preference logout = findPreference("logout");
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
                return true;
            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals("kjh")) {
            Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }

    }

}
