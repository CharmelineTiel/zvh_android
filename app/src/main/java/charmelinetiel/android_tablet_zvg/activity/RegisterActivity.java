package charmelinetiel.android_tablet_zvg.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.fragments.LoginOrRegisterFragment;
import charmelinetiel.android_tablet_zvg.fragments.ResetPasswordFragment;
import charmelinetiel.android_tablet_zvg.models.User;

public class RegisterActivity extends AppCompatActivity{

    private User user;
    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        data = intent.getData();

        if(data != null){
            String token = data.getLastPathSegment();

            Bundle bundle = new Bundle();
            bundle.putString("token", token);

            Fragment fg = new ResetPasswordFragment();
            fg.setArguments(bundle);
            setFragment(fg);
        }else{

            Fragment fg = new LoginOrRegisterFragment();
            setFragment(fg);
        }

    }

    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = this.getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

    @Override
    public void onBackPressed() {

        finishAndRemoveTask ();
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser(){

        return user;
    }
}