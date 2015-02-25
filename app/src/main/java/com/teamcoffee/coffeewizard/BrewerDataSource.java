package com.teamcoffee.coffeewizard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.teamcoffee.coffeewizard.DatabaseContract.BrewerTable;

import java.sql.SQLException;


/**
 * Created by Brendan on 2/25/2015.
 */
public class BrewerDataSource {

    private SQLiteDatabase database;
    private DatabaseOperations dbhelper;

    public BrewerDataSource(Context context){
        dbhelper = new DatabaseOperations(context);
    }

    public void open() throws SQLException{
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public long createBrewer(String name, int quantity, int time){
        ContentValues values = new ContentValues();
        values.put(BrewerTable.BREWER_NAME, name);
        values.put(BrewerTable.BREWER_QUANTITY, quantity);
        values.put(BrewerTable.BREWER_TIME, time);
        long newRowId;
        newRowId = database.insert(BrewerTable.BREWER_NAME, null, values);
        return newRowId;
    }


}

