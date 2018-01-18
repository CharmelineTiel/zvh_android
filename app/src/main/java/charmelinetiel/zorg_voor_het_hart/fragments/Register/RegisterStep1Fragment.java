package charmelinetiel.zorg_voor_het_hart.fragments.Register;


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
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.FormErrorHandling;
import charmelinetiel.zorg_voor_het_hart.models.User;


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
    private FormErrorHandling validateForm;
    private RegisterActivity registerActivity;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.getSupportActionBar().show();
        registerActivity.setTitle("Registreren stap 1 van 3");

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);
        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    setDate();
                }
            }
        });

        v.findViewById(R.id.firstBtn).setOnClickListener(this);
        v.findViewById(R.id.secondBtn).setOnClickListener(this);

        initViews();

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
                    User.getInstance().setLength(Integer.parseInt(length.getText().toString()));
                    User.getInstance().setWeight(Integer.parseInt(weight.getText().toString()));
                }catch (Exception e){


                }
                User.getInstance().setFirstname(firstName.getText().toString());
                User.getInstance().setLastname(lastName.getText().toString());
                User.getInstance().setDateOfBirth(dateOfBirth.getText().toString());
                User.getInstance().setGender(genderId);

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

        if(!validateForm.inputValidString(firstName)){

            firstName.setError("Vul uw voornaam in");
            return false;
        }
        if(!validateForm.inputValidString(lastName)){

            lastName.setError("Vul uw achternaam in");
            return false;
        }
        if(!validateForm.inputValidString(dateOfBirth)){

            dateOfBirth.setError("Vul uw geboortedatum in");
            return false;
        }

        if(!validateForm.inputValidLength(length.getText().toString().trim())){
            length.setError("Vul een geldige lengte in in gehele centimeters");
            return false;
        }
        if(!validateForm.inputValidWeight(weight.getText().toString().trim())){
            weight.setError("Vul een geldig gewicht in in gehele kilogrammen");
            return false;
        }

        return true;
    }

    public void setDate(){

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR) -65;
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (DatePicker view1, int year, int monthOfYear, int dayOfMonth) -> {
            monthOfYear += 1;
            String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth);
            dateOfBirth.setText(date);
        }, yy, mm, dd);
        datePicker.show();
    }

    private void initViews() {
        length = v.findViewById(R.id.length_input);
        weight = v.findViewById(R.id.weight_input);
        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);
        email = v.findViewById(R.id.email);
        gender = v.findViewById(R.id.radioGender);
    }

}
