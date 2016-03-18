package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;

/**
 * Created by rogeri on 18/03/16.
 */
public class QuestionDAO {
    public ArrayList<ArrayList<ArrayList<Question>>> questions;

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
        ArrayList<ArrayList<Question>> g = new ArrayList<>();
        ArrayList<Question> e = new ArrayList<>();
        e.add(q);
        g.add(e);
        questions.add(g);
    }

    public ArrayList<Question> getQuestionsByGameExercise(int g, int e) {
        return questions.get(g).get(e);
    }
    /*
    TODO :
    - getQuestion(int game, int exercise, int q)
    - addQuestion(Question q)
    */
}
