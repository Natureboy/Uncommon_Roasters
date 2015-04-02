package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;


public class FAQActivity extends ActionBarActivity {

    private FragmentNavigationDrawer dlDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
//        // Setup drawer view
//        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), toolbar,
//                R.layout.nav_drawer_item, R.id.flContent);
        // Add nav items
        dlDrawer.addNavItem("First", "First Fragment", GrindFragment.class);

        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }


    // Dynamically set the parent activity so that the up (<-) button in the Action Bar will
    //  lead to the appropriate previous screen
    @Override
    public Intent getSupportParentActivityIntent () {
        Intent upButtonIntent = new Intent(this, getIntent().getClass());

        return upButtonIntent;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faq_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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





        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // If the nav drawer is open, hide action items related to the content
            if (dlDrawer.isDrawerOpen()) {
                // Uncomment to hide menu items
                // menu.findItem(R.id.mi_test).setVisible(false);
            }
            return super.onPrepareOptionsMenu(menu);
        }



        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            dlDrawer.getDrawerToggle().syncState();
        }

//        @Override
//        public void onConfigurationChanged(Configuration newConfig) {
//            super.onConfigurationChanged(newConfig);
//            // Pass any configuration change to the drawer toggles
//            dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
//        }

    }
//   }