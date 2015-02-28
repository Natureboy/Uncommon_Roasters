package com.teamcoffee.coffeewizard;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.sql.SQLException;

public class DialActivity extends ActionBarActivity {

    private SeekBar water;
    private SeekBar density;
    private TextView waterVolume, coffeeWeight, coffeeDensity;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_in);

        water = (SeekBar) findViewById(R.id.waterSeekBar);
        density = (SeekBar) findViewById(R.id.densitySeekBar);
        coffeeWeight = (TextView) findViewById(R.id.coffeeWeightValue);
        waterVolume = (TextView)findViewById(R.id.waterVolumeNumber);
        coffeeDensity = (TextView) findViewById(R.id.coffeeDensityValue);
        waterVolume.setText(Integer.toString(water.getProgress()));
        coffeeDensity.setText("Medium Density");
        waterVolume.setText("200");

        spinner = (Spinner) findViewById(R.id.brewSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.brewers, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        water.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                waterVolume.setText(Integer.toString(progressChanged));
                if(progressChanged == 0){
                    waterVolume.setText("200");
                }
                else if(progressChanged == 1){
                    waterVolume.setText("300");
                }
                else if(progressChanged == 2){
                    waterVolume.setText("400");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        density.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                if(progressChanged == 0){
                    coffeeDensity.setText("Low Density");
                }
                else if(progressChanged == 1){
                    coffeeDensity.setText("Medium Density");
                }
                else if(progressChanged == 2){
                    coffeeDensity.setText("High Density");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dial_in, menu);
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

    public void brewButton(View view) throws SQLException {
        int waterLevel, densityLevel, weightLevel;
        Recipe result;
        String brewer;

        waterLevel = Integer.parseInt(waterVolume.getText().toString());
        densityLevel = density.getProgress();
        weightLevel = Integer.parseInt(coffeeWeight.getText().toString());
        brewer = spinner.getSelectedItem().toString();

        //result = new Recipe(densityLevel,weightLevel,waterLevel, brewer);
        Intent i = new Intent(this, CountdownActivity.class);
        //i.putExtra("Recipe", result);
        startActivity(i);
    }





}
