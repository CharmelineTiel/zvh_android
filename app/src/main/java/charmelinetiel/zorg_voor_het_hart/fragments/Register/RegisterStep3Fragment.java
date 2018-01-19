package charmelinetiel.zorg_voor_het_hart.fragments.Register;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Consultant;
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

    private final String DEFAULT_CONSULTANT_ID = "1";
    private View v;
    private APIService apiService;
    private Button registerButton, backButton;
    private Spinner consultantsView;
    private RelativeLayout spinnerView;
    private List<Consultant> allConsultants;
    private ArrayAdapter<String> adapter;
    private Consultant consultant;
    private RegisterActivity registerActivity;
    private ProgressBar progressBar;
    private TextView consultantText, consultantTitle;
    private ImageView infoToolTip;

    public RegisterStep3Fragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.setTitle("Registreren stap 3 van 3");
        v = inflater.inflate(R.layout.fragment_register_step3, container, false);


        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        initViews();


        setDefaultValueSpinner();
        initConsultantsDropdown();

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.registerButton:
                if (ExceptionHandler.getInstance().isConnectedToInternet(getContext())) {
                    consultant = (Consultant) consultantsView.getSelectedItem();

                    if (!consultant.getConsultantId().equals(DEFAULT_CONSULTANT_ID)){
                        showProgressBar();
                        User.getInstance().setConsultantId(consultant.getConsultantId());
                        apiService.register(User.getInstance()).enqueue(this);
                    }else{
                        registerActivity.makeSnackBar("U heeft nog geen consulent geselecteerd", registerActivity);
                    }
                }else{
                    registerActivity.makeSnackBar(getString(R.string.noInternetConnection), registerActivity);
                }
                break;

            case R.id.returnButton:
                getFragmentManager().popBackStack();

                break;
        }
    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if (response.isSuccessful()){
            Fragment fg = new RegisterCompletedFragment();
            registerActivity.openFragment(fg);

        }else{
            try {
                hideProgressBar();
            } catch (Exception e) {

                registerActivity.makeSnackBar(ExceptionHandler.getInstance().getMessage(e), registerActivity);
            }
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

        hideProgressBar();
        try {

        } catch (Exception e) {

            registerActivity.makeSnackBar(ExceptionHandler.getInstance().getMessage(e), registerActivity);
        }
    }

    private void initConsultantsDropdown(){
        if(Consultant.getConsultants().size() == 0){
            apiService.getAllConsultants().enqueue(new Callback<List<Consultant>>() {
                @Override
                public void onResponse(Call<List<Consultant>> call, Response<List<Consultant>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Consultant.setConsultants(response.body());
                        initConsultantsDropdown();
                    }else{
                        registerActivity.makeSnackBar("Er is iets fout gegaan, probeer het opnieuw", registerActivity);
                    }
                }

                @Override
                public void onFailure(Call<List<Consultant>> call, Throwable t) {
                    try {
                        ExceptionHandler.getInstance().exceptionThrower(new Exception());
                    } catch (Exception e) {

                        registerActivity.makeSnackBar(ExceptionHandler.getInstance().getMessage(e), registerActivity);
                    }
                }
            });
        }
            allConsultants.addAll(Consultant.getConsultants());
            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, allConsultants);
            consultantsView.setSelection(0,true);
            consultantsView.setVerticalScrollBarEnabled(true);
            consultantsView.setPadding(30, 30,30,30);
            consultantsView.setAdapter(adapter);

    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        spinnerView.setVisibility(View.INVISIBLE);
        consultantTitle.setVisibility(View.INVISIBLE);
        consultantText.setVisibility(View.INVISIBLE);
        infoToolTip.setVisibility(View.INVISIBLE);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        spinnerView.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        consultantTitle.setVisibility(View.VISIBLE);
        consultantText.setVisibility(View.VISIBLE);
        infoToolTip.setVisibility(View.GONE);
    }

    private void setDefaultValueSpinner(){
        allConsultants = new ArrayList<>();
        Consultant defaultChoice = new Consultant();
        defaultChoice.setFirstname("Selecteer uw consulent");
        defaultChoice.setConsultantId(DEFAULT_CONSULTANT_ID);
        defaultChoice.setLastname("");
        allConsultants.add(0, defaultChoice);

    }

    private void initViews()
    {
        consultantText = v.findViewById(R.id.consultant_text);
        consultantTitle = v.findViewById(R.id.consultantTitle);
        progressBar = v.findViewById(R.id.progressBar);
        registerButton = v.findViewById(R.id.registerButton);
        spinnerView = v.findViewById(R.id.spinner);
        backButton = v.findViewById(R.id.returnButton);
        consultantsView =  v.findViewById(R.id.consultants);
        infoToolTip = v.findViewById(R.id.toolTipConsultant);

        registerButton.setOnClickListener(this);
        backButton.setOnClickListener(this);


        infoToolTip.setOnClickListener(v -> {

            Tooltip.Builder builder = new Tooltip.Builder(v, R.style.AppTheme)
                    .setCancelable(true)
                    .setDismissOnClick(false)
                    .setCornerRadius(8f)
                    .setPadding(30f)
                    .setMargin(10f)
                    .setTextColor(getResources().getColor(R.color.whiteText))
                    .setBackgroundColor(getResources().getColor(R.color.mediumGrey))
                    .setGravity(Gravity.BOTTOM)
                    .setText("Uw consulent bij het St.Antonius ziekenhuis");
            builder.show();
        });

    }
}
