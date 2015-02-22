package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class brewingActivity extends ActionBarActivity {

    private int density, waterLevel, weight;
    private TextView coffeeDensity, coffeeWeight, coffeeWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewing);

        coffeeDensity = (TextView) findViewById(R.id.densityText);
        coffeeWeight = (TextView) findViewById(R.id.weightText);
        coffeeWater = (TextView) findViewById(R.id.waterText);

        Intent i = getIntent();
        Recipe recipe = (Recipe)i.getSerializableExtra("Recipe");

        density = recipe.density;
        waterLevel = recipe.waterQuantity;
        weight = recipe.weight;

        coffeeDensity.setText(String.valueOf(density));
        coffeeWeight.setText(String.valueOf(weight));
        coffeeWater.setText(String.valueOf(waterLevel));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brewing, menu);
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
