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
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandler;

public class RegisterActivity extends AppCompatActivity{

    private Snackbar snackbar;
    private Uri data;
    private Bundle bundle;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public FormErrorHandler formErrorHandler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        formErrorHandler = FormErrorHandler.getInstance();

        //Get the intent and data to determine if the activity was entered by deep link
        intent = getIntent();
        data = intent.getData();

        //If the activity was entered through deep link, open the ResetPassword fragment
        if(data != null){
            setTitle(getResources().getString(R.string.restorePassword));
            String token = data.getLastPathSegment();

            bundle = new Bundle();
            bundle.putString("token", token);

            fragment = new ResetPasswordFragment();
            fragment.setArguments(bundle);
            openFragment(fragment);

        }else{

            openFragment(new LoginOrRegisterFragment());
        }
    }

    public void openFragment(final Fragment fg)
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fg, fg.toString());
        fragmentTransaction.addToBackStack(fg.toString());

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

            //if only LoginOrRegisterFragment is in the backstack, close the app
        if (count == 1) {
            finish();
            //additional code
        }else if(count == 5){
            //go back to login or register
            for(int i = 0; i < count; i++){
                getSupportFragmentManager().popBackStack();
            }
            openFragment(new LoginOrRegisterFragment());
        }else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void makeSnackBar(String messageText, Activity fg)
    {
        snackbar = Snackbar.make(fg.findViewById(R.id.parentLayout),
                messageText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}