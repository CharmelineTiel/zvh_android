package charmelinetiel.zorg_voor_het_hart.fragments.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.LoginActivity;
import charmelinetiel.zorg_voor_het_hart.activities.RegisterActivity;
import charmelinetiel.zorg_voor_het_hart.models.ResetPasswordBody;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener, Callback<ResponseBody> {

    private EditText password, confirmedPassword;
    private String token;
    private View v;
    private APIService apiService;
    private RegisterActivity registerActivity;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        registerActivity = (RegisterActivity) getActivity();
        registerActivity.getSupportActionBar().show();

        if (getArguments() != null) {
            token = getArguments().getString("token");
        }

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        initViews();

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cancel_reset_password_button:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

                break;
            case R.id.reset_password_button:
                ResetPasswordBody passwordReset = new ResetPasswordBody();
                passwordReset.setPassword(password.getText().toString());
                passwordReset.setConfirmedPassword(confirmedPassword.getText().toString());
                passwordReset.setToken(token);
                apiService.resetPassword(passwordReset).enqueue(this);
                break;
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        registerActivity.openFragment(new ResetPasswordCompletedFragment());
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(getContext(), "Er is iets fout gegaan",
                Toast.LENGTH_LONG).show();
    }

    private void initViews(){

        password = v.findViewById(R.id.passwordInput);
        confirmedPassword = v.findViewById(R.id.confirmedPasswordInput);

        v.findViewById(R.id.cancel_reset_password_button).setOnClickListener(this);
        v.findViewById(R.id.reset_password_button).setOnClickListener(this);

    }
}
