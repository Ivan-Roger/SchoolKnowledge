package com.rogeri.schoolknowledge.view;

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
import com.rogeri.schoolknowledge.controler.ActivityNewUser;
import com.rogeri.schoolknowledge.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rogeri on 11/03/16.
 */
public class UserViewAdapter extends ArrayAdapter<User> {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-UserList";
    private Context context;
    private List<User> objects;
    private int viewId;
    private HashMap<Integer, Integer> mIdMap = new HashMap<Integer, Integer>();

    public UserViewAdapter(Context context, int textViewResourceId,List<User> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i).getID(), i);
        }
        this.context = context;
        this.objects = objects;
        this.viewId = textViewResourceId;
    }

    @Override
    public long getItemId(int position) {
        User item = getItem(position);
        return mIdMap.get(item.getID());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(viewId, parent, false);
        User user = objects.get(position);

        TextView userName = (TextView) view.findViewById(R.id.user_template_name);
        userName.setText(user.getName());

        ImageView userPic = (ImageView) view.findViewById(R.id.user_template_pic);
        userPic.setImageResource(ActivityNewUser.USER_PICTURES[user.getPic()]);

        TextView userScore = (TextView) view.findViewById(R.id.user_template_info);
        try {
          userScore.setText("Score: "+user.getTotalScore(getContext()));
        } catch(Exception e) {
          Log.e(LOG_TAG,e.getMessage());
          e.printStackTrace();
        }

        return view;
    }
}
