package com.teamcoffee.coffeewizard;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DialActivity extends ActionBarActivity {

    private SeekBar water;
    private SeekBar density;
    private TextView waterVolume, coffeeWeight, coffeeDensity, coffeeDensityLabel;
    private Spinner spinner;
    private AlertDialog.Builder builder1;

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
        coffeeDensityLabel = (TextView) findViewById(R.id.selectDensityText);
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
                    setScreenElements(false, "12", 2, 0, "200", View.VISIBLE);
                }
                else if(spinner.getSelectedItem().toString().equals("Press Pot")){
                    setScreenElements(false, "15", 400, 200, "200", View.GONE);
                }
                else{
                    setScreenElements(false, "15", 2, 0, "200", View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


    }

    private void setScreenElements(boolean weightEnabled, String weightValue, int waterMax, int waterProgress, String waterValue,
                                   int densityVisibility){
        coffeeWeight.setEnabled(weightEnabled);
        water.setMax(waterMax);
        water.setProgress(waterProgress);
        coffeeWeight.setText(weightValue);
        waterVolume.setText(waterValue);
        coffeeDensity.setVisibility(densityVisibility);
        density.setVisibility(densityVisibility);
        coffeeDensityLabel.setVisibility(densityVisibility);
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
        String brewer, densityLevel, time_select_query, event_select_query, brewer_list_query;


        DatabaseHelper dbHelper = new DatabaseHelper(DialActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        brewer_list_query = "SELECT DISTINCT " + DatabaseContract.TableOne.COLUMN1_NAME + " FROM " + DatabaseContract.TableOne.TABLE_NAME;
        Cursor c = db.rawQuery(brewer_list_query, null);
        String[] brewerList =  new String[c.getCount()];

        int i = 0;

        while(c.moveToNext()){
            brewerList[i] = c.getString(c.getColumnIndexOrThrow("machine"));
            i++;
        }

        brewer = spinner.getSelectedItem().toString();

        if(!Arrays.asList(brewerList).contains(brewer)){
            builder1.setMessage("Select a brewer from the dropdown list.");
            AlertDialog alert1 = builder1.create();
            alert1.show();
            return;
        }


        waterLevel = (waterVolume.getText().toString());
        densityLevel = coffeeDensity.getText().toString();
        weightLevel = (coffeeWeight.getText().toString());


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


        time_select_query = DatabaseContract.TableOne.createSelect(brewer, waterLevel, densityLevel);
        event_select_query = DatabaseContract.TableTwo.createSelect(brewer, waterLevel, densityLevel);

        if (brewer.equals("Press Pot")){
            time_select_query = DatabaseContract.TableOne.createSelect(brewer, "0", "n/a");
            event_select_query = DatabaseContract.TableTwo.createSelect(brewer, "0", "n/a");
        }

        c = db.rawQuery(time_select_query, null);
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

        if(brewer.equals("Press Pot")){
            timerEvents.put(0, "Pour to " + waterLevel + "g of Water");
        }

        Intent intent = new Intent(this, CountdownActivity.class);
        intent.putExtra("time", Integer.toString(time));
        intent.putExtra("events", timerEvents);
        intent.putExtra("weight", weightLevel);

        startActivity(intent);
    }





}
