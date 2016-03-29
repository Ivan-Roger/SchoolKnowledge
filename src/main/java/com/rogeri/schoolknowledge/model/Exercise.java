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
    private DAOExercise dao;

    public Exercise(int gameID, int id, Context context) {
        this.id = gameID+":"+id;
        dao = new DAOExercise(context);
    }

    public String getID() { return id; }
}
