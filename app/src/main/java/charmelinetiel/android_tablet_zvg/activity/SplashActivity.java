package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, charmelinetiel.android_tablet_zvg.activity.RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
