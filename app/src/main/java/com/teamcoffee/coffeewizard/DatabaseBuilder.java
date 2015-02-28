package com.teamcoffee.coffeewizard;


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

    public static Recipe[] recipes = {
        new Recipe("v60", 200, 12, "high", 140),
        new Recipe("v60", 200, 12, "medium", 120),
        new Recipe("v60", 200, 12, "low", 120)
    };




}
