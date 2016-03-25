package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by rogeri on 25/03/16.
 */
public class DAOQuestion {
    private static final String TABLE_NAME = "Questions";

    private static final String COL_ID = "id";
    private static final String COL_TYPE = "type";

    public static final String CREATE_TABLE = "CREATE_TABLE "+TABLE_NAME+" ("+
            COL_ID+" VARCHAR(10) PRIMARY KEY,"+
            COL_TYPE+" INTEGER"+
            ");";

    public static final String DROP_TABLE = "DROP_TABLE "+TABLE_NAME+" IF EXISTS;";

    public DAOQuestion() {}

    public Collection<Question> getQuestionsByGameExercise(int g, int e) {
        return null;
    }

    public Question getQuestionsById(String id) {
        return null;
    }
    /*
    TODO :
    - getQuestion(int game, int exercise, int q) --> Find question by reading id
    - addQuestion(Question q)
    */
}

