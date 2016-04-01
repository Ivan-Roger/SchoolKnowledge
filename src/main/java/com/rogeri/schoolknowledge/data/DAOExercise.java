package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rogeri on 18/03/16.
 */
public class DAOExercise extends BaseDAO {
    public static final String TABLE_NAME = "Exercise";

    private static final String COL_GAME_ID = "gameID";
    public static final String COL_ID = "id"; // Public: it's a new ID
    private static final String COL_NAME = "name";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_GAME_ID+" INTEGER,"+
            COL_ID+" INTEGER,"+
            COL_NAME+" VARCHAR(50),"+
            "PRIMARY KEY("+COL_GAME_ID+", "+COL_ID+"),"+
            "FOREIGN KEY("+COL_GAME_ID+") REFERENCES "+DAOGame.TABLE_NAME+"("+DAOGame.COL_ID+")"+
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS '"+TABLE_NAME+"';";

    public DAOExercise(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  IDs    Level
                "0, 0, 'Niveau 1'",
                "0, 1, 'Niveau 2'",
                "0, 2, 'Niveau 3'"
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

    public long insert(Exercise exo) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = exo.getID().split(":");
        values.put(COL_GAME_ID, ids[0]);
        values.put(COL_ID, ids[1]);
        values.put(COL_NAME, exo.getName());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int update(Exercise exo) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        String[] ids = exo.getID().split(":");
        values.put(COL_GAME_ID, ids[0]);
        values.put(COL_ID, ids[1]);
        values.put(COL_NAME, exo.getName());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        String where = COL_GAME_ID + " = " + ids[0] + " AND " + COL_ID + " = " + ids[1];
        return getDB().update(TABLE_NAME, values, where, null);
    }

    public int removeByID(String id) {
        //Suppression d'une question de la BD à partir de l'ID
        String[] ids = id.split(":");
        String where = COL_GAME_ID + " = " + ids[0] + " AND " + COL_ID + " = " + ids[1];
        return getDB().delete(TABLE_NAME, where, null);
    }

    public int remove(Exercise exo) {
        return removeByID(exo.getID());
    }

    public List<Exercise> selectAll() {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursorToListExercise(cursor);
    }

    public Exercise retrieveByID(String id) throws Exception {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  id.split(":");
        if(p.length!=2) throw new Exception("DAO-"+TABLE_NAME+": Invalid id.");
        String where = COL_GAME_ID + "=? AND "+COL_ID+"=?";
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + where, p);

        return cursorToFirstExercise(cursor);
    }

    public List<Exercise> listByGameID(int id) {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  {Integer.toString(id)};
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_GAME_ID + "=?", p);

        return cursorToListExercise(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<Exercise> cursorToListExercise(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<Exercise> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToExercise(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private Exercise cursorToExercise(Cursor cursor) {
        // Récupére l'index des champs
        int indexGameId = cursor.getColumnIndex(COL_GAME_ID);
        int indexId = cursor.getColumnIndex(COL_ID);
        int indexLevel = cursor.getColumnIndex(COL_NAME);

        int gameID = cursor.getInt(indexGameId);
        int id = cursor.getInt(indexId);
        String level = cursor.getString(indexLevel);
        Exercise res = new Exercise(gameID,id,level,this.context);
        Log.d("DAO-"+TABLE_NAME,"Found exercise "+res+", id: "+res.getID()+" with name \""+res.getName()+"\".");
        return res;
    }

    //Cette méthode permet de convertir un cursor en une question
    private Exercise cursorToFirstExercise(Cursor cursor) {
        // Declaration d'une question
        Exercise exo = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            exo = cursorToExercise(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return exo;
    }
}
