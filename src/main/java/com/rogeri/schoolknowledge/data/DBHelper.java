package com.rogeri.schoolknowledge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rogeri.schoolknowledge.SchoolKnowledge;
/**
 * Created by rogeri on 22/03/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    // VERSION de la bdd, permet les mises à jour des tables et champs au lancement de l'application
    private static final int VERSION = 13;

    // NOM de la base
    private static final String DATABASE_NAME = "database_rogeri_schoolknowledge";

    // TAG pour le log
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-DAO-DBHelper";

    // Constructeur
    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Créer la table question
        db.execSQL(DAOUser.CREATE_TABLE);
        db.execSQL(DAOGame.CREATE_TABLE);
        db.execSQL(DAOExercise.CREATE_TABLE);
        db.execSQL(DAOScore.CREATE_TABLE);
        db.execSQL(DAOQuestion.CREATE_TABLE);
        db.execSQL(DAOQuestionQCM.CREATE_TABLE);


        // Insérer les données
        for (String insert : DAOUser.getInsertSQL()) {
            db.execSQL(insert);
        }
        for (String insert : DAOGame.getInsertSQL()) {
            db.execSQL(insert);
        }
        for (String insert : DAOExercise.getInsertSQL()) {
            db.execSQL(insert);
        }
        for (String insert : DAOScore.getInsertSQL()) {
            db.execSQL(insert);
        }
        for (String insert : DAOQuestion.getInsertSQL()) {
            db.execSQL(insert);
        }
        for (String insert : DAOQuestionQCM.getInsertSQL()) {
            db.execSQL(insert);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Log
        Log.w(LOG_TAG, "UPGRADING DATABASE FROM VERSION " + oldVersion
                + " TO "
                + newVersion + ", WHICH WILL DESTROY ALL OLD DATA !");

        // DROP
        db.execSQL(DAOQuestionQCM.DROP_TABLE);
        db.execSQL(DAOQuestion.DROP_TABLE);
        db.execSQL(DAOScore.DROP_TABLE);
        db.execSQL(DAOExercise.DROP_TABLE);
        db.execSQL(DAOGame.DROP_TABLE);
        db.execSQL(DAOUser.DROP_TABLE);

        // Relancer la création
        onCreate(db);
    }
}
