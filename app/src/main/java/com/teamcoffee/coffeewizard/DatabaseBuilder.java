package com.teamcoffee.coffeewizard;


import android.util.Pair;

/**
 * Created by Brendan on 2/27/2015.
 * This class is currently used to add recipes to the database.
 * Adding a new recipe here will add it to the database ONLY when it is newly created/updated.
 * Make sure to increment the DATABASE_VERSION in DatabaseContract to add any new
 * recipe entries.
 *
 * At the moment this class will only hold information that is used to populate the database
 * upon creation.
 */
public class DatabaseBuilder {

    //Brewer, Volume, Coffee Weight, density, time

    public static Recipe[] recipes = {
        new Recipe("V60", 200, 12, "high", 140),
        new Recipe("V60", 200, 12, "medium", 120),
        new Recipe("V60", 200, 12, "low", 110),
        new Recipe("V60", 300, 19, "high", 190),
        new Recipe("V60", 300, 19, "medium", 165),
        new Recipe("V60", 300, 19, "low", 155),
        new Recipe("V60", 400, 25, "high", 225),
        new Recipe("V60", 400, 25, "medium", 190),
        new Recipe("V60", 400, 25, "low", 180),
        new Recipe("Press Pot", 0, 0, "n/a", 600),
    };

    //Brewer, Volume, Density, Event, Start Time
    public static TimerEvents[] events = {
            new TimerEvents("V60", 300, "high", "Bloom 30g", 0),
            new TimerEvents("V60", 300, "high", "", 10),
            new TimerEvents("V60", 300, "high", "Pour to 150g", 30),
            new TimerEvents("V60", 300, "high", "", 60),
            new TimerEvents("V60", 300, "high", "Pour to 230g", 80),
            new TimerEvents("V60", 300, "high", "", 100),
            new TimerEvents("V60", 300, "high", "Pour to 300g", 120),
            new TimerEvents("V60", 300, "high", "Draining", 135),
            new TimerEvents("V60", 300, "medium", "Bloom 30g", 0),
            new TimerEvents("V60", 300, "medium", "", 10),
            new TimerEvents("V60", 300, "medium", "Pour to 150g", 30),
            new TimerEvents("V60", 300, "medium", "", 60),
            new TimerEvents("V60", 300, "medium", "Pour to 230g", 75),
            new TimerEvents("V60", 300, "medium", "", 95),
            new TimerEvents("V60", 300, "medium", "Pour to 300g", 115),
            new TimerEvents("V60", 300, "medium", "Draining", 130),
            new TimerEvents("V60", 300, "low", "Bloom 30g", 0),
            new TimerEvents("V60", 300, "low", "", 10),
            new TimerEvents("V60", 300, "low", "Pour to 150g", 30),
            new TimerEvents("V60", 300, "low", "", 60),
            new TimerEvents("V60", 300, "low", "Pour to 230g", 75),
            new TimerEvents("V60", 300, "low", "", 95),
            new TimerEvents("V60", 300, "low", "Pour to 300g", 105),
            new TimerEvents("V60", 300, "low", "Draining", 120),

            new TimerEvents("V60", 200, "high", "Bloom 20g", 0),
            new TimerEvents("V60", 200, "high", "", 8),
            new TimerEvents("V60", 200, "high", "Pour to 100g", 20),
            new TimerEvents("V60", 200, "high", "", 40),
            new TimerEvents("V60", 200, "high", "Pour to 150g", 55),
            new TimerEvents("V60", 200, "high", "", 70),
            new TimerEvents("V60", 200, "high", "Pour to 200g", 85),
            new TimerEvents("V60", 200, "high", "Draining", 100),
            new TimerEvents("V60", 200, "medium", "Bloom 20g", 0),
            new TimerEvents("V60", 200, "medium", "", 8),
            new TimerEvents("V60", 200, "medium", "Pour to 100g", 20),
            new TimerEvents("V60", 200, "medium", "", 40),
            new TimerEvents("V60", 200, "medium", "Pour to 150g", 50),
            new TimerEvents("V60", 200, "medium", "", 60),
            new TimerEvents("V60", 200, "medium", "Pour to 200g", 76),
            new TimerEvents("V60", 200, "medium", "Draining", 86),
            new TimerEvents("V60", 200, "low", "Bloom 20g", 0),
            new TimerEvents("V60", 200, "low", "", 8),
            new TimerEvents("V60", 200, "low", "Pour to 100g", 20),
            new TimerEvents("V60", 200, "low", "", 40),
            new TimerEvents("V60", 200, "low", "Pour to 150g", 50),
            new TimerEvents("V60", 200, "low", "", 60),
            new TimerEvents("V60", 200, "low", "Pour to 200g", 70),
            new TimerEvents("V60", 200, "low", "Draining", 84),

            //new TimerEvents("Press Pot", 0, "n/a", "Pour to Xg of Water", 0),
            new TimerEvents("Press Pot", 0, "n/a", "", 30),
            new TimerEvents("Press Pot", 0, "n/a", "Break the Crust", 240),
            new TimerEvents("Press Pot", 0, "n/a", "Clean the Top", 250),
            new TimerEvents("Press Pot", 0, "n/a", "Replace Plunger", 290),
            new TimerEvents("Press Pot", 0, "n/a", "", 300),
            new TimerEvents("Press Pot", 0, "n/a", "Pour coffee (Remember, Do not Plunge)", 585),

            new TimerEvents("V60", 400, "high", "Bloom 40g", 0),
            new TimerEvents("V60", 400, "high", "", 12),
            new TimerEvents("V60", 400, "high", "Pour to 200g", 35),
            new TimerEvents("V60", 400, "high", "", 75),
            new TimerEvents("V60", 400, "high", "Pour to 300g", 90),
            new TimerEvents("V60", 400, "high", "", 115),
            new TimerEvents("V60", 400, "high", "Pour to 400g", 135),
            new TimerEvents("V60", 400, "high", "Draining", 160),
            new TimerEvents("V60", 400, "medium", "Bloom 40g", 0),
            new TimerEvents("V60", 400, "medium", "", 12),
            new TimerEvents("V60", 400, "medium", "Pour to 200g", 35),
            new TimerEvents("V60", 400, "medium", "", 75),
            new TimerEvents("V60", 400, "medium", "Pour to 300g", 90),
            new TimerEvents("V60", 400, "medium", "", 115),
            new TimerEvents("V60", 400, "medium", "Pour to 400g", 135),
            new TimerEvents("V60", 400, "medium", "Draining", 160),
            new TimerEvents("V60", 400, "low", "Bloom 40g", 0),
            new TimerEvents("V60", 400, "low", "", 12),
            new TimerEvents("V60", 400, "low", "Pour to 200g", 35),
            new TimerEvents("V60", 400, "low", "", 75),
            new TimerEvents("V60", 400, "low", "Pour to 300g", 85),
            new TimerEvents("V60", 400, "low", "", 110),
            new TimerEvents("V60", 400, "low", "Pour to 400g", 120),
            new TimerEvents("V60", 400, "low", "Draining", 145),





    };






}
