package com.example.art.mysimplelist.db;

import android.provider.BaseColumns;
/**
 * Created by art on 20/04/2017.
 */

public class Note {
    public static final String DB_NAME = "art.example.com.notelist.db";
    public static final int DB_VERSION = 1;

    public class NoteEntry implements BaseColumns{
        public static final String TABLE = "notes";
        public static final String COLUMN = "title";
    }
}
