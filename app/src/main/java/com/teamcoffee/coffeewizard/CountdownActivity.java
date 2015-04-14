package com.teamcoffee.coffeewizard;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
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
    private boolean finished = false;
    private Button startButton;
    public TextView timerText;  // Text showing current value of timer in minutes & seconds
    public TextView instrText;  // Text describing additional instructions
    public RelativeLayout flashLayer;
    public String instrString;
    int s; // Timer duration in seconds
    private long startTime;
    private long overflowStart; // set to 2.8% of main timer for overflow timer
    private final long interval = 1000; //We increment by 1000 milliseconds each tick
    private HashMap<Integer,String> events;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager notifyMgr;
    private int notifyID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        startButton = (Button) this.findViewById(R.id.button);
        startButton.setOnClickListener(this);
        timerText = (TextView) this.findViewById(R.id.timer);
        instrText = (TextView) this.findViewById(R.id.instructions);
        flashLayer = (RelativeLayout) findViewById(R.id.flash);

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

        startTime = s * 1000; //Converts seconds to milliseconds, which CountdownTimers use
        overflowStart = (long) Math.ceil((double)s * 0.028) * 1000;
        cdTimer = new CoffeeCountDown(startTime, interval);
        overflowTimer = new CoffeeOverflow(overflowStart, interval);
        timerText.setText(millisToString(startTime));
//        instrText.setText("Set up your machine with your selected coffee, then press Start. " +
//                "Additional instructions will appear here.");
        instrText.setText("Additional instructions will appear here during brewing.");
        Uri notifySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_not)
                .setSound(notifySound)
                .setContentTitle("Coffee Wizard")
                .setContentText("New Brewing Step");

        notifyID = 0;

        Intent resultIntent = new Intent(this, CountdownActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
    }


    @Override
    public void onBackPressed() {
        cdTimer.cancel();
        overflowTimer.cancel();
        try {
            notifyMgr.cancelAll();
        } catch (NullPointerException e){

        }
        super.onBackPressed();
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
        if (finished) {
            try {
                notifyMgr.cancelAll();
            } catch (NullPointerException e){

            }
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else if (!hasStarted) {
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
            try {
                notifyMgr.cancelAll();
            } catch (NullPointerException e){

            }
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
            finished = true;
            // Make sure the "Done!" message doesn't accidentally get dimmed by the blink
            isBlinking = false;
            timerText.setAlpha(1);
            flashLayer.setBackgroundColor(Color.WHITE);
            flashLayer.setAlpha(0);
            notifyMgr.cancelAll();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timerText.setText(millisToString((int)Math.floor(millisUntilFinished)));

            if (millisUntilFinished <= 5000) {
                timerText.setTextColor(0xFF00CC00);
//                isBlinking = true;
                instrText.setText("Brewing Nearly Finished");
                instrText.setTextColor(0xFF00CC00);
                if (millisUntilFinished > 4000) {
                    // ~5 second mark
//                    instrText.setBackgroundColor(0x44D2CCB2);
                    flashLayer.setBackgroundColor(0xFF00CC00);
                    flashLayer.setAlpha(0.9f);
                    mBuilder.setContentTitle("Coffee Wizard")
                            .setContentText("Brewing almost finished!")
                            .setTicker("Brewing almost finished!")
                            .setColor(0x8800CC00)
                            .setLights(0xFF00CC00, 500, 500);
                    notifyID++;
                    notifyMgr.notify(notifyID, mBuilder.build());
                    notifyMgr.cancel(notifyID - 1);
                } else {
//                    instrText.setBackgroundColor(0xFF2A2A2A);
                    flashLayer.setAlpha(0);
                }
            } else if ((instrString=events.get((int)((s*1000)-millisUntilFinished)/1000)) != null) {
                // If instruction field is updated
                instrText.setText(instrString);
//                instrText.setTextColor(0xFF2A2A2A);
//                instrText.setBackgroundColor(0xFFD2CCB2);
                if (instrString.length() > 0 && ((s*1000)-millisUntilFinished)/1000 > 0) {
                    flashLayer.setAlpha(0.9f);

                    // Issue a notification
                    notifyID++;
                    notifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mBuilder.setContentText(instrString);
                    mBuilder.setTicker(instrString);
                    notifyMgr.notify(notifyID, mBuilder.build());
                    if (notifyID > 1) {
                        notifyMgr.cancel(notifyID - 1);
                    }
                }
            } else {
//                instrText.setTextColor(0xFFD2CCB2);
//                instrText.setBackgroundColor(0xFF2A2A2A);
                flashLayer.setAlpha(0);
            }

            if (isBlinking) {
                //TODO set text to blink when timer nearly done?
                timerText.setAlpha(1/(timerText.getAlpha()/0.25f));
            }
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
            instrText.setText("Coffee may be overbrewed now! Please see FAQ if needed.");
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
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
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
        if (id == android.R.id.home){
            cdTimer.cancel();
            overflowTimer.cancel();
            try {
                notifyMgr.cancelAll();
            } catch (NullPointerException e){

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
