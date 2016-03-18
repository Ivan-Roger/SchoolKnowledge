package com.rogeri.schoolknowledge.controler;

import com.rogeri.schoolknowledge.data.ExerciseDAO;
import com.rogeri.schoolknowledge.data.GameDAO;
import com.rogeri.schoolknowledge.data.QuestionDAO;
import com.rogeri.schoolknowledge.data.UserDAO;

/**
 * Created by Ivan on 15/03/2016.
 */
public class Main {
    public static final UserDAO userDAO = new UserDAO();
    public static final GameDAO gameDAO = new GameDAO();
    public static final ExerciseDAO exerciseDAO = new ExerciseDAO();
    public static final QuestionDAO questionDAO = new QuestionDAO();
}
