package com.teamcoffee.coffeewizard;

/**
 * Created by Brendan on 3/8/2015.
 */
public class TimerEvents {

    public String event;
    public int startTime;
    public String brewer;
    public int volume;
    public String density;

    public TimerEvents(String brewer, int volume, String density, String event, int startTime){
        this.brewer = brewer;
        this.volume = volume;
        this.density = density;
        this.event = event;
        this.startTime = startTime;
    }

}
