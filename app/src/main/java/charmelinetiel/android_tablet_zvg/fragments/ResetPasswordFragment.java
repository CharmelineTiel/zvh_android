package charmelinetiel.android_tablet_zvg.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.ResetPasswordBody;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPasswordFragment extends Fragment {

    private Button cancelButton, resetPasswordButton;
    private EditText password, confirmedPassword;
    private String token;
    private View v;
    private APIService apiService;


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        if (getArguments() != null) {
            token = getArguments().getString("token");
        }

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        password = v.findViewById(R.id.passwordInput);
        confirmedPassword = v.findViewById(R.id.confirmedPasswordInput);


        cancelButton = v.findViewById(R.id.cancel_reset_password_button);
        cancelButton.setOnClickListener(view -> {
            String a = "n";
        });

        resetPasswordButton = v.findViewById(R.id.reset_password_button);
        resetPasswordButton.setOnClickListener(view -> {
            ResetPasswordBody passwordReset = new ResetPasswordBody();
            passwordReset.setPassword(password.getText().toString());
            passwordReset.setConfirmedPassword(confirmedPassword.getText().toString());
            passwordReset.setToken(token);
            apiService.resetPassword(passwordReset).enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Fragment fg = new ResetPasswordCompletedFragment();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentR, fg)
                            .addToBackStack(null)
                            .commit();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Je hebt gefaald",
                            Toast.LENGTH_LONG).show();
                }
            });
        });
        return v;
    }


}