package com.example.art.mysimplelist.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by art on 20/04/2017.
 */

public class NoteHelper extends SQLiteOpenHelper {

    public NoteHelper(Context context){
        super(context, Note.DB_NAME, null, Note.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + Note.NoteEntry.TABLE + " ( " +
                                               Note.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                               Note.NoteEntry.COLUMN + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST " + Note.NoteEntry.TABLE); //sql klaida, gali trukt -> ;
        onCreate(db);
    }
}

