package com.rogeri.schoolknowledge.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rogeri on 22/03/16.
 */
public abstract class BaseDAO {

    // TAG pour le log
    private static final String TAG = "DBAdapter";

    //
    private SQLiteDatabase db;
    private DBHelper databaseHelper;
    protected Context context;


    // refaire avec getWritableDatabase(); et getReadableDatabase();
    public BaseDAO(Context context) {
        this.context = context;
        databaseHelper = new DBHelper(context, null);
        open();
    }

    // On ouvre la base de données en écriture
    public void open() throws SQLException {
        db = databaseHelper.getWritableDatabase();
    }

    //on ferme l'accès à la base de données
    public void close() {
        db.close();
    }

    //
    public SQLiteDatabase getDB(){
        return db;
    }

}
