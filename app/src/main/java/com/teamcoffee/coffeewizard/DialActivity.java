package com.teamcoffee.coffeewizard;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
                if(spinner.getSelectedItem().toString().equals("V60")){
                    if(progressChanged == 0){
                        waterVolume.setText("200");
                        coffeeWeight.setText("12");
                    }
                    else if(progressChanged == 1){
                        waterVolume.setText("300");
                        coffeeWeight.setText("19");
                    }
                    else if(progressChanged == 2){
                        waterVolume.setText("400");
                        coffeeWeight.setText("25");
                    }
                }
                else if(spinner.getSelectedItem().toString().equals("Press Pot")){
                    int waterAmount = water.getProgress();
                    double coffeeAmount = waterAmount * 0.075;
                    waterVolume.setText(Integer.toString(waterAmount));
                    coffeeWeight.setText(String.format("%.2f", coffeeAmount));
                }
                else {
                    if (progressChanged == 0) {
                        waterVolume.setText("200");
                    } else if (progressChanged == 1) {
                        waterVolume.setText("300");
                    } else if (progressChanged == 2) {
                        waterVolume.setText("400");
                    }
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString().equals("V60")){
                    setScreenElements(false, "12", 2, 0, "200");
                }
                else if(spinner.getSelectedItem().toString().equals("Press Pot")){
                    setScreenElements(false, "15", 400, 200, "200");
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setScreenElements(boolean weightEnabled, String weightValue, int waterMax, int waterProgress, String waterValue){
        coffeeWeight.setEnabled(weightEnabled);
        water.setMax(waterMax);
        water.setProgress(waterProgress);
        coffeeWeight.setText(weightValue);
        waterVolume.setText(waterValue);
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
        String waterLevel;
        String weightLevel;
        Recipe result;
        int time;
        String brewer, densityLevel, time_select_query, event_select_query;


        waterLevel = (waterVolume.getText().toString());
        densityLevel = coffeeDensity.getText().toString();
        weightLevel = (coffeeWeight.getText().toString());
        brewer = spinner.getSelectedItem().toString();

        switch (densityLevel) {
            case "Low Density":
                densityLevel = "low";
                break;
            case "Medium Density":
                densityLevel = "medium";
                break;
            case "High Density":
                densityLevel = "high";
                break;
        }


        time_select_query = DatabaseContract.TableOne.createSelect(brewer, waterLevel, weightLevel, densityLevel);
        event_select_query = DatabaseContract.TableTwo.createSelect(brewer, waterLevel, densityLevel);

        DatabaseHelper dbHelper = new DatabaseHelper(DialActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(time_select_query, null);
        if(c.moveToFirst()){
            time = c.getInt(c.getColumnIndex("brewTime"));
        }
        else{
            time = -1;
        }

        c = db.rawQuery(event_select_query, null);

        int startTimeIndex = c.getColumnIndex(DatabaseContract.TableTwo.COLUMN5_NAME);
        int eventIndex = c.getColumnIndex(DatabaseContract.TableTwo.COLUMN4_NAME);

        HashMap<Integer, String> timerEvents = new HashMap<Integer, String>();

        while(c.moveToNext()){

            int startTime = c.getInt(startTimeIndex);
            String event = c.getString(eventIndex);

            timerEvents.put(startTime, event);

        }

        c.close();
        db.close();

        Intent i = new Intent(this, CountdownActivity.class);
        i.putExtra("time", Integer.toString(time));
        i.putExtra("events", timerEvents);

        startActivity(i);
    }





}
