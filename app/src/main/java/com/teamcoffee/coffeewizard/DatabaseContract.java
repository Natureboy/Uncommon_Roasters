package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Brendan on 2/25/2015.
 */
public final class DatabaseContract {
    public DatabaseContract(){}

    public static final String DATABASE_NAME = "coffeeWizard";
    public static final int DATABASE_VERSION = 1;

    public static abstract class BrewerTable implements BaseColumns{
        public static final String TABLE_NAME = "tblBrewers";
        public static final String BREWER_NAME = "brewer_name";
        public static final String BREWER_QUANTITY = "brewer_quantity";
        public static final String BREWER_TIME = "brewer_time";
    }

    public static String CREATE_BREWERS = "CREATE TABLE " + BrewerTable.TABLE_NAME + "(" + BrewerTable._ID
            + " INTEGER PRIMARY KEY, " + BrewerTable.BREWER_NAME + " TEXT NOT NULL, " + BrewerTable.BREWER_QUANTITY + " INTEGER, " + BrewerTable.BREWER_TIME + " INTEGER );";

    public static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BREWERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
