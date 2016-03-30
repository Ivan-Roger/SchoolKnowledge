package com.rogeri.schoolknowledge.model;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.Exercise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rogeri on 08/03/16.
 */
public class Game {
    private final int id;
    private final String name;
    private final String info;
    private final int picID;

    public static final int[] GAME_PICTURES = {R.mipmap.app_logo, R.mipmap.game_icon_0};

    public Game(int id, String name, String info, int picID) {
        this.id = id;
        this.name = name;
        this.picID = picID;
        this.info = info;
    }

    public int getID() { return id; }

    public String getName() { return name; }

    public String getInfo() { return info; }

    public int getPic() { return picID; }
}
