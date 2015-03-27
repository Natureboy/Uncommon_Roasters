package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PreparationActivity extends ActionBarActivity {

    String weight, time;
    TextView prepWeight, prepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);
        Intent intent = getIntent();
        weight = intent.getStringExtra("weight");
        time = intent.getStringExtra("time");
        prepWeight = (TextView) findViewById(R.id.prepWeight);
        prepTime = (TextView) findViewById(R.id.prepTime);

        prepTime.setText(time);
        prepWeight.setText(weight);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preparation, menu);
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
            Intent intent = new Intent(this, FAQActivity.class);
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
}
