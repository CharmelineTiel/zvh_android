package charmelinetiel.zorg_voor_het_hart.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.fragments.Login.LoginOrRegisterFragment;
import charmelinetiel.zorg_voor_het_hart.fragments.Login.ResetPasswordFragment;

public class RegisterActivity extends AppCompatActivity{

    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        //Get the intent and data to determine if the activity was entered by deep link
        Intent intent = getIntent();
        data = intent.getData();

        //If the activity was entered through deep link, open the ResetPassword fragment
        if(data != null){
            String token = data.getLastPathSegment();

            Bundle bundle = new Bundle();
            bundle.putString("token", token);

            Fragment fg = new ResetPasswordFragment();
            fg.setArguments(bundle);
            openFragment(fg);

        }else{

            openFragment(new LoginOrRegisterFragment());

        }

    }

    public void openFragment(final Fragment fg)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, fg, fg.toString());

            ft.addToBackStack(fg.toString());

        ft.commit();
    }

    @Override
    public void onBackPressed() {

    }

    public void makeSnackBar(String messageText, Activity fg)
    {
        Snackbar snackbar = Snackbar.make(fg.findViewById(R.id.parentLayout),
                messageText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}