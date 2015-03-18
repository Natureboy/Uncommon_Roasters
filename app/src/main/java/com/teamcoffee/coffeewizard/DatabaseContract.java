package com.teamcoffee.coffeewizard;


import android.provider.BaseColumns;
import android.util.Pair;

/**
 * Created by Brendan on 2/26/2015.
 * Contains the information to build a CREATE query.
 * TODO: Add search queries? Helper methods to run insert/search queries?
 * TODO: NORMALIZATION TABLE1 AND TABLE2
 * TODO: Relations between tables
 *
 */
public class DatabaseContract {

    //Only change this when the schema is being changed, this will delete any user added data
    public static final int DATABASE_VERSION = 30;

    public static final String DATABASE_NAME = "coffeeWizard.db";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_INTEGER_DEFAULT = " INTEGER DEFAULT 0";
    private static final String COMMA = ", ";
    private static final String EQUALS = " = ";
    private static final String AND = " AND ";

    //This is done to prevent someone from instantiating the contract class.
    private DatabaseContract(){}

    public static abstract class TableOne implements BaseColumns{
        public static final String TABLE_NAME  = "tblRecipes";
        public static final String COLUMN1_NAME = "machine";
        public static final String COLUMN2_NAME = "coffeeVolume";
        public static final String COLUMN3_NAME = "coffeeWeight";
        public static final String COLUMN4_NAME = "coffeeDensity";
        public static final String COLUMN5_NAME = "brewTime";
        public static final String COLUMN6_NAME = "recent";
        public static Recipe[] recipes = DatabaseBuilder.recipes;
        static int n = recipes.length;

        public static final String COLUMN_LIST = " (" +
                COLUMN1_NAME + COMMA +
                COLUMN2_NAME + COMMA +
                COLUMN3_NAME + COMMA +
                COLUMN4_NAME + COMMA +
                COLUMN5_NAME + COMMA +
                COLUMN6_NAME + ")";

        public static final String CREATE_QUERY = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN1_NAME + TYPE_TEXT + COMMA +
                COLUMN2_NAME + TYPE_INTEGER + COMMA +
                COLUMN3_NAME + TYPE_INTEGER + COMMA +
                COLUMN4_NAME + TYPE_TEXT + COMMA +
                COLUMN5_NAME + TYPE_INTEGER + COMMA +
                COLUMN6_NAME + TYPE_INTEGER_DEFAULT + " )";

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static String createSelect(String machine, String coffeeVolume, String coffeeDensity){

            return "SELECT " +
                    "*" + " FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + machine  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + coffeeVolume + "'"+ AND +
                    //COLUMN3_NAME + EQUALS + "'" + coffeeWeight + "'"+ AND +
                    COLUMN4_NAME + EQUALS + "'" + coffeeDensity+ "'";

        }

        public static String[] INSERT_QUERIES = new String[n];

        public static void makeInserts(){
            for(int i =0; i < n; i++){
                String machine = recipes[i].brewer;
                int volume = recipes[i].volume;
                int weight = recipes[i].weight;
                String density = recipes[i].density;
                int time = recipes[i].brewTime;

                INSERT_QUERIES[i] = "INSERT INTO " +
                        TABLE_NAME + COLUMN_LIST+
                        " VALUES ('" +
                        machine + "'" + COMMA +
                        volume + COMMA +
                        weight + COMMA + "'" +
                        density + "'" + COMMA +
                        time + COMMA + '0' + ")";

            }
        }

        public static String addRecent(String machine, String volume, String density){
            return "UPDATE " + TABLE_NAME + " SET " + COLUMN6_NAME + EQUALS + " '1' " +
                    "WHERE " + COLUMN1_NAME + EQUALS + "'" + machine + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + volume + "'" + AND +
                    COLUMN4_NAME + EQUALS + "'" + density + "'";

        }

    }

    public static abstract class TableTwo implements BaseColumns{
        public static final String TABLE_NAME  = "tblTimerEvents";
        public static final String COLUMN1_NAME = "brewer";
        public static final String COLUMN2_NAME = "volume";
        public static final String COLUMN3_NAME = "density";
        public static final String COLUMN4_NAME = "event";
        public static final String COLUMN5_NAME = "startTime";
        public static TimerEvents[] events = DatabaseBuilder.events;
        static int n = events.length;

        public static final String CREATE_QUERY = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN1_NAME + TYPE_TEXT + COMMA +
                COLUMN2_NAME + TYPE_INTEGER + COMMA +
                COLUMN3_NAME + TYPE_TEXT + COMMA +
                COLUMN4_NAME + TYPE_TEXT + COMMA +
                COLUMN5_NAME + TYPE_INTEGER + " )";
        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String COLUMN_LIST = " (" +
                COLUMN1_NAME + COMMA +
                COLUMN2_NAME + COMMA +
                COLUMN3_NAME + COMMA +
                COLUMN4_NAME + COMMA +
                COLUMN5_NAME + ")";

        public static String[] INSERT_QUERIES = new String[n];

