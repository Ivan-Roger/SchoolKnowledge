package com.rogeri.schoolknowledge.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rogeri on 11/03/16.
 */
public class UserViewAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> objects;
    private HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public UserViewAdapter(Context context, int textViewResourceId,List<User> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i).getName(), i);
        }
        this.context = context;
        this.objects = objects;
    }

    @Override
    public long getItemId(int position) {
        User item = getItem(position);
        return mIdMap.get(item.getName());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View userView = inflater.inflate(R.layout.layout_user_template, parent, false);
        User user = objects.get(position);

        TextView userName = (TextView) userView.findViewById(R.id.user_template_name);
        userName.setText(user.getName());

        ImageView userPic = (ImageView) userView.findViewById(R.id.user_template_pic);
        userPic.setImageResource(user.getPic());

        TextView userScore = (TextView) userView.findViewById(R.id.user_template_score);
        userScore.setText("Score: "+user.getTotalScore());

        return userView;
    }
}
