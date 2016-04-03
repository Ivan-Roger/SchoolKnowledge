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
public class ExerciseViewAdapter extends ArrayAdapter<ContentValues> {

    public static final String COL_TITLE="level";
    public static final String COL_INFO="info";

    private Context context;
    private List<ContentValues> objects;
    private int viewId;

    public ExerciseViewAdapter(Context context, int textViewResourceId,List<ContentValues> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        this.viewId = textViewResourceId;
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

        TextView title = (TextView) view.findViewById(R.id.template_exercise_name);
        title.setText(item.getAsString(COL_TITLE));

        TextView info = (TextView) view.findViewById(R.id.template_exercise_info);
        info.setText(item.getAsString(COL_INFO));

        return view;
    }
}
