package com.rogeri.schoolknowledge.model;

import com.rogeri.schoolknowledge.model.Question;

import java.util.ArrayList;

/**
 * Created by rogeri on 08/03/16.
 */
public class QuestionQCM extends Question {
    private String question;
    private ArrayList<String> reponses;
    private ArrayList<Boolean> answers;
    private boolean showAnswersCount;

    public QuestionQCM(int gameID, int exerciseID, int id, int score, String question, ArrayList<String> reponses, ArrayList<Boolean> answers, boolean showAnswersCount) {
        super(gameID, exerciseID, id,score);
        this.question = question;
        this.reponses = reponses;
        this.answers = answers;
        this.showAnswersCount = showAnswersCount;
    }

    public QuestionQCM(int gameID, int exerciseID, int id, int score, String question, ArrayList<String> reponses, ArrayList<Boolean> answers) {
        this(gameID, exerciseID, id, score, question, reponses, answers, false);
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public boolean isShowAnswersCount() {
        return showAnswersCount;
    }

    public boolean checkAnswers(ArrayList<Boolean> ans) {
        if (ans.size()!=reponses.size()) return false;
        return ans.equals(answers);
    }
}
