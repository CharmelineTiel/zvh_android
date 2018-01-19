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
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Faq;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment implements View.OnClickListener, Callback<List<Faq>> {


    private View v;
    private Button contactBtn;
    private MainActivity mainActivity;
    ExpandableLayout sectionLinearLayout;
    private APIService apiService;

    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_faq, container, false);

        mainActivity = (MainActivity) getActivity();
        sectionLinearLayout = v.findViewById(R.id.el);
        contactBtn = v.findViewById(R.id.contactBtn);
        contactBtn.setOnClickListener(this);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);

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
        return v;

        }

    //Initialize the faq questions, try to retrieve them again if they arent available yet
    private void initFAQ() {

        if(Faq.getFaqList().size() != 0) {
            List<Faq> faqList = Faq.getFaqList();
            for (int i = 0; i < faqList.size(); i++) {
                sectionLinearLayout.addSection(getSection(faqList.get(i).getQuestion(),
                        faqList.get(i).getAnswer()));
            }
        }else{
            apiService.getFaq().enqueue(this);
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

    @Override
    public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
        if(response.isSuccessful() && response.body() != null){
            Faq.setFaqList(response.body());
            initFAQ();
        }else {
            mainActivity.makeSnackBar("Er is iets fout gegaan, probeer het opnieuw", mainActivity);
        }
    }

    @Override
    public void onFailure(Call<List<Faq>> call, Throwable t) {
        try {
            ExceptionHandler.exceptionThrower(new Exception());
        } catch (Exception e) {

            mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
        }
    }
}
