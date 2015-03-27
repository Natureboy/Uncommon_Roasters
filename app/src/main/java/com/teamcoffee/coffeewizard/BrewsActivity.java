package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/*
* Created by Brendan on 3/1/2015.
* This activity is accessed using the "Brews" button on the main screen.
* The purpose of this activity is to display to the user either their
* recent brews, or their favorite brews depending on a button or tabbed view.
*/

public class BrewsActivity extends ActionBarActivity {

    private ListView brewsList;
    private Cursor favorites, recent;
    private String select_favorites_query, select_recent_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brews);
        brewsList = (ListView) findViewById(R.id.brewsList);

        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        select_favorites_query = "SELECT * FROM " + DatabaseContract.TableThree.TABLE_NAME;
        select_recent_query = "SELECT * FROM " + DatabaseContract.TableFour.TABLE_NAME;

        recent = db.rawQuery(select_recent_query, null);

        RecipesCursorAdapter recipesAdapter = new RecipesCursorAdapter(this, recent);

        brewsList.setAdapter(recipesAdapter);
        db.close();
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
        if (id == R.id.action_faq) {
            Intent intent = new Intent(this, FAQActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about_us) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_contact_us) {
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void favoriteButton(View view){

        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        favorites = db.rawQuery(select_favorites_query, null);
        RecipesCursorAdapterFavorites recipesAdapter = new RecipesCursorAdapterFavorites(this, favorites);
        brewsList.setAdapter(recipesAdapter);
        db.close();
    }

    public void recentButton(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        recent = db.rawQuery(select_recent_query, null);
        RecipesCursorAdapter recipesAdapter = new RecipesCursorAdapter(this, recent);
        brewsList.setAdapter(recipesAdapter);
        db.close();
    }

}
