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
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;
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
public class RegisterStep3Fragment extends Fragment implements View.OnClickListener, Callback<User> {

    private View v;
    private APIService apiService;
    private User user;
    private Button btn1, btn2;
    private EditText length;
    public EditText weight;

    public RegisterStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        (getActivity()).setTitle("Registreren stap 3 van 3");
        v = inflater.inflate(R.layout.fragment_register_step3, container, false);

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        btn1 = v.findViewById(R.id.registerBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);

        length = v.findViewById(R.id.length_input);
        weight = v.findViewById(R.id.weight_input);


        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerBtn:

                //processRegistration();

                //update user information
                RegisterActivity activity = (RegisterActivity) getActivity();
                user = activity.getUser();

                try {
                    user.setLength(Integer.parseInt(length.getText().toString()));
                    user.setWeight(Integer.parseInt(weight.getText().toString()));
                }catch (Exception e){

                }
                //register user
                apiService.register(user).enqueue(this);

                break;

            case R.id.backBtn:

                getFragmentManager().popBackStack();

                break;
        }
    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if (response.isSuccessful()){
            Fragment fg = new RegisterCompletedFragment();
            Bundle bundle = new Bundle();
            bundle.putString("email", user.getEmailAddress());
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

    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

}
