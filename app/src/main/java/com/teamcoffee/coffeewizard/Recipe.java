package com.teamcoffee.coffeewizard;

import java.io.Serializable;

/**
 * Created by Brendan on 2/22/2015.
 *
 * This Recipe object stores information about the recipe that a user creates
 * on the dial-in screen.
 *
 */
public class Recipe implements Serializable{

    public int density;
    public int weight;
    public int waterQuantity;
    public int brewTime;
    public String name;


    public Recipe(int density, int weight, int waterQuantity) {
        this.density = density;
        this.weight = weight;
        this.waterQuantity = waterQuantity;
        this.brewTime = calculateBrewTime(density, weight, waterQuantity);
        this.name = "";

    }

    //Returns the brew time in seconds
    //This is currently just a place holder formula.
    private int calculateBrewTime(int density, int weight, int waterQuantity){
        return (density * 5) + (weight * 10) + (waterQuantity * 15);
    }

    //Used to set a name for the recipe if the user wants to make it a favorite
    public void setName(String name){
        this.name = name;
    }

}
