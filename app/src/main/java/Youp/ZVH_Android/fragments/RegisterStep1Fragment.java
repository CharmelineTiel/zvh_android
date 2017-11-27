package Youp.ZVH_Android.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;

import Youp.ZVH_Android.R;


public class RegisterStep1Fragment extends Fragment
        implements BlockingStep,
        View.OnClickListener

{
    private View v;
    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText email;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);

        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);
        email = v.findViewById(R.id.email);
        return v;

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {

        firstName = v.findViewById(R.id.firstName);
        String asd = firstName.getText().toString();
        Toast.makeText(this.getContext(), asd, Toast.LENGTH_LONG);
//        VerificationError err;
//        if(TextUtils.isEmpty(lastName.getText().toString())) {
//
//            err = new VerificationError("Vul uw voornaam in");
//
//        }else{
//
//            err = null;
//        }

        return null;


    }

    @Override
    public void onSelected() {


    }

    @Override
    public void onError(@NonNull VerificationError error) {

//        if (firstName.getText() == null) {
//
//            firstName.setError(error.getErrorMessage());
//
//        } else if (lastName.getText() == null) {
//
//            lastName.setError(error.getErrorMessage());
//
//        } else if (dateOfBirth.getText() == null) {
//            dateOfBirth.setError(error.getErrorMessage());
//
//        } else if (email.getText() == null) {
//            email.setError(error.getErrorMessage());
//        }

       Toast.makeText(this.getContext(), "onError! -> " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();

        //handle error inside of the fragment, e.g. show error on EditText
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
}
