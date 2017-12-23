package charmelinetiel.android_tablet_zvg.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;
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


public class RegisterStep1Fragment extends Fragment
        implements View.OnClickListener, Callback<List<Consultant>>

{

    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText email;
    private String string = "";
    private Button btn1;
    private Button btn2;
    private RadioGroup gender;
    private Boolean valid = false;
    private Consultant consultant;
    private View v;
    private User user;
    private Spinner consultantsView;
    private List<Consultant> allConsultants;
    private APIService apiService;
    private ArrayAdapter<String> adapter;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        (getActivity()).setTitle("Registreren stap 1 van 3");

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");
        apiService = retrofit.create(APIService.class);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);

        btn1 = v.findViewById(R.id.firstBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.secondBtn);
        btn2.setOnClickListener(this);

        consultantsView =  v.findViewById(R.id.consultants);

        fetchContent();

        return v;

    }

    public boolean showError(EditText edit) {

        String regex = "[a-zA-Z0-9]\\w*";

        if (edit.getTag().equals("firstName") && edit.getText().toString() == "" || !edit.getText().toString().equals((regex))){

            firstName.setError("Vul uw voornaam in");
            return valid = true;
        }
        else if(edit.getText().toString() == "" || !edit.getText().toString().equals((regex))){
            lastName.setError("Vul uw voornaam in");
            return valid = true;
        }
        else if(edit.getText().toString() == "" || !edit.getText().toString().equals((regex))){

            email.setError("Vul uw email in");
            return valid = true;
        }

            return valid = false;

    }

    @Override
    public void onStart(){
        super.onStart();
        // Apply any required UI change now that the Fragment is visible.

    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.dateOfBirth:

                //set date of birth
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (DatePicker view1, int year, int monthOfYear, int dayOfMonth) -> {
                    String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth);
                    dateOfBirth.setText(date);
                }, yy, mm, dd);
                datePicker.show();
                break;

            case R.id.secondBtn:

                //set user
                firstName = v.findViewById(R.id.firstName);
                lastName = v.findViewById(R.id.lastName);
                email = v.findViewById(R.id.email);
                gender = v.findViewById(R.id.radioGender);
                consultant = (Consultant) ((Spinner) v.findViewById(R.id.consultants) ).getSelectedItem();
                int index = gender.indexOfChild(getActivity().findViewById(gender.getCheckedRadioButtonId()));
                int genderId;

                if(index == 0){
                    genderId = 1;
                }else{

                    genderId = 2;
                }

                user = new User();
                user.setFirstname(firstName.getText().toString());
                user.setLastname(lastName.getText().toString());
                user.setDateOfBirth(dateOfBirth.getText().toString());
                user.setGender(genderId);

                RegisterActivity activity = (RegisterActivity) getActivity();
                activity.setUser(user);

                //go to registration step 2
                Fragment fg = new RegisterStep2Fragment();
                setFragment(fg);

                break;

            case R.id.firstBtn:

                //go to previous page
                Fragment fg2 = new LoginOrRegisterFragment();
                setFragment(fg2);
                break;
        }
    }


    private void validate(final EditText edit)
    {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                string = edit.getText().toString();

                showError(edit);

          }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });

    }

    public void setFragment(Fragment fg) {
        FragmentTransaction fgTransition = getActivity().getSupportFragmentManager().beginTransaction();
        fgTransition.replace(R.id.contentR, fg);
        fgTransition.addToBackStack(String.valueOf(fg.getId()));
        fgTransition.commit();
    }

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

    private void fetchContent() {

        apiService.getAllConsultants().enqueue(this);
    }
}
