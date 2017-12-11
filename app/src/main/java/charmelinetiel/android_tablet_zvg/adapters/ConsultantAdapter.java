package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.models.Consultant;

/**
 * Created by Tiel on 2-12-2017.
 */

public class ConsultantAdapter extends ArrayAdapter<Consultant> {

    private Consultant[] consultants;
    private Context context;

    public ConsultantAdapter(@NonNull Context context, int resource, Consultant[] consultants) {
        super(context, resource);

        this.context = context;
        this.consultants = consultants;
    }

    @Override
    public Consultant getItem(int position){
        return consultants[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getCount(){
        return consultants.length;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TextView label = new TextView(context);

        label.setText(consultants[position].getFirstname());
        label.setTag(consultants[position].getConsultantId());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);

        label.setText(consultants[position].toString());

        return label;
    }
}
