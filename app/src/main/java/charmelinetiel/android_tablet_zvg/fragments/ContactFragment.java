package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.Message;
import charmelinetiel.android_tablet_zvg.models.authToken;
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
    private EditText message,subject;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact, container, false);

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        sendBtn = view.findViewById(R.id.sendMessageBtn);
        sendBtn.setOnClickListener(this);

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        message = view.findViewById(R.id.message);
        subject = view.findViewById(R.id.subject);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendMessageBtn:

                if(subject.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Onderwerp mag niet leeg zijn",
                            Toast.LENGTH_LONG).show();

                }else if (message.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Bericht mag niet leeg zijn",
                            Toast.LENGTH_LONG).show();
                }else {

                    messageObj = new Message();
                    messageObj.setSubject(subject.getText().toString());
                    messageObj.setMessage(message.getText().toString());
                    apiService.postMessage(messageObj, authToken.getInstance().getAuthToken()).enqueue(this);

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

           Fragment fg= new MessageSentFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fg)
                    .addToBackStack(fg.toString())
                    .commit();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

        Toast.makeText(getActivity(), "server error.. probeer het opnieuw",
                Toast.LENGTH_LONG).show();
    }
}
