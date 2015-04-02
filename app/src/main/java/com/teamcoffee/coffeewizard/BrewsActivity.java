package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
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
    private Button favoriteButton, recentButton;
    private ExpandableListView expandView;
    private FavoritesExpandableCursorAdapter expandAdapter;
    private FavoriteViewBinder viewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brews);

        brewsList = (ListView) findViewById(R.id.brewsList);
        favoriteButton = (Button) findViewById(R.id.favoriteBrewButton);
        recentButton = (Button) findViewById(R.id.recentBrewButton);
        recentButton.setBackgroundColor(Color.parseColor("#f1efe7"));
        favoriteButton.setBackgroundColor(Color.parseColor("#7e7a6a"));

        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        select_favorites_query = "SELECT * FROM " + DatabaseContract.TableThree.TABLE_NAME;
        select_recent_query = "SELECT * FROM " + DatabaseContract.TableFour.TABLE_NAME;

        recent = db.rawQuery(select_recent_query, null);

        favorites = db.rawQuery(select_favorites_query, null);

        expandView = (ExpandableListView) findViewById(R.id.brewsExpandList);

        RecipesCursorAdapter recipesAdapter = new RecipesCursorAdapter(this, recent);


        expandView.setVisibility(View.INVISIBLE);

        brewsList.setAdapter(recipesAdapter);
        db.close();
    }

    // Get parent activity so that up (<-) button returns to calling activity instead of starting
    //  a new one
    @Override
    public Intent getSupportParentActivityIntent () {

        return new Intent(this, getIntent().getClass());
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
            Intent intent = new Intent(this, NewFAQActivity.class);
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
        expandAdapter = new FavoritesExpandableCursorAdapter(favorites, this,
                R.layout.brews_list_group,
                R.layout.recipes_list,
                new String[] {"name"},
                new int[] {R.id.brewNameText},
                new String[] {"machine", "coffeeDensity", "coffeeWeight", "coffeeVolume", "_id", "_id"},
                new int[] {R.id.brewer, R.id.density, R.id.weight, R.id.volume, R.id.toggleButton, R.id.brewButton});
        viewBinder = new FavoriteViewBinder(this);
        expandAdapter.setViewBinder(viewBinder);
        expandView.setAdapter(expandAdapter);
        brewsList.setVisibility(View.INVISIBLE);
        expandView.setVisibility(View.VISIBLE);
        db.close();
        favoriteButton.setBackgroundColor(Color.parseColor("#f1efe7"));
        recentButton.setBackgroundColor(Color.parseColor("#7e7a6a"));

    }

    public void recentButton(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(BrewsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        recent = db.rawQuery(select_recent_query, null);
        RecipesCursorAdapter recipesAdapter = new RecipesCursorAdapter(this, recent);
        brewsList.setAdapter(recipesAdapter);
        db.close();
        brewsList.setVisibility(View.VISIBLE);
        expandView.setVisibility(View.INVISIBLE);
        recentButton.setBackgroundColor(Color.parseColor("#f1efe7"));
        favoriteButton.setBackgroundColor(Color.parseColor("#7e7a6a"));
    }

}
