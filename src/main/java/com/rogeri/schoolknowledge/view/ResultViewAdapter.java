package com.rogeri.schoolknowledge.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.util.Pair;
import android.util.Log;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogeri on 11/03/16.
 */
public class ResultViewAdapter extends ArrayAdapter<Pair<String,String>> {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-ResultList";
    private Context context;
    private List<Pair<String,String>> objects;
    private int viewId;
    private HashMap<String, Integer> mIdMap = new HashMap<>();

    public ResultViewAdapter(Context context, int textViewResourceId,List<Pair<String,String>> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i).first, i);
        }
        this.context = context;
        this.objects = objects;
        this.viewId = textViewResourceId;
        Log.d(LOG_TAG,"Initialized ResultListAdapter. (length="+objects.size()+")");
    }

    @Override
    public long getItemId(int position) {
        Pair<String,String> item = getItem(position);
        return mIdMap.get(item.first);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(viewId, parent, false);
        Pair<String,String> item = objects.get(position);

        TextView userName = (TextView) view.findViewById(R.id.template_result_name);
        userName.setText(item.first);

        TextView userScore = (TextView) view.findViewById(R.id.template_result_info);
        userScore.setText(item.second);

        return view;
    }
}
