package charmelinetiel.zorg_voor_het_hart.fragments.Service;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.models.AlarmReceiver;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.models.UserLengthWeight;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int NOTIFICATION_ID = 0;
    private static final String TAG = "ServiceFragment";
    private NotificationManager mNotificationManager;
    private Preference logout, dailyReminder, sendWeekly, veelgesteldeVragen, disclaimer;
    private EditTextPreference editLength, editWeight;
    private MainActivity mainActivity;
    private FormErrorHandling formErrorHandling;
    private APIService apiService;
    private GoogleApiClient mGoogleApiClient;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.app_preferences);

        mainActivity = (MainActivity) getActivity();
        formErrorHandling = new FormErrorHandling();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);


        logout = findPreference("logout");
        dailyReminder= findPreference("dailyReminders");
        veelgesteldeVragen = findPreference("veelgesteldeVragen");
        disclaimer = findPreference("disclaimer");
        editLength = (EditTextPreference) findPreference("editLength");
        editWeight = (EditTextPreference) findPreference("editWeight");

        editLength.setText(User.getInstance().getLength().toString());
        editWeight.setText(User.getInstance().getWeight().toString());

        editWeight.setSummary("Uw gewicht: " + User.getInstance().getWeight().toString());
        editLength.setSummary("Uw lengte: " + User.getInstance().getLength().toString());

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

                AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
                alertDialog.setTitle("Uitloggen");
                alertDialog.setMessage("Weet u zeker dat u wilt uitloggen?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Uitloggen",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Auth.CredentialsApi.disableAutoSignIn(mGoogleApiClient);
                                Intent myIntent = new Intent(mainActivity, RegisterActivity.class);
                                mainActivity.startActivity(myIntent);
                                mainActivity.finish();
                                User.getInstance().setAuthToken(null);

                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuleren", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();



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

        editLength.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newLength) {

                if (preference instanceof EditTextPreference){
                    EditTextPreference length =  (EditTextPreference)preference;
                    if (length.getText().trim().length() > 0 && formErrorHandling.inputValidInt(newLength.toString())){
                        length.setSummary("Uw lengte: " + newLength.toString());
                        editLength.setText(newLength.toString());

                        UserLengthWeight userLength = new UserLengthWeight();

                        try {
                            userLength.setLength(Integer.parseInt(newLength.toString()));
                        }catch (Exception e)
                        {

                        }


                        apiService.updateUserLenghtWeight(userLength, User.getInstance().getAuthToken()).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                if (response.body() != null && response.isSuccessful()){

                                }else{

                                    mainActivity.makeSnackBar("Er is iets fout gegaan, probeer het opnieuw", mainActivity);

                                }


                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

                        Toast.makeText(getActivity(), "Uw lengte is aangepast", Toast.LENGTH_SHORT)
                                .show();
                    }else{


                        length.setSummary("Uw lengte: " +  editLength.getText());
                    }
                }
                return false;
            }
        });

        editWeight.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newWeight) {

                if (preference instanceof EditTextPreference){
                    EditTextPreference weight =  (EditTextPreference)preference;
                    if (weight.getText().trim().length() > 0){

                        weight.setSummary("Uw gewicht: " + newWeight);
                        editWeight.setText(newWeight.toString());

                        UserLengthWeight userWeight = new UserLengthWeight();

                        try {
                            userWeight.setWeight(Integer.parseInt(newWeight.toString()));
                        }catch (Exception e){

                        }

                        apiService.updateUserLenghtWeight(userWeight, User.getInstance().getAuthToken()).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                if (response.body() != null && response.isSuccessful()){

                                }else{

                                    mainActivity.makeSnackBar("Er is iets fout gegaan, probeer het opnieuw", mainActivity);

                                }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                        Toast.makeText(getActivity(), "Uw gewicht is aangepast", Toast.LENGTH_SHORT)
                                .show();
                    }else{
                        weight.setSummary("Uw gewicht: " + editWeight.getText());
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {

        //hmm
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "GoogleApiClient connected");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "GoogleApiClient is suspended with cause code: " + cause);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "GoogleApiClient failed to connect: " + connectionResult);
    }
}
