package charmelinetiel.android_tablet_zvg.fragments;


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
    private Button goToMeasurementBtn;
    private View v;
    private MainActivity mainActivity;
    private List<Measurement> measurements;
    private APIService apiService;
    private MeasurementListAdapter adapter;

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

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

        measurements = new ArrayList<>();
        chart.setNoDataText("");

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

                Measurement selection = getMeasurements().get(position);

                Bundle bundle=new Bundle();
                bundle.putParcelable("measurement",selection);
                MeasurementDetailFragment fg = new MeasurementDetailFragment();
                fg.setArguments(bundle);
                mainActivity.openFragment(fg);
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

        if (ExceptionHandler.isConnectedToInternet(getContext())) {

                loadMeasurements(this);
        }else{

            mainActivity.makeSnackBar(getString(R.string.noInternetConnection), mainActivity);
        }

        return v;
    }

    public void initGraph(){

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
        data.setBarWidth(0.3f); // set custom bar width
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

                        ExceptionHandler.exceptionThrower(new Exception());
                        measurements = response.body();
                        mainActivity.runOnUiThread(new Runnable() {
                            public void run() {

                                initGraph();
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
