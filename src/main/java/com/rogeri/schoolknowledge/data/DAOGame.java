package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rogeri on 18/03/16.
 */
public class DAOGame extends BaseDAO {
    public static final String TABLE_NAME = "Game";

    public static final String COL_ID = "id"; // Public: it's a new ID
    private static final String COL_NAME = "name";
    private static final String COL_INFO = "info";
    private static final String COL_PIC = "picID";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_ID+" INTEGER PRIMARY KEY,"+
            COL_NAME+" VARCHAR(50),"+
            COL_INFO+" VARCHAR(200),"+
            COL_PIC+" INTEGER"+
            ");";

    public static final String DROP_TABLE = "DROP TABLE "+TABLE_NAME+" IF EXISTS;";

    public DAOGame(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  ID   Name  Info
                "0, 'Maths', 'Additions, soustractions, et autres opérations.', 1",
                "1, 'Histoire', 'Histoire de France et du monde.', 0",
                "2, 'Culture générale', 'Culture et questions générales.', 0"
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

    public long insert(Game game) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(COL_ID, game.getID());
        values.put(COL_NAME, game.getName());
        values.put(COL_INFO, game.getInfo());
        values.put(COL_PIC, game.getPic());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int update(Game game) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(COL_ID, game.getID());
        values.put(COL_NAME, game.getName());
        values.put(COL_INFO, game.getInfo());
        values.put(COL_PIC, game.getPic());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        String where = COL_ID + " = " + game.getID();
        return getDB().update(TABLE_NAME, values, where, null);
    }

    public int removeByID(int id) {
        //Suppression d'une question de la BD à partir de l'ID
        String where = COL_ID + " = " + id;
        return getDB().delete(TABLE_NAME, where, null);
    }

    public int remove(Game game) {
        return removeByID(game.getID());
    }

    public List<Game> selectAll() {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursorToListGame(cursor);
    }

    public Game retrieveByID(int id) {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  {Integer.toString(id)};
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=?", p);

        return cursorToFirstGame(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<Game> cursorToListGame(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<Game> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToGame(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private Game cursorToGame(Cursor cursor) {
        // Récupére l'index des champs
        int indexId = cursor.getColumnIndex(COL_ID);
        int indexName = cursor.getColumnIndex(COL_NAME);
        int indexInfo = cursor.getColumnIndex(COL_INFO);
        int indexPic = cursor.getColumnIndex(COL_PIC);

        int id = cursor.getInt(indexId);
        String name = cursor.getString(indexName);
        String info = cursor.getString(indexInfo);
        int pic = cursor.getInt(indexPic);
        return new Game(id,name,info,pic);//, this.context
    }

    //Cette méthode permet de convertir un cursor en une question
    private Game cursorToFirstGame(Cursor cursor) {
        // Declaration d'une question
        Game game = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            game = cursorToGame(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return game;
    }
}
