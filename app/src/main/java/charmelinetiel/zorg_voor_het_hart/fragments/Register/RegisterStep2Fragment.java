package charmelinetiel.zorg_voor_het_hart.fragments.Register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener {


    private View v;
    private APIService apiService;
    private Button btn1, btn2;
    private EditText email, pass1, pass2;
    private FormErrorHandling validateForm;
    private RegisterActivity registerActivity;

    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.setTitle("Registreren stap 2 van 3");

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        v = inflater.inflate(R.layout.fragment_register_step2, container, false);


        btn1 = v.findViewById(R.id.nextScreenBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);

        email = v.findViewById(R.id.email);
        pass1 = v.findViewById(R.id.pass1);
        pass2 = v.findViewById(R.id.pass2);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextScreenBtn:

                //update user information

                if(email.getText().toString() != "" && pass1.getText().toString() != "" && pass1.getText().toString().equals(pass2.getText().toString())) {
                    User.getInstance().setEmailAddress(email.getText().toString());

                    User.getInstance().setPassword(pass1.getText().toString());
                }


                if(validInput()) {

                    registerActivity.openFragment(new RegisterStep3Fragment());
                }
                break;

            case R.id.backBtn:

                //go to previous fragment
                getFragmentManager().popBackStack();

                break;
        }
    }

    private boolean validInput(){

        validateForm = new FormErrorHandling();

        if (!validateForm.InputValidEmail(email)){

            validateForm.showError("Geen geldige email");
           return false;
        }else if(!validateForm.inputGiven(email)){

            validateForm.showError("Vul uw e-mail in");
            return false;
        }

        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String pass = pass1.getText().toString();
                if (charSequence.length() > 0 && pass.length() > 0) {
                    if(!pass2.getText().toString().equals(pass)){

                        validateForm.showError("Uw wachtwoord komt niet overeen");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = pass1.getText().toString();
                if (editable.length() > 0 && pass.length() > 0) {
                    if(!pass2.getText().toString().equals(pass)){

                        validateForm.showError("De ingevoerde wachtwoorden komen niet overeen.");
                    }

                }
            }
        });

        if (!validateForm.inputGiven(pass1)) {

            validateForm.showError("Vul een wachtwoord in");
            return false;
        }
        if (!validateForm.inputGiven(pass2)){
            validateForm.showError("Herhaal uw wachtwoord in");
            return false;
        }else if(!pass1.getText().toString().equals(pass2.getText().toString())){

            validateForm.showError("De ingevoerde wachtwoorden komen niet overeen.");
            return false;
        }


        return true;
    }

}
