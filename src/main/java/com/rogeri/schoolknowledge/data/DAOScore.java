package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 15/03/2016.
 */
public class DAOScore extends BaseDAO {
    public static final String TABLE_NAME = "Score";
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-DAO-"+TABLE_NAME;

    private static final String COL_GAME_ID = "gameID";
    private static final String COL_LEVEL_ID = "levelID";
    private static final String COL_USER_ID = "userID";
    private static final String COL_SCORE = "score";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_GAME_ID+" INTEGER,"+
            COL_LEVEL_ID+" INTEGER,"+
            COL_USER_ID+" INTEGER,"+
            COL_SCORE+" INTEGER,"+
            "PRIMARY KEY("+COL_GAME_ID+", "+COL_LEVEL_ID+", "+COL_USER_ID+"),"+
            "FOREIGN KEY("+COL_GAME_ID+") REFERENCES "+DAOGame.TABLE_NAME+"("+DAOGame.COL_ID+"),"+
            "FOREIGN KEY("+COL_LEVEL_ID+") REFERENCES "+DAOExercise.TABLE_NAME+"("+DAOExercise.COL_ID+"),"+
            "FOREIGN KEY("+COL_USER_ID+") REFERENCES "+DAOUser.TABLE_NAME+"("+DAOUser.COL_ID+")"+
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS '"+TABLE_NAME+"';";

    public DAOScore(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  GameID   LevelID   UserID   Score
                "0, 0, 0, 10",
                "0, 0, 1, 5",
                "0, 0, 2, 7",
                "0, 1, 0, 8",
                "0, 1, 1, 17",
                "0, 1, 2, 13"
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

    public long insert(Score score) throws Exception {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = score.getExerciseID().split(":");
        if(ids.length!=2) throw new Exception(LOG_TAG+": Invalid id.");
        values.put(COL_GAME_ID, ids[0]);
        values.put(COL_LEVEL_ID, ids[1]);
        values.put(COL_USER_ID, score.getUserID());
        values.put(COL_SCORE, score.getScore());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int update(Score score) throws Exception {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = score.getExerciseID().split(":");
        if(ids.length!=2) throw new Exception(LOG_TAG+": Invalid id.");
        values.put(COL_SCORE, score.getScore());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        String where = COL_GAME_ID+" = "+ids[0]+" AND "+COL_LEVEL_ID+" = "+ids[1]+" AND "+COL_USER_ID+" = "+score.getUserID();
        return getDB().update(TABLE_NAME, values, where, null);
    }

    public void updateOrInsert(Score score) {
      try {
        if (update(score)==0) {
          insert(score);
        }
      } catch(Exception e) {
        Log.e(LOG_TAG,e.getMessage());
        e.printStackTrace();
      }
    }

    public int removeByID(int gameID, int levelID, int userID) {
        //Suppression d'une question de la BD à partir de l'ID
        String where = COL_GAME_ID+" = "+gameID+" AND "+COL_LEVEL_ID+" = "+levelID+" AND "+COL_USER_ID+" = "+userID;
        return getDB().delete(TABLE_NAME, where, null);
    }

    public int remove(Score score) throws Exception {
        String[] ids = score.getExerciseID().split(":");
        if(ids.length!=2) throw new Exception(LOG_TAG+": Invalid id.");
        return removeByID(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]), score.getUserID());
    }

    public List<Score> selectAll() {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursorToListScore(cursor);
    }

    public List<Score> selectAllByUserID(int userID) {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        String [] p =  {Integer.toString(userID)};
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_USER_ID + "=?", p);

        return cursorToListScore(cursor);
    }

    public Score retrieveByID(int gameID, int levelID, int userID) {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  {Integer.toString(gameID),Integer.toString(levelID),Integer.toString(userID)};
        String where = COL_GAME_ID+" =? AND "+COL_LEVEL_ID+" =? AND "+COL_USER_ID+" =?";
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + where, p);

        return cursorToFirstScore(cursor);
    }

    public Score retrieveByID(String levelID, int userID) throws Exception {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String[] ids = levelID.split(":");
        if (ids.length!=2) throw new Exception(LOG_TAG+": Invalid id.");
        String [] p =  {ids[0],ids[1],Integer.toString(userID)};
        String where = COL_GAME_ID+" =? AND "+COL_LEVEL_ID+" =? AND "+COL_USER_ID+" =?";
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + where, p);

        return cursorToFirstScore(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<Score> cursorToListScore(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<Score> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToScore(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private Score cursorToScore(Cursor cursor) {
        // Récupére l'index des champs
        int indexGameId = cursor.getColumnIndex(COL_GAME_ID);
        int indexLevelId = cursor.getColumnIndex(COL_LEVEL_ID);
        int indexUserId = cursor.getColumnIndex(COL_USER_ID);
        int indexScore = cursor.getColumnIndex(COL_SCORE);

        int gameID = cursor.getInt(indexGameId);
        int levelID = cursor.getInt(indexLevelId);
        int userID = cursor.getInt(indexUserId);
        int score = cursor.getInt(indexScore);
        return new Score(gameID,levelID,userID,score);//, this.context
    }

    //Cette méthode permet de convertir un cursor en une question
    private Score cursorToFirstScore(Cursor cursor) {
        // Declaration d'une question
        Score score = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            score = cursorToScore(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return score;
    }
}
