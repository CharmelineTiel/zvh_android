package youp.zvh_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import youp.zvh_android.R;
import youp.zvh_android.models.HealthIssue;


public class CheckboxAdapter extends ArrayAdapter<HealthIssue> {

    private ArrayList<HealthIssue> healthIssues;
    private ArrayList<String> selectedIssues;

    public CheckboxAdapter(@NonNull Context context, int resource, List<HealthIssue> healthIssues) {
        super(context, resource, healthIssues);

        selectedIssues = new ArrayList<>();
        this.healthIssues = new ArrayList<HealthIssue>();
        this.healthIssues.addAll(healthIssues);
    }

    private class ViewHolder {
        CheckBox box;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.checkbox_listview_item, null);

            holder = new ViewHolder();
            holder.box = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.box.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    String issueId = (String) cb.getTag();
                    selectedIssues.add(issueId);
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        HealthIssue issue = healthIssues.get(position);
        holder.box.setText(issue.getName());
        holder.box.setTag(issue.getHealthIssueId());

        return convertView;

    }

    public List<String> getSelectedIssues(){
        return selectedIssues;
    }

}
