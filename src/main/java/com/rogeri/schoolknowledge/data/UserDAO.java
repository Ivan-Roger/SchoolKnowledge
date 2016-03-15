package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.User;

import java.util.ArrayList;

/**
 * Created by Ivan on 15/03/2016.
 */
public class UserDAO {
    private ArrayList<User> users = new ArrayList<>();

    public UserDAO() {
        User user = new User(0,"User 01",0);
        users.add(user);
        user = new User(1,"User 02",1);
        users.add(user);
        user = new User(2,"User 03",2);
        users.add(user);
        user = new User(3,"User 04",3);
        users.add(user);
        user = new User(4,"User 05",4);
        users.add(user);
        user = new User(5,"User 06",5);
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