        public static void makeInserts(){
            for(int i =0; i < n; i++){
                String machine = events[i].brewer;
                int volume = events[i].volume;
                String density = events[i].density;
                String event = events[i].event;
                int time = events[i].startTime;

                INSERT_QUERIES[i] = "INSERT INTO " +
                        TABLE_NAME + COLUMN_LIST+
                        " VALUES ('" +
                        machine + "'" + COMMA +
                        volume + COMMA + "'" +
                        density + "'" + COMMA + "'" +
                        event + "'" + COMMA +
                        time + ")";

            }
        }

        public static String createSelect(String machine, String coffeeVolume, String coffeeDensity){

            return "SELECT " +
                    "*" + " FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + machine  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + coffeeVolume + "'"+ AND +
                    COLUMN3_NAME + EQUALS + "'" + coffeeDensity+ "'";

        }



    }

    public static abstract class TableThree implements BaseColumns{
        public static final String TABLE_NAME  = "tblFavorites";
        public static final String COLUMN1_NAME = "machine";
        public static final String COLUMN2_NAME = "coffeeVolume";
        public static final String COLUMN3_NAME = "coffeeDensity";
        public static final String COLUMN4_NAME = "coffeeWeight";
        public static final String COLUMN5_NAME = "name";

        public static final String CREATE_QUERY = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN1_NAME + TYPE_TEXT + COMMA +
                COLUMN2_NAME + TYPE_INTEGER + COMMA +
                COLUMN3_NAME + TYPE_TEXT + COMMA +
                COLUMN4_NAME + TYPE_INTEGER + COMMA +
                COLUMN5_NAME + TYPE_TEXT + COMMA +
                "UNIQUE( " + COLUMN1_NAME + COMMA + COLUMN2_NAME + COMMA + COLUMN3_NAME + COMMA + COLUMN4_NAME + " ))";

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;


        public static final String COLUMN_LIST = " (" +
                COLUMN1_NAME + COMMA +
                COLUMN2_NAME + COMMA +
                COLUMN3_NAME + COMMA +
                COLUMN4_NAME + COMMA +
                COLUMN5_NAME + ")";

        public static String insertQuery(String brewer, String volume, String density, String weight, String name){
            return "INSERT INTO " + TABLE_NAME + COLUMN_LIST + " VALUES ('" + brewer
                    + "'" + COMMA + "'" + volume + "'" + COMMA + "'" + density + "'" + COMMA + "'" + weight + "'" + COMMA + "'" + name + "'" + ")";
        }

        public static String selectQuery(String brewer, String volume, String density, String weight){
            return "SELECT " +
                    "*" + " FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + brewer  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + volume + "'" + AND +
                    COLUMN3_NAME + EQUALS + "'" + density + "'" + AND +
                    COLUMN4_NAME + EQUALS + "'" + weight + "'";
        }

        public static String deleteQuery(String brewer, String volume, String density, String weight) {
            return "DELETE FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + brewer  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + volume  + "'"+ AND +
                    COLUMN3_NAME + EQUALS + "'" + density + "'" + AND +
                    COLUMN4_NAME + EQUALS + "'" + weight + "'";

        }


    }

    public static abstract class TableFour implements BaseColumns{
        public static final String TABLE_NAME  = "tblRecent";
        public static final String COLUMN1_NAME = "machine";
        public static final String COLUMN2_NAME = "coffeeVolume";
        public static final String COLUMN3_NAME = "coffeeDensity";
        public static final String COLUMN4_NAME = "coffeeWeight";

        public static final String CREATE_QUERY = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN1_NAME + TYPE_TEXT + COMMA +
                COLUMN2_NAME + TYPE_INTEGER + COMMA +
                COLUMN3_NAME + TYPE_TEXT + COMMA +
                COLUMN4_NAME + TYPE_INTEGER + COMMA +
                "UNIQUE( " + COLUMN1_NAME + COMMA + COLUMN2_NAME + COMMA + COLUMN3_NAME + COMMA + COLUMN4_NAME + " ))";

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;


        public static final String COLUMN_LIST = " (" +
                COLUMN1_NAME + COMMA +
                COLUMN2_NAME + COMMA +
                COLUMN3_NAME + COMMA +
                COLUMN4_NAME + ")";

        public static String insertQuery(String brewer, String volume, String density, String weight){
            return "INSERT INTO " + TABLE_NAME + COLUMN_LIST + " VALUES ('" + brewer
                    + "'" + COMMA + "'" + volume + "'" + COMMA + "'" + density + "'" + COMMA + "'" + weight + "'" + ")";
        }

        public static String selectQuery(String brewer, String volume, String density, String weight){
            return "SELECT " +
                    "*" + " FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + brewer  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + volume + "'" + AND +
                    COLUMN3_NAME + EQUALS + "'" + density + "'" + AND +
                    COLUMN4_NAME + EQUALS + "'" + weight + "'";
        }

        public static String deleteQuery(String brewer, String volume, String density, String weight) {
            return "DELETE FROM " +
                    TABLE_NAME + " WHERE " +
                    COLUMN1_NAME + EQUALS + "'" + brewer  + "'" + AND +
                    COLUMN2_NAME + EQUALS + "'" + volume  + "'"+ AND +
                    COLUMN3_NAME + EQUALS + "'" + density + "'" + AND +
                    COLUMN4_NAME + EQUALS + "'" + weight + "'";

        }


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
    newRowId = db.insert(DatabaseContract.TableOne.TABLE_NAME, null, values);
    */




}
