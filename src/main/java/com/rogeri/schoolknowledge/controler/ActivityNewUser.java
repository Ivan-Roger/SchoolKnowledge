package com.rogeri.schoolknowledge.controler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rogeri.schoolknowledge.R;

public class ActivityNewUser extends AppCompatActivity {
    public static int[] USER_PICTURES = {
            R.mipmap.user_icon_1,
            R.mipmap.user_icon_2,
            R.mipmap.user_icon_3,
            R.mipmap.user_icon_4,
            R.mipmap.user_icon_5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        fillImagePicker();
    }

    private void fillImagePicker() {
        LinearLayout table = (LinearLayout) findViewById(R.id.new_user_pic_table);

        for(int rID: USER_PICTURES) {
            ImageView view = new ImageView(this);
            view.setImageResource(rID);
            table.addView(view);
        }
    }
}
