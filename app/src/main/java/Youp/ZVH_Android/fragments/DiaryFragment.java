package Youp.ZVH_Android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import Youp.ZVH_Android.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {

    private BarChart chart;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_diary, container, false);

        chart = v.findViewById(R.id.chart);

        return v;
    }


    public void initGraph(){

        //list 1
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        //list 2
        List<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(10f, 34f));
        entries2.add(new BarEntry(20f, 30f));
        entries2.add(new BarEntry(5f, 70f));
        entries2.add(new BarEntry(8f, 55f));
        // gap of 2f
        entries2.add(new BarEntry(9f, 74f));
        entries2.add(new BarEntry(8f, 66f));

        BarDataSet set1 = new BarDataSet(entries, "Bovendruk");
        BarDataSet set2 = new BarDataSet(entries2, "Onderdruk");

        BarData data = new BarData(set1,set2);
        data.setBarWidth(0.3f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }
}
