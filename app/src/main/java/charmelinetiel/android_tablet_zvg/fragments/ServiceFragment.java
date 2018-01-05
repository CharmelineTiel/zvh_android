package charmelinetiel.android_tablet_zvg.fragments;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;
import charmelinetiel.android_tablet_zvg.models.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private Preference logout, dailyReminder, sendWeekly, veelgesteldeVragen, disclaimer;
    private MainActivity mainActivity;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.app_preferences);

        mainActivity = (MainActivity) getActivity();

        logout = findPreference("logout");
        dailyReminder= findPreference("dailyReminders");
        veelgesteldeVragen = findPreference("veelgesteldeVragen");
        disclaimer = findPreference("disclaimer");

        getActivity().setTheme(R.style.preferenceTheme);

        //initialize notificationManager and alarmManager
        mNotificationManager = (NotificationManager) (getActivity()).getSystemService(NOTIFICATION_SERVICE);
        final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        //Set up the Notification Broadcast Intent
        Intent notifyIntent = new Intent(getActivity(), AlarmReceiver.class);

        //Set up the PendingIntent for the AlarmManager
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getActivity(), NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent myIntent = new Intent(mainActivity, RegisterActivity.class);
                mainActivity.startActivity(myIntent);
                mainActivity.finish();
                mainActivity.setUser(null);

                return true;
            }
        });

        dailyReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String toastMessage;
                boolean isReminderOn = (Boolean) newValue;
                if (isReminderOn) {


                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(400);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + AlarmManager.INTERVAL_FIFTEEN_MINUTES / 14;

                    //TODO change to daily, it is set to 1 minute for testing purposes
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES / 14;

                    //If the Toggle is turned on, set the repeating reminder with a 15 minute interval
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime, repeatInterval, notifyPendingIntent);

                    //Set the toast message for the "on" case
                    toastMessage = "Dagelijkse herinnering aan";
                } else {
                    //Cancel the notification if the reminder is turned off
                    alarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();

                    //Set the toast message for the "off" case
                    toastMessage = "Dagelijkse herinnering uit";
                }

                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT)
                        .show();


                return true;

            }
        });

        veelgesteldeVragen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Fragment fg = new FAQFragment();
                mainActivity.openFragment(fg);

                return true;
            }
        });

        disclaimer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Fragment fg = new DisclaimerFragment();
                mainActivity.openFragment(fg);

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
