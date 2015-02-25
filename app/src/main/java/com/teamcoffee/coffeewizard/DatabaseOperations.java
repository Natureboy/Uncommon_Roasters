package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.teamcoffee.coffeewizard.DatabaseContract.BrewerTable;

import static com.teamcoffee.coffeewizard.DatabaseContract.BrewerTable.*;

/**
 * Created by Brendan on 2/25/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper{

    public static final  int database_version = 1;
    public String CREATE_BREWERS = "CREATE TABLE " + TABLE_NAME + "(" + BrewerTable._ID + " INTEGER PRIMARY KEY, " + BREWER_NAME + " TEXT NOT NULL, " + BREWER_QUANTITY + " INTEGER, " + BREWER_TIME + " INTEGER );";


    public DatabaseOperations(Context context) {
        super(context, "PLACEHOLDER", null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BREWERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteOpenHelper.class.getName(), "Upgrading database.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
