package com.rogeri.schoolknowledge.model;

import com.rogeri.schoolknowledge.controler.ActivityQCM;
import com.rogeri.schoolknowledge.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rogeri on 08/03/16.
 */
public class QuestionQCM extends Question {
    private String question;
    private List<String> reponses;
    private List<Boolean> answers;
    private boolean showCount;

    public static int type() { return Question.TYPE_QCM; }

    public QuestionQCM(String id, int score, String question, List<String> reponses, List<Boolean> answers, boolean showCount) {
        this(id, score);
        this.question = question;
        this.reponses = reponses;
        this.answers = answers;
        this.showCount = showCount;
    }

    public QuestionQCM(String id, int score, String question, String reponses, String answers, boolean showCount) {
        this(id, score, question, Arrays.asList(stringToArray(reponses)), Arrays.asList(stringToBoolArray(answers)), showCount);
    }

    public QuestionQCM(String id, int score) {
        super(id,score);
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    public void setAnswers(List<Boolean> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question+" | "+getID();
    }

    public List<String> getReponses() { return reponses; }

    public String getReponsesAsString() {
        String res="";
        for (String r: reponses) {
            res += "\""+r+"\"";
            if (reponses.indexOf(r)!=reponses.size()-1)
                res+=", ";
        }
        return res;
    }

    public List<Boolean> getAnswers() { return answers; }

    public String getAnswersAsString() {
        String res="";
        for (Boolean r: answers) {
            res += r;
            if (answers.indexOf(r)!=answers.size()-1)
                res+=", ";
        }
        return res;
    }

    public boolean isShowCount() {
        return showCount;
    }

    public boolean checkAnswers(ArrayList<Boolean> ans) {
        if (ans.size()!=reponses.size()) return false;
        return ans.equals(answers);
    }

    private static String[] stringToArray(String s) {
        return s.substring(1,s.length()-2).split("\", \"");
    }
    private static Boolean[] stringToBoolArray(String s) {
        String [] arr = stringToArray(s);
        Boolean[] res = new Boolean[arr.length];
        int i=0;
        for (String str: arr) {
            res[i++] = (str.equals("true"));
        }
        return res;
    }
}
