package com.rogeri.schoolknowledge.model;

import android.content.Context;

import com.rogeri.schoolknowledge.data.DAOExercise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rogeri on 08/03/16.
 */
public class Exercise {
    private final String id;
    private final String name;
    private DAOExercise dao;

    public Exercise(int gameID, int id, String name, Context context) {
        this.id = gameID+":"+id;
        this.name = name;
        dao = new DAOExercise(context);
    }

    public String getID() { return id; }

    public String getName() { return name; }
}
