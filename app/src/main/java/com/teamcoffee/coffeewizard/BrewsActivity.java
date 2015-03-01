package com.teamcoffee.coffeewizard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*
* Created by Brendan on 3/1/2015.
* This activity is accessed using the "Brews" button on the main screen.
* The purpose of this activity is to display to the user either their
* recent brews, or their favorite brews depending on a button or tabbed view.
*/

public class BrewsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brews);

        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String select_query = "SELECT * FROM tblRecipes";

        Cursor c = db.rawQuery(select_query, null);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brews, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
