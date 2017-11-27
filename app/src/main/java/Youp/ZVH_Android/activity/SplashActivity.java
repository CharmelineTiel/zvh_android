package Youp.ZVH_Android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Youp.ZVH_Android.activity.RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
