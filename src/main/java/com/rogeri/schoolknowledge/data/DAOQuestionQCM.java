package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rogeri on 18/03/16.
 */
public class DAOQuestionQCM extends BaseDAO {
    public static final String TABLE_NAME = "QuestionQCM";

    private static final String COL_GAME_ID = "gameID";
    private static final String COL_LEVEL_ID = "levelID";
    private static final String COL_ID = "id";
    private static final String COL_QUESTION = "question";
    private static final String COL_REPONSES = "reponses";
    private static final String COL_ANSWERS = "answers";
    private static final String COL_SHOW_COUNT = "showCount";
    private static final String COL_SCORE = "score";

    public static final String CREATE_TABLE = "CREATE_TABLE "+TABLE_NAME+" ("+
            COL_GAME_ID+" INTEGER PRIMARY KEY,"+
            COL_LEVEL_ID+" INTEGER PRIMARY KEY,"+
            COL_ID+" INTEGER PRIMARY KEY,"+
            COL_QUESTION+" VARCHAR(255),"+
            COL_REPONSES+" VARCHAR(511),"+
            COL_ANSWERS+" VARCHAR(255),"+
            COL_SHOW_COUNT+" INTEGER,"+
            COL_SCORE+" INTEGER,"+
            "FOREIGN KEY("+COL_GAME_ID+") REFERENCES "+DAOGame.TABLE_NAME+"("+DAOGame.COL_ID+"),"+
            "FOREIGN KEY("+COL_LEVEL_ID+") REFERENCES "+DAOExercise.TABLE_NAME+"("+DAOExercise.COL_ID+"),"+
            "FOREIGN KEY("+COL_ID+") REFERENCES "+DAOQuestion.TABLE_NAME+"("+DAOQuestion.COL_ID+")"+
        ");";

    public static final String DROP_TABLE = "DROP_TABLE "+TABLE_NAME+" IF EXISTS;";

    public DAOQuestionQCM(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  IDs  Score  Question   Reponses   Answers   ShowCount
                "0, 0, 0, 10, 'Question 1', '\"Rep A\", \"Rep B\", \"Rep C\"', '\"false\", \"true\", \"false\"', 0",
                "0, 0, 1, 15, 'Question 2', '\"Yay\", \"Meh\", \"Poy\"', '\"true\", \"false\", \"true\"', 0",
                "0, 0, 2, 12, 'Question 3', '\"4\", \"8\", \"15\"', '\"false\", \"false\", \"true\"', 0"
        };
        //
        String[] liste = new String[DATA.length];
        int i = 0;
        for (String d : DATA) {
            // Instruction SQL INSERT
            liste[i] = insertSQL + "(" + d + ")";
            i++;
        }

        return liste;
    }
    public long insert(QuestionQCM question) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = question.getID().split(":");
        values.put(COL_GAME_ID, ids[0]);
        values.put(COL_LEVEL_ID, ids[1]);
        values.put(COL_ID, ids[2]);
        values.put(COL_QUESTION, question.getQuestion());
        values.put(COL_REPONSES, question.getReponsesAsString());
        values.put(COL_ANSWERS, question.getAnswersAsString());
        values.put(COL_SCORE, question.getScore());
        values.put(COL_SHOW_COUNT, (question.isShowCount()?1:0));

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int update(QuestionQCM question) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(COL_QUESTION, question.getQuestion());
        values.put(COL_REPONSES, question.getReponsesAsString());
        values.put(COL_ANSWERS, question.getAnswersAsString());
        values.put(COL_SCORE, question.getScore());
        values.put(COL_SHOW_COUNT, (question.isShowCount()?1:0));

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        String[] ids = question.getID().split(":");
        String where = COL_GAME_ID + " = " + ids[0] + " AND " + COL_LEVEL_ID + " = " + ids[1] + " AND " + COL_ID + " = " + ids[2];
        return getDB().update(TABLE_NAME, values, where, null);
    }

    public int removeByID(String id) {
        //Suppression d'une question de la BD à partir de l'ID
        return getDB().delete(TABLE_NAME, COL_ID + " = " + id, null);
    }

    public int remove(QuestionQCM question) {
        return removeByID(question.getID());
    }

    public List<QuestionQCM> selectAll() {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursorToListQuestion(cursor);
    }

    public QuestionQCM retrieveByID(String id) {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  {id};
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=?", p);

        return cursorToFirstQuestion(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<QuestionQCM> cursorToListQuestion(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<QuestionQCM> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToQuestion(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private QuestionQCM cursorToQuestion(Cursor cursor) {
        // Récupére l'index des champs
        int indexGameId = cursor.getColumnIndex(COL_GAME_ID);
        int indexLevelId = cursor.getColumnIndex(COL_LEVEL_ID);
        int indexId = cursor.getColumnIndex(COL_ID);
        int indexQuestion = cursor.getColumnIndex(COL_QUESTION);
        int indexReponse = cursor.getColumnIndex(COL_REPONSES);
        int indexAnswers = cursor.getColumnIndex(COL_ANSWERS);
        int indexScore = cursor.getColumnIndex(COL_SCORE);
        int indexShowCount = cursor.getColumnIndex(COL_SHOW_COUNT);

        String id = cursor.getInt(indexGameId)+":"+cursor.getInt(indexLevelId)+":"+cursor.getInt(indexId);
        int score = cursor.getInt(indexScore);
        String quest = cursor.getString(indexQuestion);
        String rep = cursor.getString(indexReponse);
        String ans = cursor.getString(indexAnswers);
        boolean showCount = cursor.getInt(indexShowCount)==1;
        return new QuestionQCM(id,score,quest,rep,ans,showCount);
    }

    //Cette méthode permet de convertir un cursor en une question
    private QuestionQCM cursorToFirstQuestion(Cursor cursor) {
        // Declaration d'une question
        QuestionQCM question = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            question = cursorToQuestion(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return question;
    }
}
