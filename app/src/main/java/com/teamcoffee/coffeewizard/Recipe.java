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

    //Returns the brew time in seconds
    private int calculateBrewTime(int density, int weight, int waterQuantity){
        if(brewer.equals("V60")){
            if(waterQuantity == 200){
                if (density == 0){
                    return 110;
                }
                else if(density == 1){
                    return 120;
                }
                else{
                    return 140;
                }
            }
            else if(waterQuantity == 300){
                if(density == 0){
                    return 155;
                }
                else if(density == 1){
                    return 165;
                }
                else{
                    return 190;
                }
            }
            else if(waterQuantity == 400){
                if(density == 0){
                    return 180;
                }
                else if(density == 1){
                    return 190;
                }
                else{
                    return 225;
                }
            }

        }
        //This probably won't ever be right.
        return (density * 5) + (weight * 10) + (waterQuantity * 15);
    }

    //Used to set a name for the recipe if the user wants to make it a favorite
    public void setName(String name){
        this.name = name;
    }

}
