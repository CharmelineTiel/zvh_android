package charmelinetiel.android_tablet_zvg.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codevscolor.materialpreference.activity.MaterialPreferenceActivity;
import com.codevscolor.materialpreference.callback.MaterialPreferenceCallback;
import com.codevscolor.materialpreference.util.MaterialPrefUtil;

public class SettingsActivity extends MaterialPreferenceActivity implements MaterialPreferenceCallback {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        //use dark theme or not . Default is light theme
        useDarkTheme(true);

        //set primary color
        setPrimaryColor(MaterialPrefUtil.COLOR_CYAN);

        //default secondary color for tinting widgets, if no secondary color is used yet
        setDefaultSecondaryColor(this, MaterialPrefUtil.COLOR_CYAN);

    }

    @Override
    public void onPreferenceSettingsChanged(SharedPreferences sharedPreferences, String name) {

    }
}
