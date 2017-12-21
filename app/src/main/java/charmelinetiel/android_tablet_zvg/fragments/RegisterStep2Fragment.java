package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.User;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStep2Fragment extends Fragment implements View.OnClickListener {


    private View v;
    private APIService apiService;
    private User user;
    private Button btn1, btn2;
    private EditText email, pass1,repeatPass;

    public RegisterStep2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle("Registreren stap 2 van 3");

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/"); //TODO fix this, DRY CODE FTW
        apiService = retrofit.create(APIService.class);

        v =inflater.inflate(R.layout.fragment_register_step2, container, false);

        btn1 = v.findViewById(R.id.nextScreenBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextScreenBtn:

                user = new User();
                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    user.setConsultantId(bundle.getString("consultantId"));
                    user.setFirstname(bundle.getString("firstName"));
                    user.setLastname(bundle.getString("lastName"));
                    user.setDateOfBirth(bundle.getString("dateOfBirth"));
                    user.setGender(bundle.getInt("gender"));
                }
                email = v.findViewById(R.id.email);
                pass1 = v.findViewById(R.id.pass1);//TODO check if passwords match

                if (email != null) {
                    user.setEmailAddress(email.getText().toString());
                }
                if(pass1 != null) {
                    user.setPassword(pass1.getText().toString());
                }

                Fragment fg = new RegisterStep3Fragment();
                Bundle bundleForRegister = new Bundle();
                bundleForRegister.putParcelable("User", user);
                fg.setArguments(bundle);
                setFragment(fg);

                break;

            case R.id.backBtn:

                getFragmentManager().popBackStack();

                break;
        }
    }

//TODO make setFragment static somehow, this method is being used in pretty much all the fragments.. DRY CODE FTW
    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

}
