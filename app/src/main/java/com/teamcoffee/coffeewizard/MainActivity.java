package com.teamcoffee.coffeewizard;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View con = this.findViewById(android.R.id.content);
        String size= getSizeInfo(con);
        String den= getDenInfo();

        //Used to create a database in order to inspect it outside of the app.
        //TODO: Remove when the app is ready to deploy
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void openWeb(View view){
        String url = "https://uncommoncoffeeroasters.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openDial_in(View view){
        Intent i = new Intent(this, DialActivity.class);
        startActivity(i);
    }

    public void openBrews(View view){
        Intent i = new Intent(this, BrewsActivity.class);
        startActivity(i);
    }

    //Determine screen size
    private static String getSizeInfo(View view) {
        int screenLayout = view.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                System.out.println("The size is xl");
                return "xl";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                System.out.println("The size is large");
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                System.out.println("The size is normal");
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                System.out.println("The size is small");
                return "small";
            default:
                System.out.println("The size is unknown");
                return "unknown";
        }

    }
    //Determine screen density
    private String getDenInfo() {
        DisplayMetrics metrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density= metrics.densityDpi;

        switch(density){
            case DisplayMetrics.DENSITY_XXXHIGH:
                System.out.println("The density is xxxhigh\n");
                return "xxxhigh";
            case DisplayMetrics.DENSITY_XXHIGH:
                System.out.println("The density is xxhigh\n");
                return "xxhigh";
            case DisplayMetrics.DENSITY_XHIGH:
                System.out.println("The density is xhigh\n");
                return "xhigh";
            case DisplayMetrics.DENSITY_HIGH:
                System.out.println("The density is high\n");
                return "high";
            case DisplayMetrics.DENSITY_MEDIUM:
                System.out.println("The density is med\n");
                return "med";
            case DisplayMetrics.DENSITY_LOW:
                System.out.println("The density is low\n");
                return "low";
            default:
                System.out.println("The density is unknown");
                return "unknown";
        }
    }


}
