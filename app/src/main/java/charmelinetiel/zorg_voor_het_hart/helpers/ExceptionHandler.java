package charmelinetiel.zorg_voor_het_hart.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by C Tiel on 1/5/2018.
 */

public class ExceptionHandler {

    private static ExceptionHandler exceptionHandler = null;

    private ExceptionHandler() { }
    public static ExceptionHandler getInstance(){

        if(exceptionHandler == null) {
            exceptionHandler = new ExceptionHandler();
        }
        return exceptionHandler;
    }

    public String getMessage(Exception ex) {
        String message;

        if (ex instanceof ArrayIndexOutOfBoundsException) {
            message = "array out of bound exception";
        } else if (ex instanceof ClassNotFoundException) {
            message = "class not found exception";
        } else if (ex instanceof IndexOutOfBoundsException) {
            message = "index out of bounds exception";
        } else if (ex instanceof NullPointerException) {
            message = "Null pointer exception";
        } else if (ex instanceof NumberFormatException) {
            message = "number format exception";
        } else if (ex instanceof RuntimeException) {
            message = "runtime Exception";
        } else if(ex instanceof ConnectException || ex instanceof SocketTimeoutException){

            message = "Geen internet verbinding";
        }else{

            message = "Er is iets fout gegaan";
        }
        return message;
    }


    public void exceptionThrower(Exception ex) throws Exception {
        if (ex != null) {
            if (ex instanceof ArrayIndexOutOfBoundsException) {
                throw (ArrayIndexOutOfBoundsException) ex;
            } else if (ex instanceof ClassNotFoundException) {
                throw (ClassNotFoundException) ex;
            } else if (ex instanceof IndexOutOfBoundsException) {
                throw (IndexOutOfBoundsException) ex;
            } else if (ex instanceof NullPointerException) {
                throw (NullPointerException) ex;
            } else if (ex instanceof NumberFormatException) {
                throw (NumberFormatException) ex;
            } else if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw (Exception) ex;
            }
        }
    }

    public boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}