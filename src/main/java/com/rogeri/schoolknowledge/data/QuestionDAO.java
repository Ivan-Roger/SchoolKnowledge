package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by rogeri on 18/03/16.
 */
public class QuestionDAO {
    private static final String TABLE_NAME = "Question";

    private static final String COL_ID = "id";
    private static final String COL_TYPE = "type";
    private static final String COL_SCORE = "score";

    public static final String CREATE_TABLE = "CREATE_TABLE "+TABLE_NAME+" ("+
            COL_ID+" VARCHAR(10) PRIMARY KEY,"+
            COL_TYPE+" VARCHAR(15) NOT NULL,"+
            COL_SCORE+" INTEGER"+
        ");";

    public static final String DROP_TABLE = "DROP_TABLE "+TABLE_NAME+" IF EXISTS;";

    public HashMap<String,Question> questions = new HashMap<>();

    public QuestionDAO() {
        ArrayList<String> r = new ArrayList<>();
        ArrayList<Boolean> a = new ArrayList<>();
        r.add("wcgv");
        a.add(true);
        r.add("dddd");
        a.add(false);
        r.add("aaaa");
        a.add(false);

        Question q = new QuestionQCM(0,0,0,10,"wcgv ?",r,a);
        questions.put(q.getID(), q);
    }

    public Collection<Question> getQuestionsByGameExercise(int g, int e) {
        return questions.values();
    }
    /*
    TODO :
    - getQuestion(int game, int exercise, int q) --> Find question by reading id
    - addQuestion(Question q)
    */
}
