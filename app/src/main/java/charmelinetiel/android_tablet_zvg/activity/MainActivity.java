package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.measurement:

                    setTitle("Meting");
                    fg = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", user);
                    fg.setArguments(bundle);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        measurement = new Measurement();

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        apiService.getAllHealthIssues().enqueue(this);


        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        customizeNav();

        bottomNavigationView.setSelectedItemId(R.id.measurement);

    }


    @Override
    public void onBackPressed() {

        int count = fg.getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
            finish();
        } else {
            fg.getFragmentManager().popBackStack();
        }
    }

    public void setFragment(Fragment fg)
{
    FragmentTransaction fgTransition = getSupportFragmentManager().beginTransaction();
    fgTransition.replace(R.id.content, fg);
    fgTransition.addToBackStack("backHome");
    fgTransition.commit();
}
    public void customizeNav()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
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
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void updateUserLengthWeight(int length, int weight){
        UserLengthWeight lenghtWeight = new UserLengthWeight(length, weight);
        apiService.updateUserLenghtWeight(lenghtWeight).enqueue(this);
    }

    public List<HealthIssue> getHealthIssues(){
        return healthIssues;
    }

    public void postMeasurement(){
        apiService.postMeasurement(measurement,"5a0ad4ac9b582a11641e12f6").enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful() && response.body() != null){
            try{
                healthIssues = (List<HealthIssue>) response.body();
            }catch (Exception e){
//                Measurement responseMeasurement = (Measurement) response.body();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
