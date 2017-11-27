package Youp.ZVH_Android.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.codevscolor.materialpreference.activity.MaterialPreferenceActivity;
import com.codevscolor.materialpreference.callback.MaterialPreferenceCallback;

import Youp.ZVH_Android.R;

public class ServiceActivity extends MaterialPreferenceActivity implements MaterialPreferenceCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    //initialization of the sdk should be done here
    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        setPreferenceChangedListener(this);

        useDarkTheme(true);

        setXmlResourceName("app_preferences");

    }

    /**
     * callback for preference changes
     *
     * @param sharedPreferences
     * @param name
     */
    @Override
    public void onPreferenceSettingsChanged(SharedPreferences sharedPreferences, String name) {
        Toast.makeText(this, "preference with key " + name + " changed", Toast.LENGTH_LONG).show();
    }
}
