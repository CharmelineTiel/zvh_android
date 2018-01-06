package charmelinetiel.android_tablet_zvg.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;
import charmelinetiel.android_tablet_zvg.models.ExceptionHandler;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandling;
import charmelinetiel.android_tablet_zvg.models.User;


public class RegisterStep1Fragment extends Fragment
        implements View.OnClickListener

{

    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText email, length, weight;
    private Button btn1, btn2;
    private RadioGroup gender;
    private View v;
    private User user;
    private FormErrorHandling validateForm;
    private RegisterActivity registerActivity;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();

        registerActivity.setTitle("Registreren stap 1 van 3");

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);

        btn1 = v.findViewById(R.id.firstBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.secondBtn);
        btn2.setOnClickListener(this);

        length = v.findViewById(R.id.length_input);
        weight = v.findViewById(R.id.weight_input);
        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);
        email = v.findViewById(R.id.email);
        gender = v.findViewById(R.id.radioGender);

        return v;

    }

    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the DiaryMonthFragment is visible.

    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dateOfBirth:

                setDate();

                break;

            case R.id.secondBtn:

                int index = gender.indexOfChild(registerActivity.findViewById(gender.getCheckedRadioButtonId()));
                int genderId;

                if(index == 0){
                    genderId = 1;
                }else{

                    genderId = 2;
                }


                try {
                    ExceptionHandler.exceptionThrower(new NumberFormatException());
                    user.setLength(Integer.parseInt(length.getText().toString()));
                    user.setWeight(Integer.parseInt(weight.getText().toString()));
                }catch (Exception e){

                    registerActivity.makeSnackBar(ExceptionHandler.getMessage(e), registerActivity);

                }
                user = new User();
                user.setFirstname(firstName.getText().toString());
                user.setLastname(lastName.getText().toString());
                user.setDateOfBirth(dateOfBirth.getText().toString());
                user.setGender(genderId);
                user.setDateOfBirth(dateOfBirth.getText().toString());

                RegisterActivity activity = (RegisterActivity) getActivity();
                activity.setUser(user);

                    if(validInput()) {

                        registerActivity.openFragment(new RegisterStep2Fragment());
                    }
                break;

            case R.id.firstBtn:

                getFragmentManager().popBackStack();
                break;
        }
    }


    private boolean validInput()
    {
        validateForm = new FormErrorHandling();

        if(!validateForm.inputGiven(firstName)){

            validateForm.showError("Vul uw voornaam in");
            return false;
        }else if(!validateForm.inputValidString(firstName)) {

            validateForm.showError("Geen geldige voornaam");
            return false;
        }

        if(!validateForm.inputGiven(lastName)){

            validateForm.showError("Vul uw achternaam in");
            return false;
        }else if(!validateForm.inputValidString(lastName)) {
            validateForm.showError("Geen geldige achternaam");
        }

        if(!validateForm.inputGiven(dateOfBirth)){

            validateForm.showError("Vul uw geboortedatum in");
            return false;
        }

        if(!validateForm.inputGiven(length)){

            validateForm.showError("Vul uw lengte in");
            return false;
        }

        if(!validateForm.inputGiven(weight)){

            validateForm.showError("Vul uw gewicht in");
            return false;
        }

        return true;
    }

    public void setDate(){

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (DatePicker view1, int year, int monthOfYear, int dayOfMonth) -> {
            String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth);
            dateOfBirth.setText(date);
        }, yy, mm, dd);
        datePicker.show();
    }

}
