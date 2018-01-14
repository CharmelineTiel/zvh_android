package charmelinetiel.zorg_voor_het_hart.fragments.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.zorg_voor_het_hart.activities.MainActivity;
import charmelinetiel.zorg_voor_het_hart.adapters.MessageListAdapter;
import charmelinetiel.zorg_voor_het_hart.helpers.ExceptionHandler;
import charmelinetiel.zorg_voor_het_hart.models.Message;
import charmelinetiel.zorg_voor_het_hart.models.User;
import charmelinetiel.zorg_voor_het_hart.webservices.APIService;
import charmelinetiel.zorg_voor_het_hart.webservices.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMessagesFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;
    private List<Message> messages;
    private APIService apiService;
    private MessageListAdapter adapter;
    private ListView mListView;
    private TextView noMessagesText;

    public AllMessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_messages, container, false);

        mainActivity = (MainActivity) getActivity();
        mListView = view.findViewById(R.id.message_list_view);
        noMessagesText = view.findViewById(R.id.noMessagesText);
        messages = new ArrayList<>();
        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(APIService.class);


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Message selectedMessage = getMessages().get(position);

                Bundle bundle=new Bundle();
                bundle.putParcelable("message",selectedMessage);
                MessageDetailFragment fg = new MessageDetailFragment();
                fg.setArguments(bundle);
                mainActivity.openFragment(fg);
            }

        });

        if (ExceptionHandler.isConnectedToInternet(getContext())) {

            loadMessages();
        }else{

            mainActivity.makeSnackBar(String.valueOf(R.string.noInternetConnection), mainActivity);
        }

        return view;
    }


    public List<Message> getMessages() {
        return messages;
    }


    public void loadMessages(){

        apiService.getMessage(User.getInstance().getAuthToken()).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.isSuccessful() && response.body() != null){


                    try{
                        messages = response.body();
                        mainActivity.runOnUiThread(new Runnable() {
                            public void run() {

                                adapter = new MessageListAdapter(getContext(), messages);
                                mListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        });

                        // Show/Hide elements in the fragment based on if there are measurements
                        if (messages.size() == 0){

                            noMessagesText.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);


                        }else{

                            noMessagesText.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                        }

                    }catch (Exception e){

                        mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

                try {
                    ExceptionHandler.exceptionThrower(new Exception());
                } catch (Exception e) {

                    mainActivity.makeSnackBar(ExceptionHandler.getMessage(e), mainActivity);
                }

            }
        });
    }

}
