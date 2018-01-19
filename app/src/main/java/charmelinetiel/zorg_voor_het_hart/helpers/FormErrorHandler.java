package charmelinetiel.zorg_voor_het_hart.helpers;

import android.widget.EditText;

/**
 * Created by C Tiel on 12/28/2017.
 */

public class FormErrorHandler {

    private static FormErrorHandler formErrorHandler = null;

    private FormErrorHandler() { }
    public static FormErrorHandler getInstance(){

        if(formErrorHandler == null) {
            formErrorHandler = new FormErrorHandler();
        }
        return formErrorHandler;
    }

   public static boolean inputValidString(EditText editText){

        String regexString = "^[0-9]*$";
        if(editText.getText().toString().trim().matches(regexString) ||
                "".equals(editText.getText().toString().trim()))
        {
            return false;
        }

        return true;
    }


    public static boolean InputValidEmail(EditText editText){

        return android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches();

    }

    public static boolean inputValidBloodPressure(EditText editText, boolean isUpperBloodPressure){

        int bloodPressure;

        try {

            bloodPressure = Integer.parseInt(editText.getText().toString().trim());

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

    public static boolean inputValidLength(String lengthString){
        int length;

        try {

            length = Integer.parseInt(lengthString.trim());

        }catch (Exception e){
            return false;
        }

        return (length >= 80 && length <= 230);
    }

    public static boolean inputValidWeight(String weightString){
        int weight;
        try {

            weight = Integer.parseInt(weightString.trim());

        }catch (Exception e){
            return false;
        }

        return (weight >= 30 && weight <= 350);
    }

}
