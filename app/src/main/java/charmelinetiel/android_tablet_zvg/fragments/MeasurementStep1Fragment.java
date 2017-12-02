package charmelinetiel.android_tablet_zvg.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import charmelinetiel.android_tablet_zvg.R;


public class MeasurementStep1Fragment extends Fragment
        implements
        View.OnClickListener

{
    private View v;
    String string = "";
    static boolean verified = false;
    public MeasurementStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);



        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // save views as variables in this method
        // "view" is the one returned from onCreateView

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
