package charmelinetiel.zorg_voor_het_hart.fragments.Service;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.fragments.Message.ContactHostFragment;
import charmelinetiel.zorg_voor_het_hart.models.Faq;
import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment implements View.OnClickListener {


    private View v;
    private Button contactButton;
    private MainActivity mainActivity;
    ExpandableLayout sectionLinearLayout;

    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_faq, container, false);
        mainActivity = (MainActivity) getActivity();

        initViews();

        return v;

        }

    private void initFAQ() {

        List<Faq> faqList = Faq.getFaqList();
        for(int i = 0; i < faqList.size(); i++){
            sectionLinearLayout.addSection(getSection(faqList.get(i).getQuestion(),
                    faqList.get(i).getAnswer()));
        }
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

    private void initViews(){


        sectionLinearLayout = v.findViewById(R.id.el);
        contactButton = v.findViewById(R.id.contactBtn);
        contactButton.setOnClickListener(this);

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

        initFAQ();

        sectionLinearLayout.setExpandListener((ExpandCollapseListener.ExpandListener<String>) (parentIndex, parent, view) -> {

        });

        sectionLinearLayout.setCollapseListener((ExpandCollapseListener.CollapseListener<String>) (parentIndex, parent, view) -> {

        });
    }

}
