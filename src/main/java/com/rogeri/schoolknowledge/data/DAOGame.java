package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.Game;

import java.util.ArrayList;

/**
 * Created by rogeri on 18/03/16.
 */
public class DAOGame {
    private ArrayList<Game> games = new ArrayList<>();

    public DAOGame() {
        /*/DAOExercise exerciseDAO = new DAOExercise();
        Game game = new Game(0,"Maths", 1, exerciseDAO.getExercisesByGame(0), "Multiplication, Addition, et plus ...");
        games.add(game);
        game = new Game(1,"Histoire", 0, exerciseDAO.getExercisesByGame(1), "Histoire de France et du monde.");
        games.add(game);
        game = new Game(2,"Culture générale", 0, exerciseDAO.getExercisesByGame(2), "Questions diverses ?!.");
        games.add(game);*/
    }

    public ArrayList<Game> getGames() { return games; }

    public Game getGame(int id) { return games.get(id); }
    /*
    TODO :
    - addGame(Game g)
    */
}
