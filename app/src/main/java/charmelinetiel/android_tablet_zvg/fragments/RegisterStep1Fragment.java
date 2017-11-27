package charmelinetiel.android_tablet_zvg.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;


public class RegisterStep1Fragment extends Fragment
        implements BlockingStep,
        View.OnClickListener

{
    private View v;
    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText email;
    private List<EditText> form;
    String string = "";
    static boolean verified = false;
    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // save views as variables in this method
        // "view" is the one returned from onCreateView

        form = new ArrayList<EditText>();

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        dateOfBirth = view.findViewById(R.id.dateOfBirth);
        email = view.findViewById(R.id.email);


        validate(firstName);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {

        VerificationError err;
        validate(firstName);
        if (!verified){

            err = new VerificationError("Vul uw voornaam in");
            return err;
        }else
        {
            err = null;
            return err;
        }

    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {

        if (firstName.getText().toString() == ""){
            firstName.setError(error.getErrorMessage());
        }else if(lastName.getText().toString() == "")
        {
            lastName.setError(error.getErrorMessage());
        }else if(dateOfBirth.getText().toString() == "")
        {
            dateOfBirth.setError(error.getErrorMessage());
        }else if(email.getText().toString() == "")
        {
            email.setError(error.getErrorMessage());
        }
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            callback.goToNextStep();

            }
        }, 50L);
    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.complete();
            }
        }, 500L);
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

        callback.goToPrevStep();
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
    public void onClick(final View v) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.valueOf(year) +"-"+String.valueOf(monthOfYear)
                        +"-"+String.valueOf(dayOfMonth);
                EditText DateOfBirth = v.findViewById(R.id.dateOfBirth);
                DateOfBirth.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }


    private void validate(final EditText edit)
    {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                string = s.toString();
                if (string == "" || string.isEmpty() || string.length() <= 0){

                    verified = false;
                }else{

                    verified = true;
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });
    }
}
