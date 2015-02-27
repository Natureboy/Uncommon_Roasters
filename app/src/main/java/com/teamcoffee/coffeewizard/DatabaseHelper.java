package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brendan on 2/26/2015.
 * This class is responsible for building the database if it does not exist.
 * If the database does exist onUpgrade checks to see if the version has been incremented,
 * and updates the database if that is the case.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context){
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    //This will contain multiple CREATE queries when they are made up.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableOne.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.TableOne.DELETE_QUERY);
        onCreate(db);

    }
}
