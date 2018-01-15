package charmelinetiel.zorg_voor_het_hart.fragments.Register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.models.Consultant;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
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
    private Button btn1, btn2;
    private Spinner consultantsView;
    private List<Consultant> allConsultants;
    private ArrayAdapter<String> adapter;
    private Consultant consultant;
    private RegisterActivity registerActivity;
    private RelativeLayout progressBar;
    private TextView consultantText, consultantTitle;

    public RegisterStep3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.setTitle("Registreren stap 3 van 3");
        v = inflater.inflate(R.layout.fragment_register_step3, container, false);

        consultantText = v.findViewById(R.id.consultant_text);
        consultantTitle = v.findViewById(R.id.consultant_title);
        progressBar = v.findViewById(R.id.progressBar);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        v.findViewById(R.id.registerBtn).setOnClickListener(this);
        v.findViewById(R.id.backBtn).setOnClickListener(this);

        consultantsView =  v.findViewById(R.id.consultants);

        if (ExceptionHandler.isConnectedToInternet(getContext())) {
            ConsultantsDropdown();
        }else{
            registerActivity.makeSnackBar(String.valueOf(R.string.noInternetConnection), registerActivity);
        }

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerBtn:

                showProgressBar();

                if (ExceptionHandler.isConnectedToInternet(getContext())) {

                    consultant = (Consultant) consultantsView.getSelectedItem();
                    User.getInstance().setConsultantId(consultant.getConsultantId());
                    apiService.register(User.getInstance()).enqueue(this);

                }else{

                    registerActivity.makeSnackBar(getString(R.string.noInternetConnection), registerActivity);

                }
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
            bundle.putString("email", User.getInstance().getEmailAddress());
            fg.setArguments(bundle);
            registerActivity.openFragment(fg);

        }else{
            try {
                hideProgressBar();
                ExceptionHandler.exceptionThrower(new Exception());
            } catch (Exception e) {

                registerActivity.makeSnackBar(ExceptionHandler.getMessage(e), registerActivity);
            }
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        hideProgressBar();
        try {
            ExceptionHandler.exceptionThrower(new Exception());
        } catch (Exception e) {

            registerActivity.makeSnackBar(ExceptionHandler.getMessage(e), registerActivity);
        }
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

                try {
                    ExceptionHandler.exceptionThrower(new Exception());
                } catch (Exception e) {

                    registerActivity.makeSnackBar(ExceptionHandler.getMessage(e), registerActivity);
                }

            }
        });
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        consultantsView.setVisibility(View.GONE);
        consultantTitle.setVisibility(View.GONE);
        consultantText.setVisibility(View.GONE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        consultantsView.setVisibility(View.VISIBLE);
        consultantTitle.setVisibility(View.VISIBLE);
        consultantText.setVisibility(View.VISIBLE);
    }
}
