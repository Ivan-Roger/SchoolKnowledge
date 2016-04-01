package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.Pair;

import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogeri on 30/03/16.
 */
public class DAOQuestion extends BaseDAO {
    public static final String TABLE_NAME = "QuestionList";

    private static final String COL_GAME_ID = "gameID";
    private static final String COL_LEVEL_ID = "levelID";
    public static final String COL_ID = "id"; // Public: it's a new ID
    private static final String COL_TYPE = "type";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_GAME_ID+" INTEGER,"+
            COL_LEVEL_ID+" INTEGER,"+
            COL_ID+" INTEGER,"+
            COL_TYPE+" VARCHAR(50),"+
            "PRIMARY KEY("+COL_GAME_ID+", "+COL_LEVEL_ID+", "+COL_ID+"),"+
            "FOREIGN KEY("+COL_GAME_ID+") REFERENCES "+DAOGame.TABLE_NAME+"("+DAOGame.COL_ID+"),"+
            "FOREIGN KEY("+COL_LEVEL_ID+") REFERENCES "+DAOExercise.TABLE_NAME+"("+DAOExercise.COL_ID+")"+
            ");";

    public static final String DROP_TABLE = "DROP TABLE "+TABLE_NAME+" IF EXISTS;";

    private static final String QUESTION_TYPE_QCM = "QuestionQCM";

    public DAOQuestion(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  ID   Name  Info
                "0, 0, 0, '"+QUESTION_TYPE_QCM+"'",
                "0, 0, 1, '"+QUESTION_TYPE_QCM+"'",
                "0, 0, 2, '"+QUESTION_TYPE_QCM+"'"
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

    public long insert(Question question) throws Exception {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = question.getID().split(":");
        values.put(COL_GAME_ID, ids[0]);
        values.put(COL_LEVEL_ID, ids[1]);
        values.put(COL_ID, ids[2]);

        String type;
        if (question instanceof QuestionQCM) {
            type = QUESTION_TYPE_QCM;
            DAOQuestionQCM dao = new DAOQuestionQCM(this.context);
            dao.insert((QuestionQCM)question);
        } else
            throw new Exception("DAO-"+TABLE_NAME+": Unknow class");


        values.put(COL_TYPE, type);

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int removeByID(String id) throws Exception {
        //Suppression d'une question de la BD à partir de l'ID
        Pair<String,String> type = selectById(id);
        String[] ids = id.split(":");
        int success;
        if (type.second.equals(QUESTION_TYPE_QCM)) {
            DAOQuestionQCM dao = new DAOQuestionQCM(this.context);
            success = dao.removeByID(id);
        } else
            throw new Exception("DAO-"+TABLE_NAME+": Unknow type");
        String where = COL_GAME_ID + " = " + ids[0] + " AND " + COL_LEVEL_ID + " = " + ids[1] + " AND " + COL_ID + "=" + ids[2];
        success += getDB().delete(TABLE_NAME, where, null);
        return success;
    }

    public int remove(Question question) throws Exception {
        return removeByID(question.getID());
    }

    public List<Question> selectAll() throws Exception {
        ArrayList<Question> res = new ArrayList<>();
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Pair<String,String>> liste = cursorToListPair(cursor);
        return getQuestionsFromListPair(liste);
    }

    public Question retrieveByID(String id) throws Exception {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        Pair<String,String> type = selectById(id);
        return getQuestionFromPair(type);
    }

    public List<Question> listByExerciseID(String id) throws Exception {
        String [] p =  id.split(":");
        if (p.length!=2) throw new Exception("DAO-"+TABLE_NAME+": Invalid id.");
        String where = COL_GAME_ID + "=? AND " + COL_LEVEL_ID + "=?";
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + where, p);
        return getQuestionsFromListPair(cursorToListPair(cursor));
    }

    private Question getQuestionFromPair(Pair<String,String> pair) throws Exception {
        Question res;
        // Rajouter les nouveaux types de question ici.
        if (pair.second.equals(QUESTION_TYPE_QCM)) {
            DAOQuestionQCM dao = new DAOQuestionQCM(this.context);
            res = dao.retrieveByID(pair.first);
            Log.d("DAO-"+TABLE_NAME,"A) Found question "+pair.first+" of type "+pair.second+" => "+res);
        } else
            throw new Exception("DAO-"+TABLE_NAME+": Unknown value for "+COL_TYPE);
        return res;
    }

    private List<Question> getQuestionsFromListPair(List<Pair<String,String>> list) throws Exception {
        ArrayList<Question> res = new ArrayList<>();
        for (Pair<String,String> type: list) {
            Question q = getQuestionFromPair(type);
            res.add(q);
            Log.d("DAO-"+TABLE_NAME,"B) Found question "+type.first+" of type "+type.second+" => "+q);
        }
        return res;
    }

    private Pair<String,String> selectById(String id) throws Exception {
        String [] p =  id.split(":");
        if (p.length!=3) throw new Exception("DAO-"+TABLE_NAME+": Invalid id.");
        String where = COL_GAME_ID + "=? AND " + COL_LEVEL_ID + "=? AND " + COL_ID + "=?";
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + where, p);
        return cursorToFirstPair(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<Pair<String,String>> cursorToListPair(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<Pair<String,String>> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToPair(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private Pair<String,String> cursorToPair(Cursor cursor) {
        // Récupére l'index des champs
        int indexGameId = cursor.getColumnIndex(COL_GAME_ID);
        int indexLevelId = cursor.getColumnIndex(COL_LEVEL_ID);
        int indexId = cursor.getColumnIndex(COL_ID);
        int indexType = cursor.getColumnIndex(COL_TYPE);

        String id = cursor.getInt(indexGameId)+":"+cursor.getInt(indexLevelId)+":"+cursor.getInt(indexId);
        String type = cursor.getString(indexType);
        return new Pair<>(id,type);
    }

    //Cette méthode permet de convertir un cursor en une question
    private Pair<String,String> cursorToFirstPair(Cursor cursor) {
        // Declaration d'une question
        Pair<String,String> res = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            res = cursorToPair(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return res;
    }
}
