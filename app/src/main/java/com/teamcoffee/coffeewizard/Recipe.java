package com.teamcoffee.coffeewizard;

import java.io.Serializable;

/**
 * Created by Brendan on 2/22/2015.
 *
 */
public class Recipe implements Serializable{

    public int density;
    public int weight;
    public int waterQuantity;


    public Recipe(int density, int weight, int waterQuantity) {
        this.density = density;
        this.weight = weight;
        this.waterQuantity = waterQuantity;
    }



}
