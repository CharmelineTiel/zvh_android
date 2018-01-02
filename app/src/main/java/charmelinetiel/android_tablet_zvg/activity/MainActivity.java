package charmelinetiel.android_tablet_zvg.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.fragments.ContactFragment;
import charmelinetiel.android_tablet_zvg.fragments.DiaryFragment;
import charmelinetiel.android_tablet_zvg.fragments.HomeFragment;
import charmelinetiel.android_tablet_zvg.fragments.ServiceFragment;
import charmelinetiel.android_tablet_zvg.helpers.BottomNavigationViewHelper;
import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.models.UserLengthWeight;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements  Callback {

    private Fragment fg;
    private Measurement measurement;
    private APIService apiService;
    private List<HealthIssue> healthIssues;
    private List<Measurement> measurements;
    private User user;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        measurement = new Measurement();
        setTitle("Meting");
        Intent intent = getIntent();
        setUser(intent.getParcelableExtra("user"));
        setContentView(R.layout.activity_main);


        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        if (getUser().getAuthToken() != null) {
            apiService.getAllHealthIssues(getUser().getAuthToken()).enqueue(this);
        }else{

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);

            loadMeasurements();
        }

        initBottomNav();

    }



    @Override
    public void onBackPressed() {

        finishAndRemoveTask ();
    }

    public void setFragment(Fragment fg)
{
    FragmentTransaction fgTransition = getSupportFragmentManager().beginTransaction();
    fgTransition.replace(R.id.content, fg);
    fgTransition.addToBackStack("backHome");
    fgTransition.commit();
}
    public void initBottomNav()
    {

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.measurement:

                        setTitle("Meting");
                        fg = new HomeFragment();
                        setFragment(fg);
                        return true;
                    case R.id.diary:
                        setTitle("Mijn Dagboek");
                        fg = new DiaryFragment();
                        setFragment(fg);
                        return true;

                    case R.id.contact:
                        setTitle("Contact");
                        fg = new ContactFragment();
                        setFragment(fg);
                        return true;

                    case R.id.settings:
                        setTitle("Service");
                        fg = new ServiceFragment();
                        setFragment(fg);
                        return true;
                }
                return false;
            }
        };
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            //icon height and weight
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        bottomNavigationView.setSelectedItemId(R.id.measurement);

    }

    public void loadMeasurements()
    {
        apiService.getMeasurements(user.getAuthToken()).enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                if(response.isSuccessful() && response.body() != null){
                    try{
                        measurements = response.body();
                    }catch (Exception e){
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {

            }
        });

    }
    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void updateUserLengthWeight(int length, int weight){
        UserLengthWeight lenghtWeight = new UserLengthWeight(length, weight);
        apiService.updateUserLenghtWeight(lenghtWeight, getUser().getAuthToken()).enqueue(this);
    }

    public List<HealthIssue> getHealthIssues(){
        return healthIssues;
    }

    public void postMeasurement(){
        apiService.postMeasurement(measurement,getUser().getAuthToken()).enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful() && response.body() != null){
            try{
                healthIssues = (List<HealthIssue>) response.body();
            }catch (Exception e){
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
    public User getUser(){
        return user;
    }

    public void setUser(User user){

        this.user = user;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }


    public void setDateOfToday(TextView textView){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date customDate = new Date();
        textView.setText("Datum van vandaag: " + " " + simpleDateFormat.format(customDate));

    }

    public void sendNotification(View view) {

        //Get an instance of NotificationManager//
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "Reminder")
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Meting herinnering")
                        .setContentText("Het is weer tijd voor uw meting!");


        //Create the intent that’ll fire when the user taps the notification//

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar timeToAlert = Calendar.getInstance();
        Calendar currentTimeCal = Calendar.getInstance();

        timeToAlert.set(Calendar.HOUR, 2);
        timeToAlert.set(Calendar.MINUTE, 35);
        timeToAlert.set(Calendar.SECOND, 0);

        long alertTime = timeToAlert.getTimeInMillis();
        long currentTime = currentTimeCal.getTimeInMillis();

        if (alertTime >= currentTime){

            alarmManager.setRepeating(AlarmManager.RTC, alertTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }else{

            timeToAlert.add(Calendar.DAY_OF_MONTH, 1);
            alertTime = timeToAlert.getTimeInMillis();
            alarmManager.setRepeating(AlarmManager.RTC, alertTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        mBuilder.setContentIntent(pendingIntent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
