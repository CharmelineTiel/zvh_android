package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.AuthToken;
import charmelinetiel.android_tablet_zvg.models.FormErrorHandling;
import charmelinetiel.android_tablet_zvg.models.Message;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener, Callback<ResponseBody> {

    private View view;
    private APIService apiService;
    private Message messageObj;
    private Button sendBtn, backBtn;
    private EditText message,subject, consultantEmail, consultantName;
    private FormErrorHandling validateForm;
    private MainActivity mainActivity;
    private ProgressBar progressBar;
    private ScrollView contactPage;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact, container, false);


        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        sendBtn = view.findViewById(R.id.sendMessageBtn);
        sendBtn.setOnClickListener(this);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        message = view.findViewById(R.id.message);
        subject = view.findViewById(R.id.subject);
        consultantEmail = view.findViewById(R.id.consultantEmail);
        consultantName = view.findViewById(R.id.consultantName);

        mainActivity = (MainActivity)getActivity();
        consultantEmail.setText(mainActivity.getUser().getConsultant().getEmailAddress());
        consultantName.setText(mainActivity.getUser().getConsultant().getFirstname()
                                + " " + mainActivity.getUser().getConsultant().getLastname());

        progressBar = view.findViewById(R.id.progressBar);
        contactPage = view.findViewById(R.id.contactPage);

        validateForm = new FormErrorHandling();

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendMessageBtn:

                if(validInput()) {
                    showProgressBar();
                    messageObj = new Message();
                    messageObj.setSubject(subject.getText().toString());
                    messageObj.setMessage(message.getText().toString());
                    apiService.postMessage(messageObj, AuthToken.getInstance().getAuthToken()).enqueue(this);
                }
                break;

            case R.id.backBtn:

                getFragmentManager().popBackStack();

                break;
        }

    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if(response.isSuccessful()){

           mainActivity.openFragment(new MessageSentFragment());
        }else{
            Toast.makeText(getActivity(), "Er is iets fout gegaan, controleer alstublieft alle velden",
                    Toast.LENGTH_LONG).show();
            hideProgressBar();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

        hideProgressBar();
        Toast.makeText(getActivity(), "server error.. probeer het opnieuw",
                Toast.LENGTH_LONG).show();
    }


    public boolean validInput()
    {
        if(!validateForm.inputGiven(subject)){

            validateForm.showError("Onderwerp mag niet leeg zijn");
            return false;

        }else if (!validateForm.inputGiven(message)) {

            validateForm.showError("Bericht mag niet leeg zijn");
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
