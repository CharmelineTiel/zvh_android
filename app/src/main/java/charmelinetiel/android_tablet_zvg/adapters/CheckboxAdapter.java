package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.HealthIssue;

public class CheckboxAdapter extends ArrayAdapter<HealthIssue> {

    private ArrayList<HealthIssue> healthIssues;
    private List<String> selectedIssues;

    public CheckboxAdapter(@NonNull Context context, int resource, List<HealthIssue> healthIssues, List<String> selectedIssues) {
        super(context, resource, healthIssues);

        this.selectedIssues = selectedIssues;
        this.healthIssues = new ArrayList<HealthIssue>();
        this.healthIssues.addAll(healthIssues);
    }

    private class ViewHolder {
        CheckBox box;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.checkbox_listview_item, null);


            holder = new ViewHolder();
            holder.box = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.box.setOnCheckedChangeListener(null);
            if(selectedIssues != null){
                if(selectedIssues.contains(healthIssues.get(position).getHealthIssueId())){
                    holder.box.setChecked(true);
                }else{
                    holder.box.setChecked(false);
                }
            }else{
                selectedIssues = new ArrayList<>();
            }

            holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox cb = (CheckBox) buttonView ;
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
