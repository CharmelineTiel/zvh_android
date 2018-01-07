package charmelinetiel.android_tablet_zvg.models;

import android.widget.EditText;

/**
 * Created by C Tiel on 12/28/2017.
 */

public class FormErrorHandling {

    private EditText editText;

    public boolean inputGiven(EditText editText)
    {
        this.editText = editText;
        if("".equals(editText.getText().toString().trim())) {

            return false;
        }
        return true;
    }

    public boolean inputValidString(EditText editText){

        this.editText = editText;
        String regexString = "^[0-9]*$";
        if(editText.getText().toString().trim().matches(regexString))
        {
            return false;
        }

        return true;
    }

    public void showError(String errorMessage){

        editText.setError(errorMessage);
    }

    public boolean InputValidEmail(EditText editText){

        this.editText = editText;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches();

    }

    public boolean inputValidBloodPressure(EditText editText, boolean isUpperBloodPressure){

        int bloodPressure = 0;
        try {

            inputValidInt(editText.getText().toString());

        }catch (Exception e){
            return false;
        }

        if(isUpperBloodPressure && (bloodPressure > 250 || bloodPressure < 70)){
            return false;
        }
        else if(!isUpperBloodPressure && (bloodPressure > 150 || bloodPressure < 30)){
            return false;
        }

        return true;
    }

    public boolean inputValidInt(String text){


        try {

            Integer.parseInt(text);

        }catch (Exception e){
            return false;
        }

        return true;
    }
}
