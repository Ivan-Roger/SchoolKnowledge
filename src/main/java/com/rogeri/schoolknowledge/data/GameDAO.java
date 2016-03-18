package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.controler.Main;
import com.rogeri.schoolknowledge.model.Game;

import java.util.ArrayList;

/**
 * Created by rogeri on 18/03/16.
 */
public class GameDAO {
    private ArrayList<Game> games;

    public GameDAO() {
        Game game = new Game(0,"Maths", Main.exerciseDAO.getExercisesByGame(0));
        games.add(game);
    }

    public ArrayList<Game> getGames() { return games; }

    public Game getGame(int id) { return games.get(id); }
    /*
    TODO :

    - addGame(Game g)
    */
}
