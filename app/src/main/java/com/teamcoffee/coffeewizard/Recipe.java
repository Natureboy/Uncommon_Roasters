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

    public String density;
    public int weight;
    public int volume;
    public int brewTime;
    public String brewer;
    public String name;



    public Recipe(String brewer, int volume, int weight, String density, int time) {
        this.density = density;
        this.weight = weight;
        this.volume = volume;
        this.brewer = brewer;
        this.brewTime = time;
        this.name = "";
    }

    //Used to set a name for the recipe if the user wants to make it a favorite
    public void setName(String name){
        this.name = name;
    }

}
