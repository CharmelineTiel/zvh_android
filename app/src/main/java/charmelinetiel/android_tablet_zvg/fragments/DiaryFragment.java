package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import charmelinetiel.android_tablet_zvg.adapters.ListAdapter;
import charmelinetiel.android_tablet_zvg.models.Measurement;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    private BarChart chart;
    private ListView mListView;
    private TextView insertMeasurementText;
    private Button goToMeasurementBtn;
    private View v;
    private ArrayList<Measurement> measurements;


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
        MainActivity mainActivity = (MainActivity) getActivity();

        measurements = new ArrayList<>();
        measurements.addAll(mainActivity.getMeasurements());

        ListAdapter adapter = new ListAdapter(getContext(),this, measurements);

        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (measurements.size() == 0){

            mListView.setVisibility(View.GONE);
            chart.setVisibility(View.GONE);
            insertMeasurementText.setVisibility(View.VISIBLE);
            goToMeasurementBtn.setVisibility(View.VISIBLE);

        }else{

            mListView.setVisibility(View.VISIBLE);
            chart.setVisibility(View.VISIBLE);
            insertMeasurementText.setVisibility(View.GONE);
            goToMeasurementBtn.setVisibility(View.GONE);
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

                Measurement selection = mainActivity.getMeasurements().get(position);

                Bundle bundle=new Bundle();
                bundle.putParcelable("measurement",selection);
                MeasurementDetailFragment fg =new MeasurementDetailFragment();
                fg.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, fg);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });

        goToMeasurementBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

               MeasurementStep1Fragment fg = new MeasurementStep1Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fg)
                        .addToBackStack(String.valueOf(fg.getId()))
                        .commit();
            }
        });



        initGraph();
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
}
