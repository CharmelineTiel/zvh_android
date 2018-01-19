package charmelinetiel.zorg_voor_het_hart.fragments.Register;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.tooltip.Tooltip;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener {


    private View v;
    private ImageView infoToolTip;
    private EditText email, password1, password2;
    private RegisterActivity registerActivity;

    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.setTitle("Registreren stap 2 van 3");


        v = inflater.inflate(R.layout.fragment_register_step2, container, false);

        v.findViewById(R.id.nextScreenBtn).setOnClickListener(this);
        v.findViewById(R.id.returnButton).setOnClickListener(this);


        initViews();

        infoToolTip.setOnClickListener(v -> {

            Tooltip.Builder builder = new Tooltip.Builder(v, R.style.AppTheme)
                    .setCancelable(true)
                    .setDismissOnClick(false)
                    .setCornerRadius(8f)
                    .setPadding(30f)
                    .setMargin(20f)
                    .setGravity(Gravity.BOTTOM)
                    .setTextColor(getResources().getColor(R.color.whiteText))
                    .setBackgroundColor(getResources().getColor(R.color.mediumGrey))
                    .setText("Om in te kunnen loggen heeft u een email en een wachtwoord nodig");
            builder.show();
        });

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextScreenBtn:

                //update user information
                if(email.getText().toString() != "" && password1.getText().toString() != "" && password1.getText().toString().equals(password2.getText().toString())) {
                    User.getInstance().setEmailAddress(email.getText().toString());

                    User.getInstance().setPassword(password1.getText().toString());
                }


                if(validInput()) {

                    registerActivity.openFragment(new RegisterStep3Fragment());
                }
                break;

            case R.id.returnButton:

                //go to previous fragment
                getFragmentManager().popBackStack();

                break;
        }
    }

    private boolean validInput(){

        if (!registerActivity.formErrorHandler.InputValidEmail(email)){

            email.setError("Geen geldige email");
           return false;
        }else if(!registerActivity.formErrorHandler.inputValidString(email)){

            email.setError("Vul uw e-mail in");
            return false;
        }
        if (password1.getText().toString().trim().equals("")) {

            password1.setError("Vul een wachtwoord in");
            return false;
        }
        if (password2.getText().toString().trim().equals("")) {
            password2.setError("Herhaal uw wachtwoord in");
            return false;
        }

        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String pass = password1.getText().toString().trim();
                if (charSequence.length() > 0 && pass.length() > 0) {
                    if(!password2.getText().toString().trim().equals(pass)){

                        password2.setError("De ingevoerde wachtwoorden komen niet overeen.");

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = password1.getText().toString().trim();
                if (editable.length() > 0 && pass.length() > 0) {
                    if(!password2.getText().toString().trim().equals(pass)){

                        password2.setError("De ingevoerde wachtwoorden komen niet overeen.");

                    }

                }
            }
        });


            if (!password1.getText().toString().trim().equals(password2.getText().toString().trim())) {

                password2.setError("De ingevoerde wachtwoorden komen niet overeen.");

                return false;
            }


        return true;
    }

    private void initViews(){

        email = v.findViewById(R.id.email);
        password1 = v.findViewById(R.id.password1);
        password2 = v.findViewById(R.id.password2);
        infoToolTip = v.findViewById(R.id.toolTipLoginInfo);

    }

}

