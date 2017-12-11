package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener, Callback<User> {


    private View v;
    private APIService apiService;
    private User user;
    private Button btn1, btn2;
    private EditText email, password;

    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        v =inflater.inflate(R.layout.fragment_register_step2, container, false);

        btn1 = v.findViewById(R.id.registerBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        Fragment fg;
        switch (v.getId()) {

            case R.id.registerBtn:

                processRegistration();

                break;

            case R.id.backBtn:

                getFragmentManager().popBackStack();

                break;
        }
    }


    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

    private void processRegistration()
    {
        //create user based on form inputs
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.pass1);

        user = new User();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user.setConsultantId(bundle.getString("consultantId"));
            user.setFirstname(bundle.getString("firstName"));
            user.setLastname(bundle.getString("lastName"));
            user.setDateOfBirth(bundle.getString("dateOfBirth"));
            user.setGender(bundle.getInt("gender"));
        }
        user.setEmailAddress(email.getText().toString());
        user.setPassword(password.getText().toString());

        apiService.register(user).enqueue(this);
    }
    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if (response.isSuccessful()){
            Fragment fg = new RegisterCompletedFragment();
            Bundle bundle = new Bundle();
            bundle.putString("email", email.getText().toString());
            fg.setArguments(bundle);
            setFragment(fg);

        }else{

            Toast.makeText(getActivity(), "Er is iets fout gegaan, controleer alle velden",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        Toast.makeText(getActivity(), "server error.. probeer het opnieuw",
                Toast.LENGTH_LONG).show();
    }
}
