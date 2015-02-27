package com.teamcoffee.coffeewizard;


import android.provider.BaseColumns;

/**
 * Created by Brendan on 2/26/2015.
 * Contains the information to build a CREATE query.
 * TODO: Add search queries? Helper methods to run insert/search queries?
 *
 */
public class DatabaseContract {

    //Only change this when the schema is being changed, this will delete any user added data
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "coffeeWizard.db";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA = ",";

    //This is done to prevent someone from instantiating the contract class.
    private DatabaseContract(){}

    public static abstract class TableOne implements BaseColumns{
        public static final String TABLE_NAME  = "tblRecipes";
        public static final String COLUMN1_NAME = "machine";
        public static final String COLUMN2_NAME = "density";
        public static final String COLUMN3_NAME = "quantity";

        public static final String CREATE_QUERY = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN1_NAME + TYPE_TEXT + COMMA +
                COLUMN2_NAME + TYPE_TEXT + COMMA +
                COLUMN3_NAME + TYPE_TEXT + " )";

        public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    /*
    This is how you connect to and use the database.
    Going to work on helper methods to make this simplier.

    //Connects to the database
    DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    //Sets the values that we want to use
    ContentValues values = new ContentValues();
    values.put(DatabaseContract.TableOne.COLUMN1_NAME, "v90");
    values.put(DatabaseContract.TableOne.COLUMN2_NAME, "123");
    values.put(DatabaseContract.TableOne.COLUMN3_NAME, "321");

    //Adds the values to the database
    long newRowId;
    newRowId = db.insert(DatabaseContract.Table1.TABLE_NAME, null, values);
    */




}
