package Youp.ZVH_Android.activity;

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

import Youp.ZVH_Android.R;
import Youp.ZVH_Android.fragments.ContactFragment;
import Youp.ZVH_Android.fragments.DiaryFragment;
import Youp.ZVH_Android.fragments.HomeFragment;
import Youp.ZVH_Android.fragments.ServiceFragment;
import Youp.ZVH_Android.helpers.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private Fragment fg;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        customizeNav();

        setTitle("Meting");
        fg = new HomeFragment();
        setFragment(fg);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
            finish();
        } else {
            getFragmentManager().popBackStack();
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

}
