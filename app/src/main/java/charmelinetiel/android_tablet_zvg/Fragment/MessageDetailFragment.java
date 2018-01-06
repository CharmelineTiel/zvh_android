package charmelinetiel.android_tablet_zvg.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.activity.MainActivity;
import charmelinetiel.android_tablet_zvg.models.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageDetailFragment extends Fragment implements View.OnClickListener {

    private View v;
    private Message m;
    private MainActivity mainActivity;

    public MessageDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         m = getArguments().getParcelable("message");

         mainActivity = (MainActivity) getActivity();

        v = inflater.inflate(R.layout.fragment_message_detail, container, false);

        Button backButton = v.findViewById(R.id.cancelButton);
        backButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:

                 mainActivity.openFragment(new ContactHostFragment());

                break;
        }
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView date = v.findViewById(R.id.date);
        TextView subject = v.findViewById(R.id.subject);
        TextView message = v.findViewById(R.id.message);

        date.setText("4-12-2018");
        subject.setText(m.getSubject());
        message.setText(m.getMessage());

    }
}
