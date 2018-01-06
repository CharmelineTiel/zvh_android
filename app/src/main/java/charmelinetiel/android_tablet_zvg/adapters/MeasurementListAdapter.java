package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * Created by Tiel on 27-11-2017.
 */

public class MeasurementListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Fragment fg;
    private Context context;
    private List<Measurement> data;

    private static class ViewHolder {
        TextView title;
        TextView bloodPressure;
        TextView date;
        TextView feedbackMessage;
        RelativeLayout layout;
    }

    public MeasurementListAdapter(Context context, Fragment fg, List<Measurement> data) {
        this.fg = fg;
        this.data = data;
        this.inflater = fg.getLayoutInflater();
        this.context = context;
    }

    @Override
    public int getCount() {
        if(data != null){
            return data.size();
        }else{
            return 0;
        }
    }

    public void setData(List<Measurement> data){
        this.data.addAll(data);
    }

    public void setDataWeek(List<Measurement> data){
        this.data = new ArrayList<>();
        this.data.addAll(data);
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Measurement m = data.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.measurement_list, null);

            viewHolder.title = convertView.findViewById(R.id.titleLbl);
            viewHolder.bloodPressure = convertView.findViewById(R.id.bloodPressureLbl);
            viewHolder.date = convertView.findViewById(R.id.dateLbl);
            viewHolder.feedbackMessage = convertView.findViewById(R.id.feedbackLbl);
            viewHolder.layout = convertView.findViewById(R.id.measurement_list_item);

            convertView.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(m.getMeasurementDateFormatted());
        viewHolder.bloodPressure.setText("Bovendruk:" + m.getBloodPressureUpper().toString() + "," + " " +
                "Onderdruk:" +
                m.getBloodPressureLower().toString());
        //viewHolder.date.setText(m.getMeasurementDateTime());

        if (m.getBloodPressureLower() <= 100 && m.getBloodPressureUpper()
                <= 130) {

            viewHolder.feedbackMessage.setTextColor(ContextCompat.getColor(context, R.color.positiveFeedbackTxt));
            viewHolder.feedbackMessage.setText("Bloeddruk feedback");
            viewHolder.layout.setBackgroundResource(R.color.positiveFeedback);
        }else{

            viewHolder.feedbackMessage.setTextColor(ContextCompat.getColor(context, R.color.negativeFeedbackTxt));
            viewHolder.feedbackMessage.setText("Bloeddruk feedback");
            viewHolder.layout.setBackgroundResource(R.color.negativeFeedback);
        }
            return convertView;


    }

}
