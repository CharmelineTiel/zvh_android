package charmelinetiel.android_tablet_zvg.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.adapters.ListAdapter;
import charmelinetiel.android_tablet_zvg.models.Measurement;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    private BarChart chart;
    private ListView mListView;
    private ListAdapter adapter;
    private View v;
    private ArrayList<Measurement> listItems;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_diary, container, false);
        mListView = v.findViewById(R.id.measurement_list_view);
        dummyData();


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

                Measurement selection = listItems.get(position);

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

        chart = v.findViewById(R.id.chart);
        initGraph();
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public void dummyData()
    {
        listItems = new ArrayList<>();

        Measurement m1 = new Measurement();
        m1.setBloodPressureUpper(120);
        m1.setBloodPressureLower(80);
        m1.setMeasurementDateTime("di, 28 nov 2017");
        listItems.add(m1);

        Measurement m2 = new Measurement();
        m2.setBloodPressureUpper(150);
        m2.setBloodPressureLower(80);
        m2.setMeasurementDateTime("ma, 27 nov 2017");
        listItems.add(m2);

        Measurement m3 = new Measurement();
        m3.setBloodPressureUpper(160);
        m3.setBloodPressureLower(60);
        m3.setMeasurementDateTime("zo, 26 nov 2017");
        listItems.add(m3);

        Measurement m4 = new Measurement();
        m4.setBloodPressureUpper(100);
        m4.setBloodPressureLower(80);
        m4.setMeasurementDateTime("za, 25 nov 2017");
        listItems.add(m4);


        adapter = new ListAdapter(getActivity(),this, listItems);

        mListView.setAdapter(adapter);

    }
    public void initGraph(){

        //bovendruk
        List<BarEntry> bloodPressureUpper = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++)
        {
            bloodPressureUpper.add(new BarEntry(i,listItems.get(i).getBloodPressureUpper()));
        }

        //onderdruk
        List<BarEntry> bloodPressureLower = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++)
        {
            bloodPressureLower.add(new BarEntry(i,listItems.get(i).getBloodPressureLower()));
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
