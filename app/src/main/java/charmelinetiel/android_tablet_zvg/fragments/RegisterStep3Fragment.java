package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.RegisterActivity;
import charmelinetiel.android_tablet_zvg.models.Consultant;
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
    private Spinner consultantsView;
    private List<Consultant> allConsultants;
    private ArrayAdapter<String> adapter;
    private Consultant consultant;
    private RegisterActivity registerActivity;

    public RegisterStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.setTitle("Registreren stap 3 van 3");
        v = inflater.inflate(R.layout.fragment_register_step3, container, false);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        btn1 = v.findViewById(R.id.registerBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.backBtn);
        btn2.setOnClickListener(this);

        consultantsView =  v.findViewById(R.id.consultants);
        consultant = (Consultant) ((Spinner) v.findViewById(R.id.consultants) ).getSelectedItem();

        ConsultantsDropdown();

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerBtn:

                //Register user
                user = registerActivity.getUser();
                user.setConsultantId(consultant.getConsultantId());
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
            registerActivity.openFragment(fg);

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

    public void ConsultantsDropdown(){

        apiService.getAllConsultants().enqueue(new Callback<List<Consultant>>() {
            @Override
            public void onResponse(Call<List<Consultant>> call, Response<List<Consultant>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    allConsultants = response.body();


                    adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, allConsultants);


                    consultantsView.setPrompt("Selecteer uw consulent");
                    consultantsView.setSelection(adapter.getCount()-1,true);
                    consultantsView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Consultant>> call, Throwable t) {

            }
        });
    }
}
