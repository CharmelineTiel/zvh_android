package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CheckBox box;

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.checkbox_listview_item, null);

            box = convertView.findViewById(R.id.checkBox1);
            convertView.setTag(box);

            HealthIssue issue = healthIssues.get(position);

            if(selectedIssues != null){
                if(selectedIssues.contains(issue.getHealthIssueId())){
                    box.setChecked(true);
                }else{
                    box.setChecked(false);
                }

            }else{
                selectedIssues = new ArrayList<>();
            }


            box.setText(issue.getName());
            box.setTag(issue.getHealthIssueId());

            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final boolean isChecked = box.isChecked();

                    if (isChecked){

                        selectedIssues.add(v.getTag().toString());

                    }else
                    {
                        selectedIssues.remove(v.getTag().toString());

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
