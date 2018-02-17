package com.example.ilenwabordavid.a2do;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;


/**
 * Created by ILENWABOR DAVID on 14/06/2017.
 */

public class ToDoDatabase extends SQLiteOpenHelper {
    public static final String LIST_MESSAGE="message";
    public static int DB_VERSION=1;
    public static String DB_NAME="ToDoDatabase";
    public ToDoDatabase(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                ("CREATE TABLE LIST (" +
                        "_id INTEGER KEY," +
                        "TODO TEXT);"
                )
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
