package com.teamcoffee.coffeewizard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

/* CountdownActivity
*   This activity handles the timer screen. Currently, the value of the timer is hard-coded
*   for demonstration purposes, but eventually it will be passed to this activity by other
*   sections of the app (namely, either the Dial-In screen or the Favorites menu).*/
public class CountdownActivity extends ActionBarActivity implements View.OnClickListener {

    // cdTimer: Main timer handling the time required for brewing.
    private CountDownTimer cdTimer;
    // overflowTimer: Timer handling the grace period before coffee is considered overbrewed.
    private CountDownTimer overflowTimer;
    private boolean hasStarted = false;
    private boolean hasStartedOverflow = false;
    private boolean isBlinking = false;
    private Button startButton;
    public TextView timerText;  // Text showing current value of timer in minutes & seconds
    public TextView instrText;  // Text describing additional instructions
    public RelativeLayout flashLayer;
    public String instrString;
    int s; // Timer duration in seconds
    private long startTime;
    private long overflowStart; // set to 2.8% of main timer for overflow timer
    //TODO: Check with client of proper value of overflow timer (i.e., margin of error on brew time)
    private final long interval = 1000; //We increment by 1000 milliseconds each tick
    private HashMap<Integer,String> events;

    //TODO Run in background
    //TODO Alerts, notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        startButton = (Button) this.findViewById(R.id.button);
        startButton.setOnClickListener(this);
        timerText = (TextView) this.findViewById(R.id.timer);
        instrText = (TextView) this.findViewById(R.id.instructions);
        flashLayer = (RelativeLayout) this.findViewById(R.id.timer_flash);

        //Gets the brewTime from the intent, or if there is no brewtime in the intent
        //(running just the CountdownActivity) goes to a default time of 15 seconds.
        Intent i = getIntent();
        if (i.getStringExtra("time") == null){
            s = 15;
        }
        else {
            s = Integer.parseInt(i.getStringExtra("time"));
        }

        events = (HashMap<Integer,String>) i.getSerializableExtra("events");
        //TODO Look into whether it would be better to use a Timer to schedule these events instead

        startTime = s * 1000; //Converts seconds to milliseconds, which CountdownTimers use
        overflowStart = (long) Math.ceil((double)s * 0.028) * 1000;
        cdTimer = new CoffeeCountDown(startTime, interval);
        overflowTimer = new CoffeeOverflow(overflowStart, interval);
        timerText.setText(timerText.getText() + millisToString(startTime));
        instrText.setText("Set up your machine with your selected coffee, then press Start. " +
                "Additional instructions will appear here.");
    }

    // Dynamically set the parent activity so that the up (<-) button in the Action Bar will
    //  lead to the appropriate previous screen
    @Override
    public Intent getSupportParentActivityIntent () {
        Intent upButtonIntent = new Intent(this, getIntent().getClass());

        return upButtonIntent;
    }

    // Method for handling behavior when button is tapped
    @Override
    public void onClick(View v) {
        if (!hasStarted) {
            timerText.setTextColor(0xFFD2CCB2);
            instrText.setTextColor(0xFFD2CCB2);
            instrText.setText("");
            cdTimer.start();
            hasStarted = true;
            startButton.setText("Cancel");
        } else {
            cdTimer.cancel();
            hasStarted = false;
            startButton.setText("Restart");
            timerText.setText(millisToString(startTime));
            instrText.setText("");
        }

        if (hasStartedOverflow) {
            overflowTimer.cancel();
        }
    }

    // Class handling the main timer, including when to change colors.
    // Eventually, this should also handle alerts as well - e.g. sounds & push notifications
    public class CoffeeCountDown extends CountDownTimer {
        public CoffeeCountDown(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timerText.setText("Done!");
            timerText.setTextColor(0xFFD2CCB2);
            instrText.setText("");
            startButton.setText("Done");
            overflowTimer.start();
            hasStartedOverflow = true;
            // Make sure the "Done!" message doesn't accidentally get dimmed by the blink
            isBlinking = false;
            timerText.setAlpha(1);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText(millisToString(millisUntilFinished));

            if (millisUntilFinished <= 5000) {
                timerText.setTextColor(0xFF00CC00);
                instrText.setText("Brewing Nearly Finished");
                instrText.setTextColor(0xFF00CC00);
                if (millisUntilFinished > 4000) {
                    instrText.setBackgroundColor(0x44D2CCB2);
                } else {
                    instrText.setBackgroundColor(0xFF2A2A2A);
                }
            } else if ((instrString=events.get((int)((s*1000)-millisUntilFinished)/1000)) != null) {
                instrText.setText(instrString);
//                instrText.setTextColor(0xFF2A2A2A);
//                instrText.setBackgroundColor(0xFFD2CCB2);
                if(instrString=="") {
                    flashLayer.setAlpha(0.8f);
                }
            } else {
//                instrText.setTextColor(0xFFD2CCB2);
//                instrText.setBackgroundColor(0xFF2A2A2A);
                flashLayer.setAlpha(0);
            }

            if (isBlinking) {
                //TODO set text to blink when timer nearly done
                timerText.setAlpha(1/(timerText.getAlpha()/0.25f));
            }

            // Update the instructions field with text from the HashMap, if any
            // Values are stored in HashMap as time elapsed, so time remaining must be converted
            //  in order to obtain a usable key



            // Following code does not work for clearing instructions. Check if needed.
//            if((instrString=events.get((int)((s*1000)-millisUntilFinished)/1000)) == "\n") {
//                // If the element in the HashMap is a string consisting only of a newline character,
//                // interpret it as a signal to clear the instrText field
//                instrText.setText("");
//            } else if ((instrString=events.get((int)((s*1000)-millisUntilFinished)/1000)) != null) {
//                instrText.setText(instrString);
//                // Add an event to the hashmap to clear the instruction text some time later
//                // Currently set at 15 seconds; check if this is a good interval or even needed
//                events.put(((int) (((startTime*1000)-millisUntilFinished)/1000)+15), "\n");
//            }
        }
    }

    // Class handling the "overflow" timer, which notifies of a likely overbrew.
    // Will eventually prompt user with the FAQ if timer was not stopped in time.
    public class CoffeeOverflow extends CountDownTimer {
        public CoffeeOverflow(long overflowStart, long interval) {
            super(overflowStart, interval);
        }

        @Override
        public void onFinish() {
            instrText.setText("Coffee overbrewed!");
            instrText.setTextColor(0xFFCC0000);
            hasStartedOverflow = false;
        }

        @Override
        public void onTick(long millisUntilFinished2) {
            instrText.setText("Brewing done! Please remove grounds now.");
        }
    }

    // Helper method to easily convert the milliseconds from CountDownTimer into a readable String.
    public String millisToString (long m) {
        m = m /1000;
        if (m < 60) {
            return "" + m;
        } else if (m % 60 < 10) {
            return "" + (m / 60) + ":0" + (m % 60);
        } else {
            return "" + (m / 60) + ":" + (m % 60);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countdown, menu);
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
