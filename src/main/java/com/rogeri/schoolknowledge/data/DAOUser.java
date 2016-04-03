package com.rogeri.schoolknowledge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 15/03/2016.
 */
public class DAOUser extends BaseDAO {
    public static final String TABLE_NAME = "User";
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-DAO-"+TABLE_NAME;

    public static final String COL_ID = "id"; // Public: it's a new ID
    private static final String COL_NAME = "name";
    private static final String COL_PIC = "picID";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_ID+" INTEGER PRIMARY KEY,"+
            COL_NAME+" VARCHAR(50),"+
            COL_PIC+" INTEGER"+
            ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS '"+TABLE_NAME+"';";

    public DAOUser(Context ctx) {
        super(ctx);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " VALUES ";

        String[] DATA = {
                //  ID   Name  Pic
                "0, 'Jean', 1",
                "1, 'Pascal', 2",
                "2, 'Louis', 0"
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

    public long insert(User user) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(COL_ID, user.getID());
        values.put(COL_NAME, user.getName());
        values.put(COL_PIC, user.getPic());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_NAME, null, values);
    }

    public int update(User user) {
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(COL_NAME, user.getName());
        values.put(COL_PIC, user.getPic());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        String where = COL_ID + " = " + user.getID();
        return getDB().update(TABLE_NAME, values, where, null);
    }

    public int removeByID(int id) {
        //Suppression d'une question de la BD à partir de l'ID
        String where = COL_ID + " = " + id;
        return getDB().delete(TABLE_NAME, where, null);
    }

    public int remove(User user) {
        return removeByID(user.getID());
    }

    public List<User> selectAll() {
        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursorToListUser(cursor);
    }

    public User retrieveByID(int id) {
        //Récupère dans un Cursor les valeur correspondant à une question contenu dans la BD à l'aide de son id
        String [] p =  {Integer.toString(id)};
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=?", p);

        return cursorToFirstUser(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<User> cursorToListUser(Cursor cursor) {
        // Declaration et initialisation d'une liste de question
        ArrayList<User> liste = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Création d'une question et ajout dans la liste
            liste.add(cursorToUser(cursor));
        }
        // Fermeture du cursor
        cursor.close();

        return liste;
    }

    private User cursorToUser(Cursor cursor) {
        // Récupére l'index des champs
        int indexId = cursor.getColumnIndex(COL_ID);
        int indexName = cursor.getColumnIndex(COL_NAME);
        int indexPic = cursor.getColumnIndex(COL_PIC);

        int id = cursor.getInt(indexId);
        String name = cursor.getString(indexName);
        int pic = cursor.getInt(indexPic);
        return new User(id,name,pic, this.context);
    }

    //Cette méthode permet de convertir un cursor en une question
    private User cursorToFirstUser(Cursor cursor) {
        // Declaration d'une question
        User user = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // Création d'une question
            user = cursorToUser(cursor);
        }

        // Fermeture du cursor
        cursor.close();

        return user;
    }
}
