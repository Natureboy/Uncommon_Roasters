package com.teamcoffee.coffeewizard;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Brendan on 2/27/2015.
 */
public class DatabaseBuilder {

    //First is brew style, second is all the recipes for that style, third is the information in those recipes.


    public static Recipe[] recipes = {
        new Recipe("v60", 200, 12, "high", 140),
        new Recipe("v60", 200, 12, "medium", 120),
        new Recipe("v60", 200, 12, "low", 120)
    };

    public void createQueries(){
        int n = recipes.length;
        String[] queries = new String[n];
        for (int i = 0; i < n; i++){
            String machine = recipes[n].brewer;
            int volume = recipes[n].volume;
            int weight = recipes[n].weight;
            String density = recipes[n].density;
            int time = recipes[n].brewTime;



        }

        String asdf = "asdf";

    }



}
