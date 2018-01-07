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
    private int amountOfBoxesChecked;

    public CheckboxAdapter(@NonNull Context context, int resource, List<HealthIssue> healthIssues, List<String> selectedIssues) {
        super(context, resource, healthIssues);

        amountOfBoxesChecked = 0;

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
        View view;
        TextView warningMessage;

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.checkbox_listview_item, null);
            view = vi.inflate(R.layout.fragment_measurement_step2, parent,false);
            warningMessage = view.findViewById(R.id.warningMessage);

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

                    countCheck(isChecked);

                    if (isChecked){

                        selectedIssues.add(v.getTag().toString());
                        countCheck(isChecked);

                        if (amountOfBoxesChecked >= 2) {

                            warningMessage.setVisibility(View.VISIBLE);
                            warningMessage.setText("Mochten deze klachten aanhouden, dan is het verstandig om contact op te nemen met uw consulent");

                        }

                    }else
                    {
                        selectedIssues.remove(v.getTag().toString());

                    }
                }
            });

        }

        return convertView;

    }

    private void countCheck(boolean isChecked) {

        amountOfBoxesChecked += isChecked ? 1 : -1 ;
    }
    public List<String> getSelectedIssues(){

        return selectedIssues;
    }


}
