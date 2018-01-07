package charmelinetiel.android_tablet_zvg.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.adapters.MeasurementListAdapter;
import charmelinetiel.android_tablet_zvg.models.AuthToken;
import charmelinetiel.android_tablet_zvg.models.ExceptionHandler;
import charmelinetiel.android_tablet_zvg.models.Measurement;
import charmelinetiel.android_tablet_zvg.webservices.APIService;
import charmelinetiel.android_tablet_zvg.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static charmelinetiel.android_tablet_zvg.activity.MainActivity.progressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    private BarChart chart;
    private ListView mListView;
    private TextView insertMeasurementText;
    private Button goToMeasurementBtn, weekButton,monthButton, graphButton;
    private View v;
    private MainActivity mainActivity;
    private List<Measurement> measurements;
    private APIService apiService;
    private MeasurementListAdapter adapter;
    private String screenResolution;
    private boolean weekSelected, monthSelected, graphSelected;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_diary, container, false);
        mListView = v.findViewById(R.id.measurement_list_view);
        chart = v.findViewById(R.id.chart);
        insertMeasurementText = v.findViewById(R.id.insertMeasurementText);
        goToMeasurementBtn = v.findViewById(R.id.goToMeasurement);
        mainActivity = (MainActivity) getActivity();
        progressBar = v.findViewById(R.id.progressBar_cyclic);
        graphButton = v.findViewById(R.id.graphOverview);
        weekButton = v.findViewById(R.id.weekOverview);
        monthButton = v.findViewById(R.id.monthOverview);
        screenResolution = getString(R.string.screen_type);

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        measurements = new ArrayList<>();
        chart.setNoDataText("");



//        weekButton.setTextColor(getResources().getColor(R.color.ms_black));
//        monthButton.setTextColor(getResources().getColor(R.color.lightGrey));
//        graphButton.setTextColor(getResources().getColor(R.color.lightGrey));


        //check if mobile or not
        if(screenResolution.equals("mobile")) {

            graphButton.setVisibility(View.GONE);
        }else{

            graphButton.setVisibility(View.VISIBLE);
        }

            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (ExceptionHandler.isConnectedToInternet(mainActivity)){

                    Measurement selection = getMeasurements().get(position);

                    Bundle bundle=new Bundle();
                    bundle.putParcelable("measurement",selection);
                    MeasurementDetailFragment fg = new MeasurementDetailFragment();
                    fg.setArguments(bundle);
                    mainActivity.openFragment(fg);

                }else{

                    mainActivity.makeSnackBar(getString(R.string.noInternetConnection),mainActivity);
                }

            }

        });

        goToMeasurementBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mainActivity.setEditingMeasurement(false);
                mainActivity.setMeasurement(new Measurement());
                mainActivity.openFragment(new MeasurementStep1Fragment());

            }
        });

        weekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (!weekSelected && ExceptionHandler.isConnectedToInternet(mainActivity)) {

                    weekSelected = true;
                    monthSelected = false;
                    graphSelected = false;
                    adapter.setDataWeek(measurements.subList(0, 6));
                    adapter.notifyDataSetChanged();
                    mListView.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.GONE);
                    weekButton.setTextColor(getResources().getColor(R.color.ms_black));
                    monthButton.setTextColor(getResources().getColor(R.color.lightGrey));
                    monthButton.setTextColor(getResources().getColor(R.color.lightGrey));
                    Toast.makeText(getContext(), "weekoverzicht geselecteerd", Toast.LENGTH_SHORT).show();

                }else{

                    mainActivity.makeSnackBar(getString(R.string.noInternetConnection),mainActivity);

                }

            }
        });

        monthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (!monthSelected && ExceptionHandler.isConnectedToInternet(mainActivity)) {

                    monthSelected = true;
                    graphSelected = false;
                    weekSelected = false;
                    adapter.setData(measurements);
                    adapter.notifyDataSetChanged();
                    mListView.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "maandoverzicht geselecteerd", Toast.LENGTH_SHORT).show();

                    monthButton.setTextColor(getResources().getColor(R.color.ms_black));
                    weekButton.setTextColor(getResources().getColor(R.color.lightGrey));
                    graphButton.setTextColor(getResources().getColor(R.color.lightGrey));

                }else{

                    mainActivity.makeSnackBar(getString(R.string.noInternetConnection),mainActivity);

                }
            }
        });
        graphButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (!graphSelected && ExceptionHandler.isConnectedToInternet(mainActivity)) {

                    graphSelected = true;
                    monthSelected = false;
                    weekSelected = false;
                    chart.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    initGraph();
                    mListView.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "grafiek overzicht geselecteerd", Toast.LENGTH_SHORT).show();


                    graphButton.setTextColor(getResources().getColor(android.R.color.black));
                    monthButton.setTextColor(getResources().getColor(R.color.lightGrey));
                    weekButton.setTextColor(getResources().getColor(R.color.lightGrey));


                }else{

                    mainActivity.makeSnackBar(getString(R.string.noInternetConnection),mainActivity);

                }
            }
        });


        if (ExceptionHandler.isConnectedToInternet(getContext())) {

                loadMeasurements(this);
        }else{

            mainActivity.makeSnackBar(getString(R.string.noInternetConnection), mainActivity);
        }

        return v;
    }

    public void initGraph(){

        adapter.notifyDataSetChanged();

        //bovendruk
        List<BarEntry> bloodPressureUpper = new ArrayList<>();
        for (int i = 0; i < measurements.size(); i++)
        {
            bloodPressureUpper.add(new BarEntry(i,measurements.get(i).getBloodPressureUpper()));
        }

        //onderdruk
        List<BarEntry> bloodPressureLower = new ArrayList<>();
        for (int i = 0; i < measurements.size(); i++)
        {
            bloodPressureLower.add(new BarEntry(i,measurements.get(i).getBloodPressureLower()));
        }

        BarDataSet upperBP = new BarDataSet(bloodPressureUpper, "Bovendruk");
        BarDataSet lowerBP = new BarDataSet(bloodPressureLower, "Onderdruk");

        int color1 = getResources().getColor(R.color.chart2);
        int color2 = getResources().getColor(R.color.chart1);

        lowerBP.setColors(color1);
        upperBP.setColors(color2);

        BarData data = new BarData(upperBP,lowerBP);
        data.setBarWidth(0.5f); // set custom bar width
        chart.setDrawGridBackground(false);
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }


    public void loadMeasurements(DiaryFragment diaryFragment)
    {
        apiService.getMeasurements(AuthToken.getInstance().getAuthToken()).enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {


                if(response.isSuccessful() && response.body() != null) {

                    try {
                        measurements = response.body();
                        mainActivity.runOnUiThread(new Runnable() {
                            public void run() {

                                adapter = new MeasurementListAdapter(getContext(), diaryFragment, measurements);
                                mListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        });

                        // Show/Hide elements in the fragment based on if there are measurements
                        if (measurements.size() == 0) {

                            mListView.setVisibility(View.GONE);
                            chart.setVisibility(View.GONE);
                            insertMeasurementText.setVisibility(View.VISIBLE);
                            goToMeasurementBtn.setVisibility(View.VISIBLE);

                        } else {

                            mListView.setVisibility(View.VISIBLE);
                            chart.setVisibility(View.VISIBLE);
                            insertMeasurementText.setVisibility(View.GONE);
                            goToMeasurementBtn.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {

                        mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                try {
                    ExceptionHandler.exceptionThrower(new Exception());
                } catch (Exception e) {

                    mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
                }
            }
        });
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }


}
