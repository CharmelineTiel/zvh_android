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

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.Measurement;

/**
 * Created by Tiel on 27-11-2017.
 */

public class ListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Fragment fg;
    private Context context;
    private ArrayList<Measurement> data;

    private static class ViewHolder {
        TextView title;
        TextView bloodPressure;
        TextView date;
        TextView feedbackMessage;
        RelativeLayout layout;
    }

    public ListAdapter(Context context, Fragment fg, ArrayList<Measurement> data) {
        this.fg = fg;
        this.data = data;
        this.inflater = fg.getLayoutInflater();
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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

        viewHolder.title.setText(m.getMeasurementDateTime());
        viewHolder.bloodPressure.setText("Bovendruk:" + m.getBloodPressureUpper().toString() + "," + " " +
                "Onderdruk:" +
                m.getBloodPressureLower().toString());
        //viewHolder.date.setText(m.getMeasurementDateTime());

        if (m.getBloodPressureLower() <= 100 && m.getBloodPressureUpper()
                <= 130) {

            viewHolder.feedbackMessage.setTextColor(ContextCompat.getColor(context, R.color.positiveFeedbackTxt));
            viewHolder.feedbackMessage.setText("Uw bloeddruk was prima");
            viewHolder.layout.setBackgroundResource(R.color.positiveFeedback);
        }else{

            viewHolder.feedbackMessage.setTextColor(ContextCompat.getColor(context, R.color.negativeFeedbackTxt));
            viewHolder.feedbackMessage.setText("Houd uw bloeddruk goed in de gaten");
            viewHolder.layout.setBackgroundResource(R.color.negativeFeedback);
        }
            return convertView;


    }
}