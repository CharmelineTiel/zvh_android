package charmelinetiel.android_tablet_zvg.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.LoginActivity;
import charmelinetiel.android_tablet_zvg.models.Consultant;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegisterStep1Fragment extends Fragment
        implements View.OnClickListener, Callback<List<Consultant>>

{

    EditText firstName;
    EditText lastName;
    EditText dateOfBirth;
    EditText email;
    List<EditText> form;
    String string = "";
    Button btn1;
    Button btn2;
    View v;

    List<Consultant> allConsultants;
    ArrayList<String> consultantsNames = new ArrayList<>();
    private APIService apiService;
    static boolean verified = false;
    ArrayAdapter<String> adapter;

    public RegisterStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Retrofit retrofit = RetrofitClient.getClient("https://zvh-api.herokuapp.com/");

        apiService = retrofit.create(APIService.class);

        v = inflater.inflate(R.layout.fragment_register_step1, container, false);

        dateOfBirth = v.findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(this);

        btn1 = v.findViewById(R.id.firstBtn);
        btn1.setOnClickListener(this);

        btn2 = v.findViewById(R.id.secondBtn);
        btn2.setOnClickListener(this);


        fetchContent();




        return v;

    }
//
//    public void errorHandeling() {
//        if (firstName.getText().toString() == "") {
//
//        } else if (lastName.getText().toString() == "") {
//
//        } else if (dateOfBirth.getText().toString() == "") {
//
//        } else if (email.getText().toString() == "") {
//
//        }
//    }

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
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth);
                    EditText DateOfBirth = view.findViewById(R.id.dateOfBirth);
                    DateOfBirth.setText(date);
                }
            }, yy, mm, dd);
            datePicker.show();
            break;

            case R.id.secondBtn:

                Fragment fg;
                fg = new RegisterStep2Fragment();
                getActivity().setTitle("Registreren stap 2 van 2");
                setFragment(fg);

                break;

            case R.id.firstBtn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


//    private void validate(final EditText edit)
//    {
//        edit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                string = s.toString();
//                if (string == "" || string.isEmpty() || string.length() <= 0){
//
//                    verified = false;
//                }else{
//
//                    verified = true;
//                }
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//
//
//            }
//        });
//    }

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

            for (Consultant c : allConsultants){

                consultantsNames.add(c.getFirstname());
            }

            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, consultantsNames);

            Spinner consultantsView =  v.findViewById(R.id.consultants);



            consultantsView.setSelection(0,true);
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
