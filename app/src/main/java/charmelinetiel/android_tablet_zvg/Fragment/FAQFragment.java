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
import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment implements View.OnClickListener {


    private View v;
    private Button contactBtn;
    private MainActivity mainActivity;

    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_faq, container, false);


        mainActivity = (MainActivity) getActivity();
        ExpandableLayout sectionLinearLayout = v.findViewById(R.id.el);
        contactBtn = v.findViewById(R.id.contactBtn);
        contactBtn.setOnClickListener(this);

        sectionLinearLayout.setRenderer(new ExpandableLayout.Renderer<String, String>() {
            @Override
            public void renderParent(View view, String s, boolean b, int i) {

                ((TextView) view.findViewById(R.id.question)).setText(s);
                view.findViewById(R.id.arrow).setBackgroundResource(b ? R.drawable.ic_chevron_right_black_24dp : R.drawable.ic_expand_more_black_24dp);

            }

            @Override
            public void renderChild(View view, String s, int i, int i1) {
                ((TextView) view.findViewById(R.id.answer)).setText(s);

            }
        });


        sectionLinearLayout.addSection(getSection(getResources().getString(R.string.q1),
                getResources().getString(R.string.a1)));
        sectionLinearLayout.addSection(getSection(getResources().getString(R.string.q2),
                getResources().getString(R.string.a2)));
        sectionLinearLayout.addSection(getSection(getResources().getString(R.string.q3),
                getResources().getString(R.string.a3)));
        sectionLinearLayout.addSection(getSection(getResources().getString(R.string.q4),
                getResources().getString(R.string.a4)));

        sectionLinearLayout.setExpandListener((ExpandCollapseListener.ExpandListener<String>) (parentIndex, parent, view) -> {

        });

        sectionLinearLayout.setCollapseListener((ExpandCollapseListener.CollapseListener<String>) (parentIndex, parent, view) -> {

        });
        return v;

        }


    public Section<String, String> getSection(String question, String answer) {
        Section<String, String> section = new Section<>();
        String q = new String(question);
        String a  = new String(answer);


        section.parent = q;
        section.children.add(a);

        section.expanded = false;
        return section;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Veelgestelde vragen");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.contactBtn:

                mainActivity.openFragment(new ContactHostFragment());

                break;
            }
    }

}