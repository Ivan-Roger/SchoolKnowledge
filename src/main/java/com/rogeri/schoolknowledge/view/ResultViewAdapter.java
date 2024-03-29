package com.rogeri.schoolknowledge.view;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;

import java.util.List;

/**
 * Created by rogeri on 11/03/16.
 */
public class ResultViewAdapter extends ArrayAdapter<ContentValues> {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-ResultList";

    public static final String COL_TITLE="question";
    public static final String COL_SCORE="score";
    public static final String COL_POINTS="points";
    public static final String COL_INFO="answer";

    private Context context;
    private List<ContentValues> objects;
    private int viewId;

    public ResultViewAdapter(Context context, int textViewResourceId,List<ContentValues> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        this.viewId = textViewResourceId;
        Log.d(LOG_TAG,"Initialized ResultListAdapter. (length="+objects.size()+")");
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(viewId, parent, false);
        ContentValues item = objects.get(position);

        int pts = item.getAsInteger(COL_SCORE);
        ImageView state = (ImageView) view.findViewById(R.id.template_result_state);
        if (pts>0) {
          state.setImageResource(android.R.drawable.presence_online);
        } else {
          state.setImageResource(android.R.drawable.presence_offline);
        }

        TextView points = (TextView) view.findViewById(R.id.template_result_points);
        points.setText(pts+"/"+item.getAsInteger(COL_POINTS)+" pts");

        TextView title = (TextView) view.findViewById(R.id.template_result_title);
        title.setText(item.getAsString(COL_TITLE));

        TextView info = (TextView) view.findViewById(R.id.template_result_info);
        info.setText(item.getAsString(COL_INFO));

        return view;
    }
}
