package com.rogeri.schoolknowledge.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

import com.rogeri.schoolknowledge.model.Score;
import com.rogeri.schoolknowledge.data.DAOScore;

/**
 * Created by rogeri on 08/03/16.
 */
public class User {
    private int id;
    private String name;
    private int pic;
    private Context ctx;
    private static int nextID=0;

    public User(int id, String name, int pic, Context ctx) {
        nextID=Math.max(nextID,id)+1;
        this.id=id;
        this.name = name;
        this.pic = pic;
        this.ctx = ctx;
    }

    public static int getNextID() {
        return nextID;
    }

    public int getID() { return id; }

    public String getName() {
        return this.name;
    }

    public int getPic() { return pic; }

    public int getTotalScore() throws Exception {
      DAOScore dao = new DAOScore(ctx);
      ArrayList<Score> list = new ArrayList<>(dao.selectAllByUserID(id));
      int total=0;
      for (Score s: list) {
        total+=s.getScore();
      }
      return total;
    }
}
