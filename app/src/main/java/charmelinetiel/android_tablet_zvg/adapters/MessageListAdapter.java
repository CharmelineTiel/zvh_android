package charmelinetiel.android_tablet_zvg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import charmelinetiel.android_tablet_zvg.R;
import charmelinetiel.android_tablet_zvg.models.Message;

/**
 * Created by Tiel on 27-11-2017.
 */

public class MessageListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private List<Message> data;

    private static class ViewHolder {
        TextView title;
        TextView excerpt;
        TextView date;
        RelativeLayout layout;
    }

    public MessageListAdapter(Context context, List<Message> data) {

        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(data != null){
            return data.size();
        }else{
            return 0;
        }
    }

    public void setData(List<Message> data){
        this.data.addAll(data);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message m = data.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.message_list, null);

            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.layout = convertView.findViewById(R.id.message_list_item);

            convertView.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(m.getSubject());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        viewHolder.date.setText(m.getDateTime());


            return convertView;


    }
}
