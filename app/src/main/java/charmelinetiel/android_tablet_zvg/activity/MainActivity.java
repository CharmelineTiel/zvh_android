package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
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

import java.text.SimpleDateFormat;
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

    private Measurement measurement;
    private APIService apiService;
    private List<HealthIssue> healthIssues;
    private User user;
    private boolean isEditingMeasurement;
    public static ProgressBar progressBar;


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
                    openFragment(new ContactFragment());
                    return true;

                case R.id.settings:
                    setTitle("Service");
                    openFragment(new ServiceFragment());
                    return true;
            }


            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        measurement = new Measurement();
        setTitle("Meting");
        Intent intent = getIntent();
        setUser(intent.getParcelableExtra("user"));

        progressBar = findViewById(R.id.progressBar_cyclic);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        if (getUser().getAuthToken() != null) {
            apiService.getAllHealthIssues(getUser().getAuthToken()).enqueue(this);
        }else{

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }



        initBottomNav();

    }

    public void openFragment(final Fragment fg)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fg, fg.toString());
        ft.addToBackStack(fg.toString());
        ft.commit();
    }

    @Override
    public void onBackPressed() {

        finishAndRemoveTask ();
    }


    public void initBottomNav()
    {
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

    public void putMeasurement(){
        apiService.putMeasurement(measurement, getUser().getAuthToken()).enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                String a = "a";
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                String a = "b";
            }
        });
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

    public void setDateOfToday(TextView textView){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date customDate = new Date();
        textView.setText("Datum van vandaag: " + " " + simpleDateFormat.format(customDate));

    }

    public boolean isEditingMeasurement() {
        return isEditingMeasurement;
    }

    public void setEditingMeasurement(boolean editingMeasurement) {
        isEditingMeasurement = editingMeasurement;
    }


}
