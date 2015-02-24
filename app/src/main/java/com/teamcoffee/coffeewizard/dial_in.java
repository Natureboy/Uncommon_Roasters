package com.teamcoffee.coffeewizard;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class dial_in extends ActionBarActivity {

    private SeekBar water;
    private SeekBar density;
    private TextView waterVolume, coffeeWeight, coffeeDensity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_in);

        water = (SeekBar) findViewById(R.id.waterSeekBar);
        density = (SeekBar) findViewById(R.id.densitySeekBar);

        coffeeWeight = (TextView) findViewById(R.id.editText);
        waterVolume = (TextView)findViewById(R.id.waterVolumeNumber);
        coffeeDensity = (TextView) findViewById(R.id.coffeeDensityValue);
        waterVolume.setText(Integer.toString(water.getProgress()));
        coffeeDensity.setText(Integer.toString(density.getProgress()));

        water.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                waterVolume.setText(Integer.toString(progressChanged));
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
                coffeeDensity.setText(Integer.toString(progressChanged));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Intent intent = getIntent();
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

    public void brewButton(View view){
        int waterLevel, densityLevel, weightLevel;
        Recipe result;

        waterLevel = water.getProgress();
        densityLevel = density.getProgress();
        weightLevel = Integer.parseInt(coffeeWeight.getText().toString());

        result = new Recipe(densityLevel,weightLevel,waterLevel);
        Intent i = new Intent(this, brewingActivity.class);
        i.putExtra("Recipe", result);

        startActivity(i);
    }


}
