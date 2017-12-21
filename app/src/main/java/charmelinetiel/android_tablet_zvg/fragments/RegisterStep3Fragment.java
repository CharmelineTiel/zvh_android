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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep3Fragment extends Fragment implements View.OnClickListener, Callback<User> {

    private View v;
    private APIService apiService;
    private User user;
    private Button btn1, btn2;
    private EditText length, weight;

    public RegisterStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        (getActivity()).setTitle("Registreren stap 3 van 3");
        v = inflater.inflate(R.layout.fragment_register_step3, container, false);

        btn1 = v.findViewById(R.id.registerBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerBtn:

                processRegistration();

                break;

            case R.id.backBtn:

                getFragmentManager().popBackStack();

                break;
        }
    }

    private void processRegistration()
    {
        //create user based on form inputs
        length = v.findViewById(R.id.length_input);
        weight = v.findViewById(R.id.weight_input);

        user = new User();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("user");
        }

            user.setLength(0);

            user.setWeight(0);


        apiService.register(user).enqueue(this);
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
