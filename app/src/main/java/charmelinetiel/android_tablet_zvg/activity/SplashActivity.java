package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if Auto-login has been configured
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Check if user has been saved, if so go to mainactivity


        Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
