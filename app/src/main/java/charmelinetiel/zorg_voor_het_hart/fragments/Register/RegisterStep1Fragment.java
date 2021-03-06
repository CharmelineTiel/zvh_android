package charmelinetiel.zorg_voor_het_hart.fragments.Register;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.tooltip.Tooltip;

import java.util.Calendar;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.models.User;


public class RegisterStep1Fragment extends Fragment
        implements View.OnClickListener

{
    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText length, weight;
    private RadioGroup gender;
    private ImageView infoToolTip;
    private View v;
    private RegisterActivity registerActivity;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.getSupportActionBar().show();
        registerActivity.setTitle("Registreren stap 1 van 3");

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);


        initViews();


        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dateOfBirth:

                setDate();

                break;

            case R.id.nextButton:

                int index = gender.indexOfChild(registerActivity.findViewById(gender.getCheckedRadioButtonId()));
                int genderId;

                if (index == 0) {
                    genderId = 1;
                } else {

                    genderId = 2;
                }

                try {
                    User.getInstance().setLength(Integer.parseInt(length.getText().toString()));
                    User.getInstance().setWeight(Integer.parseInt(weight.getText().toString()));
                } catch (Exception e) {


                }
                User.getInstance().setFirstname(firstName.getText().toString());
                User.getInstance().setLastname(lastName.getText().toString());
                User.getInstance().setDateOfBirth(dateOfBirth.getText().toString());
                User.getInstance().setGender(genderId);

                if (validInput()) {

                    registerActivity.openFragment(new RegisterStep2Fragment());
                }
                break;

            case R.id.cancelButton:

                getFragmentManager().popBackStack();
                break;
        }
    }


    private boolean validInput() {

        if (!registerActivity.formErrorHandler.inputValidString(firstName)) {

            firstName.setError("Vul uw voornaam in");
            return false;
        }
        if (!registerActivity.formErrorHandler.inputValidString(lastName)) {

            lastName.setError("Vul uw achternaam in");
            return false;
        }
        if (!registerActivity.formErrorHandler.inputValidString(dateOfBirth)) {

            dateOfBirth.setError("Vul uw geboortedatum in");
            return false;
        }
        if (!registerActivity.formErrorHandler.inputValidLength(length.getText().toString().trim())) {
            length.setError("Vul een geldige lengte in in gehele centimeters");
            return false;
        }
        if (!registerActivity.formErrorHandler.inputValidWeight(weight.getText().toString().trim())) {
            weight.setError("Vul een geldig gewicht in in gehele kilogrammen");
            return false;
        }

        return true;
    }

    public void setDate() {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR) - 65;
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (DatePicker view1, int year, int monthOfYear, int dayOfMonth) -> {
            monthOfYear += 1;
            String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(year);
            dateOfBirth.setText(date);
        }, yy, mm, dd);
        datePicker.show();
    }

    private void initViews() {
        length = v.findViewById(R.id.length_input);
        weight = v.findViewById(R.id.weight_input);
        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);
        gender = v.findViewById(R.id.radioGender);
        infoToolTip = v.findViewById(R.id.toolTipPersonalInfo);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);
        dateOfBirth.setOnFocusChangeListener((view, b) -> {
            if (b) {
                setDate();
            }
        });

        v.findViewById(R.id.cancelButton).setOnClickListener(this);
        v.findViewById(R.id.nextButton).setOnClickListener(this);


        infoToolTip.setOnClickListener(v -> {

            Tooltip.Builder builder = new Tooltip.Builder(v, R.style.AppTheme)
                    .setCancelable(true)
                    .setDismissOnClick(false)
                    .setCornerRadius(8f)
                    .setPadding(30f)
                    .setMargin(10f)
                    .setTextColor(getResources().getColor(R.color.whiteText))
                    .setBackgroundColor(getResources().getColor(R.color.mediumGrey))
                    .setGravity(Gravity.BOTTOM)
                    .setText("Om u zo goed mogelijk te kunnen ondersteunen vragen wij u om enkele persoonsgegevens");
            builder.show();
        });
    }

}
