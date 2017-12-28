package charmelinetiel.android_tablet_zvg.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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
import charmelinetiel.android_tablet_zvg.models.User;


public class RegisterStep1Fragment extends Fragment
        implements View.OnClickListener

{

    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText email, length, weight;
    private String string = "";
    private Button btn1;
    private Button btn2;
    private RadioGroup gender;
    private Boolean allInputValid = false;
    private View v;
    private User user;


    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        (getActivity()).setTitle("Registreren stap 1 van 3");

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

    public boolean showError(EditText edit) {

        String regex = "[a-zA-Z0-9]\\w*";

        if (edit.getTag().equals("firstName") && !edit.getText().toString().equals((regex))){

            firstName.setError("Vul uw voornaam in");
            allInputValid = false;
        }
        else if(edit.getTag().equals("lastName")){
            lastName.setError("Vul uw voornaam in");
            allInputValid = false;
        }
        else if(edit.getTag().equals("dateOfBirth")){

            dateOfBirth.setError("Vul uw geboortedatum in");
            allInputValid = false;
        }else if (edit.getTag().equals("length_input")){

            length.setError("Vul uw lengte in");

        }else if(edit.getTag().equals("weight_input")){

            weight.setError("Vul uw weight in");
            allInputValid = false;
        }else{

            allInputValid = true;
        }

        return allInputValid;
    }

    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the Fragment is visible.

    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dateOfBirth:

                //set date of birth
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
                break;

            case R.id.secondBtn:



                int index = gender.indexOfChild(getActivity().findViewById(gender.getCheckedRadioButtonId()));
                int genderId;

                if(index == 0){
                    genderId = 1;
                }else{

                    genderId = 2;
                }

                try {
                    user.setLength(Integer.parseInt(length.getText().toString()));
                    user.setWeight(Integer.parseInt(weight.getText().toString()));
                }catch (Exception e){

                }
                user = new User();
                user.setFirstname(firstName.getText().toString());
                user.setLastname(lastName.getText().toString());
                user.setDateOfBirth(dateOfBirth.getText().toString());
                user.setGender(genderId);
                user.setDateOfBirth(dateOfBirth.getText().toString());

                RegisterActivity activity = (RegisterActivity) getActivity();
                activity.setUser(user);

                //ken wel beter eh


                    //go to registration step 2
                    Fragment fg = new RegisterStep2Fragment();
                    setFragment(fg);

                break;

            case R.id.firstBtn:
                getFragmentManager().popBackStack();
                break;
        }
    }


    private void validate(final EditText edit)
    {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                if("".equals(edit.getText().toString())) {

                    showError(edit);
                }
          }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(edit.getText().toString().equals("")) {

                    showError(edit);
                }
            }
        });

    }

    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }


}
