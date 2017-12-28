package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandeling;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener {


    private View v;
    private APIService apiService;
    private User user;
    private Button btn1, btn2;
    private EditText email, pass1, pass2;
    private FormErrorHandeling validateForm;

    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle("Registreren stap 2 van 3");

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/"); //TODO fix this, DRY CODE FTW
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
                RegisterActivity activity = (RegisterActivity) getActivity();
                user = activity.getUser();

                if(email.getText().toString() != "" && pass1.getText().toString() != "" && pass1.getText().toString().equals(pass2.getText().toString())) {
                    user.setEmailAddress(email.getText().toString());

                    user.setPassword(pass1.getText().toString());
                }

                activity.setUser(user);

                if(validInput()) {
                    Fragment fg = new RegisterStep3Fragment();
                    setFragment(fg);
                }
                break;

            case R.id.backBtn:

                //go to previous fragment
                getFragmentManager().popBackStack();

                break;
        }
    }

    private boolean validInput(){

        validateForm = new FormErrorHandeling();

        if (!validateForm.InputValidEmail(email)){

            validateForm.showError("Geen geldige email");
            return false;
        }else if(!validateForm.inputNotEmpty(email)){

            validateForm.showError("Vul uw email in");
        }

        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = pass1.getText().toString();
                if (editable.length() > 0 && pass.length() > 0) {
                    if(!pass2.getText().toString().equals(pass)){

                        validateForm.showError("Uw wachtwoord komt niet overeen");
                    }

                }
            }
        });

        if (!validateForm.inputNotEmpty(pass1) || !validateForm.inputNotEmpty(pass2)){

            validateForm.showError("Vul uw wachtwoord in");
            return false;
        }
        return true;
    }

//TODO make setFragment static somehow, this method is being used in pretty much all the fragments.. DRY CODE FTW
    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

}
