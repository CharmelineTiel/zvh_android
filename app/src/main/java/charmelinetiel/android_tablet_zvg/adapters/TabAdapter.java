package charmelinetiel.android_tablet_zvg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import charmelinetiel.android_tablet_zvg.fragments.DiaryFragment;

/**
 * Created by C Tiel on 12/29/2017.
 */

public class TabAdapter  extends FragmentStatePagerAdapter {


    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DiaryFragment tab1 = new DiaryFragment();
                return tab1;
            case 1:
                DiaryFragment tab2 = new DiaryFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
