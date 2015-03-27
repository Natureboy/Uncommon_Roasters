package com.teamcoffee.coffeewizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FAQActivity extends ActionBarActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.elv);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        if(expListView!=null) {
           System.out.println("Success");
            expListView.setAdapter(listAdapter);
        }
        else{
            System.out.println("Failure");
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
        getMenuInflater().inflate(R.menu.menu_faq, menu);
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
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pouring Water");
        listDataHeader.add("Grind Quality");
        listDataHeader.add("Grinders");
        listDataHeader.add("Fines and Boulders");
        listDataHeader.add("Water Quality");
        listDataHeader.add("Coffee Density");

        // Adding child data
        List<String> pour = new ArrayList<String>();
        pour.add("The flow rate of the water used to brew coffee is one of many crucial variables" +
                " in pour-over brewing that must be controlled in order to brew a consistent cup." +
                " For the recipes in the Coffee Wizard to be applicable, the user must pour the " +
                "water as restricted and slowly as possible without letting the water stream drip." +
                " This will maximize consistency and let the grind size and coffee density determine" +
                " our overall brew time. Start by pouring the water from the center out to the edge of" +
                " the brewer in quick circles as to saturate all grounds evenly. Remember, we want a " +
                "restricted flow, but quick circles. ");
        pour.add("Coffee can be brewed in many different ways, but we chose to utilize a slow pouring method"+
                "because it supplies the most consistent results. Pouring with high velocities will force the coffee to move inside"+
                "the brewer, agitating the coffee grounds, causing some grounds to extract at different rates than others. Secondly,"+
                "the water will drip faster and less consistently when stirred around than it will with a flat and even coffee bed");
        pour.add("Practical Solutions - The best choices for pour-over brewing are gooseneck kettles which provide ample control" +
                " over the water flow. The best results come from filling the kettle only half to three quarters full. " +
                " For additional consistency, you can insert a flow restrictor into the tip of the kettle. ");

        List<String> grindQ = new ArrayList<String>();
        grindQ.add("Apart from great coffee, the grind quality is possibly the most important component to delicious" +
                " coffee. Unfortunately, this happens to be one of the least accessible factors in coffee brewing. " +
                "Nice grinders are expensive...And even the best grinders needs help in achieving the " +
                "optimal grind size. Most of the brew recipes and brewers will use a similar grind " +
                "size. Start by matching the grind size to the picture provided. If your coffee tastes" +
                " sour or brews too quickly, adjust the grind FINER. If the the coffee tastes bitter or" +
                " brews too slowly, grind the coffee COARSER. The grind size for the V60 and Press pot will " +
                "be very similar for our recipes, but the chemex will use a slightly coarser grind. The grounds" +
                " should feel like a medium fine table salt if run between your fingers. The grind size will" +
                " affect how quickly water will flow through the coffee bed. As a rule of thumb, the finer you" +
                " grind, the more flow restriction (the water will flow slower). However, inconsistent grinds " +
                "can flow slow because the fines and boulders are plugging up the holes that would be there if " +
                "the grounds were more even. ");
        List<String> grindr = new ArrayList<String>();
        grindr.add("All grinders will perform differently, even the same models will vary slightly. Successful coffee " +
                "brewing comes from maximizing the uniformity (evenness) of ground coffee particles because the " +
                "particle size determines how quickly the coffee will be extracted.");
        grindr.add("Burr Grinders -  Burr grinders supply the best grind uniformity, however, flat burrs" +
                " tend to do a better job than conical burrs. In addition to the burrs’ teeth shape," +
                " the size of the burrs matter too. We tend to favor larger burrs because they chop " +
                "the coffee a greater number of times before the grounds exit the grinder. This" +
                " means that it is more likely that the grounds will end up being the correct size.");
        grindr.add("Blade Grinders - Blade grinders work, but they aren’t ideal as you can’t control" +
                " the particle size. Try experimenting with pulsing and shaking the grinder so that" +
                " all parts of the beans are chopped.");
        List<String> fAndB = new ArrayList<String>();
        fAndB.add("Fines and boulders are what we call" +
                " the extreme outliers for particles that are too big and small." +
                " These components are inevitable, but we can reduce them by changing " +
                "burrs once worn down or dull. ");
        fAndB.add("Tips - We can get rid of our fines and boulders by using a few simple tricks. Ideally, " +
                "we want a grind size of 400 microns, but this is hardly measurable at home without buying a " +
                "sieve or expensive gear.");
        fAndB.add("One - Grind a little extra coffee and run the grounds through a fine kitchen sifter. " +
                "This will catch the boulders we don’t want. Go ahead and discard them.");
        fAndB.add("Two - Dump grounds onto a paper towel or a damp coffee filter and rub the coffee into the paper. The fines should" +
                " stick the paper towel or filter. Throw away the paper towel when finished.");
        fAndB.add("Your grounds are ready to go and much more consistent than before!");
        List<String> waterQ = new ArrayList<String>();
        waterQ.add("Don't use dirty water you fool");

        List<String> density = new ArrayList<String>();
         density.add("Mass/ volume");
         density.add("this coffee is dense");

        listDataChild.put(listDataHeader.get(0), pour); // Header, Child data
        listDataChild.put(listDataHeader.get(1), waterQ);
        listDataChild.put(listDataHeader.get(2), density);
    }
}