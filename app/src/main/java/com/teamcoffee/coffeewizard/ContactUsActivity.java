package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Alex Suriano on 3/25/2015.
 */
public class ContactUsActivity  extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    // Dynamically set the parent activity so that the up (<-) button in the Action Bar will
    //  lead to the appropriate previous screen
    @Override
    public Intent getSupportParentActivityIntent() {

        return new Intent(this, getIntent().getClass());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_us, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void facebook(View view) {
        String url = "https://www.facebook.com/uncommongroundssaugatuck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void twitter(View view) {
        String url = "https://twitter.com/SaugatuckUG";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void googlePlus(View view) {
        String url = "https://plus.google.com/+UncommonCoffeeRoastersSaugatuck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void foursqaure(View view) {
        String url = "https://foursquare.com/v/uncommon-coffee-roasters/4b4bc07ef964a52070a626e3";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void instagram(View view) {
        String url = "https://instagram.com/uncommoncoffeeroasters/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void yelp(View view) {
        String url = "http://www.yelp.com/biz/uncommon-coffee-roasters-saugatuck";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void playStore(View view) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Coffee Wizard");
            String sAux = "Check out Coffee Wizard in the Play Store\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.teamcoffee.coffeewizard";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share"));
        } catch (Exception e) { //e.toString();
        }
    }
}
