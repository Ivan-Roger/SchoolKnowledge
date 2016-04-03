package com.rogeri.schoolknowledge.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rogeri.schoolknowledge.SchoolKnowledge;
/**
 * Created by rogeri on 22/03/16.
 */
public abstract class BaseDAO {

    // TAG pour le log
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-DBAdapter";

    //
    private SQLiteDatabase db;
    private DBHelper databaseHelper;
    protected Context context;


    // refaire avec getWritableDatabase(); et getReadableDatabase();
    public BaseDAO(Context context) {
        this.context = context;
        databaseHelper = new DBHelper(context, null);
        //open();
    }

    // On ouvre la base de données en écriture
    public void open() {
        //Log.d(LOG_TAG,"Oppenning database");
        try {
          db = databaseHelper.getWritableDatabase();
        } catch(SQLException e) {
          Log.e(LOG_TAG,e.getMessage());
        }
    }

    //on ferme l'accès à la base de données
    public void close() {
        db.close();
    }

    //
    public SQLiteDatabase getDB(){
      if (db==null)
        open();
      return db;
    }

}
