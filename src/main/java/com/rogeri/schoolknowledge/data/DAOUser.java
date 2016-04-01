package com.rogeri.schoolknowledge.data;

import android.util.Log;

import com.rogeri.schoolknowledge.model.User;

import java.util.ArrayList;

/**
 * Created by Ivan on 15/03/2016.
 */
public class DAOUser {
    private ArrayList<User> users = new ArrayList<>();

    public DAOUser() {
        User user = new User(User.getNextID(),"User 01",0);
        users.add(user);
        user = new User(User.getNextID(),"User 02",1);
        users.add(user);
        user = new User(User.getNextID(),"User 03",2);
        users.add(user);
        user = new User(User.getNextID(),"User 04",3);
        users.add(user);
        user = new User(User.getNextID(),"User 05",4);
        users.add(user);
        user = new User(User.getNextID(),"User 06",5);
        users.add(user);
    }

    public ArrayList<User> getJoueurs() {
        return users;
    }

    public User getJoueur(int id) {
        return users.get(id);
    }

    public void addJoueur(User u) {
        users.add(u);
    }
}
