package charmelinetiel.zorg_voor_het_hart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.fragments.Diary.DiaryDetailFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Diary.DiaryFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Measurement.HomeFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Measurement.MeasurementStep2Fragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Message.ContactHostFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Message.MessageDetailFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Service.FAQFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Service.ServiceFragment;
import charmelinetiel.zorg_voor_het_hart.helpers.BottomNavigationView;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.HealthIssue;
import charmelinetiel.zorg_voor_het_hart.models.Measurement;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements  Callback {

    private Measurement measurement;
    private APIService apiService;
    private List<HealthIssue> healthIssues;
    private boolean isEditingMeasurement;
    public static ProgressBar progressBar;

    private android.support.design.widget.BottomNavigationView bottomNavigationView;

    android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //This listener is used to go to fragments by selecting the desired menu item in the bottom nav
            switch (item.getItemId()) {
                case R.id.measurement:
                    setTitle("Meting");
                    openFragment(new HomeFragment());
                    return true;
                case R.id.diary:
                    setTitle("Mijn Dagboek");
                    openFragment(new DiaryFragment());
                    return true;

                case R.id.contact:
                    setTitle("Contact");
                    openFragment(new ContactHostFragment());
                    return true;

                case R.id.settings:
                    setTitle("Service");
                    openFragment(new ServiceFragment());
                    return true;
            }
            return onContextItemSelected(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        measurement = new Measurement();
        setTitle("Meting");

        progressBar = findViewById(R.id.progressBar_cyclic);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        if (User.getInstance().getAuthToken() != null && ExceptionHandler.isConnectedToInternet(getApplicationContext())) {

            apiService.getAllHealthIssues(User.getInstance().getAuthToken()).enqueue(this);
        } else {

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        initBottomNav();
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public List<HealthIssue> getHealthIssues() {
        return healthIssues;
    }

    public void postMeasurement() {

        setDateOfLastMeasurement();
        apiService.postMeasurement(measurement, User.getInstance().getAuthToken()).enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful() && response.body() != null) {
            try {
                healthIssues = (List<HealthIssue>) response.body();
            } catch (Exception e) {
            }
        }
    }

    public void putMeasurement() {
        apiService.putMeasurement(measurement, User.getInstance().getAuthToken()).enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {

                setDateOfLastMeasurement();
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t)

            {
                try {
                    ExceptionHandler.exceptionThrower(new Exception());
                } catch (Exception e) {

                    makeSnackBar(ExceptionHandler.getMessage(e), MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onFailure(Call call, Throwable t) {

        try {
            ExceptionHandler.exceptionThrower(new Exception());
        } catch (Exception e) {

            makeSnackBar(ExceptionHandler.getMessage(e), MainActivity.this);
        }
    }

    public void setDateOfToday(TextView textView) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date customDate = new Date();

        if(!isEditingMeasurement()){
            textView.setText(getResources().getString(R.string.date,
                    simpleDateFormat.format(customDate)));
        }else{
            textView.setText(getResources().getString(R.string.editMeasurementDate,
                    simpleDateFormat.format(getMeasurement().getMeasurementDateTime())));
        }

    }

    public boolean isEditingMeasurement() {
        return isEditingMeasurement;
    }

    public void setEditingMeasurement(boolean editingMeasurement) {
        isEditingMeasurement = editingMeasurement;
    }

    public void makeSnackBar(String messageText, Activity fg) {
        Snackbar snackbar = Snackbar.make(fg.findViewById(R.id.parentLayout),
                messageText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void openFragment(final Fragment fg) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fg, fg.toString());
        ft.addToBackStack(fg.toString());
        ft.commit();

    }

    public String dateTimeNow(){

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void setDateOfLastMeasurement(){

        ServiceFragment.putPref(User.getInstance().getId() + "Date", dateTimeNow(), this);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        //If we're at the home fragment, exit the mainactivity
        if(currentFragment instanceof HomeFragment){
            finish();
        //If we're at a fragment we can move back from, go back
        }else if(currentFragment instanceof MeasurementStep2Fragment ||
                currentFragment instanceof DiaryDetailFragment ||
                currentFragment instanceof MessageDetailFragment ||
                currentFragment instanceof FAQFragment) {
            getSupportFragmentManager().popBackStack();
        //If we're somewhere else, go to home
        }else {
            bottomNavigationView.setSelectedItemId(R.id.measurement);
        }
    }

    public void initBottomNav() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationView.removeShiftMode(bottomNavigationView);
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
    public android.support.design.widget.BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
}
