package charmelinetiel.zorg_voor_het_hart.fragments.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Message;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMessageFragment extends Fragment implements View.OnClickListener,
        Callback<ResponseBody> {

    private View view;
    private APIService apiService;
    private Message messageObj;
    private EditText message,subject;
    private TextView consultantEmail, consultantName;
    private MainActivity mainActivity;
    private ProgressBar progressBar;
    private ScrollView contactPage;

    public NewMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_message, container, false);
        mainActivity = (MainActivity)getActivity();

        initViews();

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);


        return view;
    }

    private void initViews() {

        message = view.findViewById(R.id.message);
        subject = view.findViewById(R.id.subject);
        consultantEmail = view.findViewById(R.id.consultantEmail);
        consultantName = view.findViewById(R.id.consultantName);
        progressBar = view.findViewById(R.id.progressBar);
        contactPage = view.findViewById(R.id.contactPage);

        view.findViewById(R.id.sendMessageBtn).setOnClickListener(this);
        view.findViewById(R.id.returnButton).setOnClickListener(this);
        consultantEmail.setText(User.getInstance().getConsultant().getEmailAddress());
        consultantName.setText(User.getInstance().getConsultant().getFirstname()
                + " " + User.getInstance().getConsultant().getLastname());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendMessageBtn:

                if(validInput()) {

                    if (ExceptionHandler.getInstance().isConnectedToInternet(getContext())) {

                        showProgressBar();
                        messageObj = new Message();
                        messageObj.setSubject(subject.getText().toString());
                        messageObj.setMessage(message.getText().toString());
                        apiService.postMessage(messageObj, User.getInstance().getAuthToken()).enqueue(this);
                    }else{

                        mainActivity.makeSnackBar(String.valueOf(R.string.noInternetConnection), mainActivity);
                    }

                }
                break;

            case R.id.returnButton:

                mainActivity.onBackPressed();

                break;
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if(response.isSuccessful()){

            mainActivity.openFragment(new MessageSentFragment());
        }else{

            try {

            } catch (Exception e) {

                mainActivity.makeSnackBar(ExceptionHandler.getInstance().getMessage(e), mainActivity);
            }

            hideProgressBar();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

        try {
            ExceptionHandler.getInstance().exceptionThrower(new Exception());
        } catch (Exception e) {

            mainActivity.makeSnackBar(ExceptionHandler.getInstance().getMessage(e), mainActivity);
        }

        hideProgressBar();
    }

    public boolean validInput()
    {
        if(!mainActivity.formErrorHandler.inputValidString(subject)){

            subject.setError("Onderwerp mag niet leeg zijn");
            return false;

        }else if (!mainActivity.formErrorHandler.inputValidString(message)) {

            message.setError("Bericht mag niet leeg zijn");
            return false;
        }
        return true;
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        contactPage.setVisibility(View.GONE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        contactPage.setVisibility(View.VISIBLE);
    }
}
