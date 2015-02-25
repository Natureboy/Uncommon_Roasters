package com.teamcoffee.coffeewizard;

import android.provider.BaseColumns;

/**
 * Created by Brendan on 2/25/2015.
 */
public final class DatabaseContract {
    public DatabaseContract(){}

    public static abstract class BrewerTable implements BaseColumns{
        public static final String DATABASE_NAME = "coffeeWizard";
        public static final String TABLE_NAME = "tblBrewers";
        public static final String BREWER_NAME = "brewer_name";
        public static final String BREWER_QUANTITY = "brewer_quantity";
        public static final String BREWER_TIME = "brewer_time";
    }



}
