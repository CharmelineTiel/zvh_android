package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
        try {
            this.healthIssues.addAll(healthIssues);
        }catch (Exception e){

        }
    }

    private class ViewHolder {
        CheckBox box;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view;
        TextView otherNamely;
        EditText otherNamelyInput;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.checkbox_listview_item, null);
            view = vi.inflate(R.layout.fragment_measurement_step2, parent,false);
            otherNamely = view.findViewById(R.id.otherNamely);
            otherNamelyInput = view.findViewById(R.id.otherNamelyInput);
            holder = new ViewHolder();
            holder.box = convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);


            selectedIssues = new ArrayList<>();
            HealthIssue issue = healthIssues.get(position);
            holder.box.setText(issue.getName());
            holder.box.setTag(issue.getHealthIssueId());

            holder.box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final boolean isChecked = holder.box.isChecked();

                    if (isChecked){

                        selectedIssues.add(v.getTag().toString());

                        if (issue.getHealthIssueId() == v.getTag()){

                            otherNamely.setVisibility(View.VISIBLE);
                            otherNamelyInput.setVisibility(View.VISIBLE);
                        }
                    }else
                    {
                        selectedIssues.remove(v.getTag().toString());

                        if (issue.getHealthIssueId() == v.getTag()){
                        otherNamely.setVisibility(View.GONE);
                        otherNamelyInput.setVisibility(View.GONE);
                        }
                    }
                }
            });

        }

        return convertView;

    }

    public List<String> getSelectedIssues(){
        return selectedIssues;
    }

}
