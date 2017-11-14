package charmelinetiel.android_tablet_zvg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.measurement:
                    setTitle("Meting");
                    HomeFragment fg = new HomeFragment();
                    FragmentTransaction fgTransition = getSupportFragmentManager().beginTransaction();
                    fgTransition.replace(R.id.content, fg, "Meting");
                    fgTransition.commit();
                    return true;
                case R.id.diary:
                    setTitle("Mijn Dagboek");
                    DiaryFragment fg2 = new DiaryFragment();
                    FragmentTransaction fgTransition2 = getSupportFragmentManager().beginTransaction();
                    fgTransition2.replace(R.id.content, fg2, "Meting");
                    fgTransition2.commit();
                    return true;

                case R.id.contact:
                    setTitle("Contact");
                    DiaryFragment fg3 = new DiaryFragment();
                    FragmentTransaction fgTransition3 = getSupportFragmentManager().beginTransaction();
                    fgTransition3.replace(R.id.content, fg3, "Meting");
                    fgTransition3.commit();
                    return true;

                case R.id.settings:
                    setTitle("Service");
                    DiaryFragment fg4 = new DiaryFragment();
                    FragmentTransaction fgTransition4 = getSupportFragmentManager().beginTransaction();
                    fgTransition4.replace(R.id.content, fg4, "Meting");
                    fgTransition4.commit();
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customizeNav();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void customizeNav()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setShiftingMode(false);
            item.setChecked(item.getItemData().isChecked());

            //icon height and weight
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }
}
