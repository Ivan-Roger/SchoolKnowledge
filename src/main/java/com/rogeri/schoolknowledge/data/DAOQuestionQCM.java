package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by rogeri on 18/03/16.
 */
public class DAOQuestionQCM {
    private static final String TABLE_NAME = "QuestionQCM";

    private static final String COL_ID = "id";
    private static final String COL_SCORE = "score";

    public static final String CREATE_TABLE = "CREATE_TABLE "+TABLE_NAME+" ("+
            COL_ID+" VARCHAR(10) PRIMARY KEY,"+
            COL_SCORE+" INTEGER"+
        ");";

    public static final String DROP_TABLE = "DROP_TABLE "+TABLE_NAME+" IF EXISTS;";

    public HashMap<String,QuestionQCM> questions = new HashMap<>();

    public DAOQuestionQCM() {
        ArrayList<String> r = new ArrayList<>();
        ArrayList<Boolean> a = new ArrayList<>();
        r.add("wcgv");
        a.add(true);
        r.add("dddd");
        a.add(false);
        r.add("aaaa");
        a.add(false);

        QuestionQCM q = new QuestionQCM(0,0,0,10,"Question ?",r,a);
        questions.put(q.getID(), q);
    }

    public Collection<QuestionQCM> getQuestionsByGameExercise(int g, int e) {
        return questions.values();
    }

    public QuestionQCM getQuestionsById(String id) {
        return questions.get(id);
    }
    /*
    TODO :
    - getQuestion(int game, int exercise, int q) --> Find question by reading id
    - addQuestion(Question q)
    */
}
