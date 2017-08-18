package edu.pdx.final_project.zipbike;

/*

 Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the class in whose objects JSON data is stored

    private double lat - latitude
    private double lon - longitude
    private int numBikes - free bikes available
    private String hubID - hub ID of the station
    private String stnAdd - Address of the station


    Objects of this class are created in Location Activity

    Created By Abhishek Tatke, Rahul Kumar


      */

public class Station extends Object {

    private double lat;
    private double lon;
    private int numBikes;
    private String hubID;
    private String stnAdd;

    public String getStnAdd() {
        return stnAdd;
    }

    //Setters

    public void setStnAdd(String stnAdd) {
        this.stnAdd = stnAdd;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    //Getters

    public int getNumBikes() {
        return numBikes;
    }

    public void setNumBikes(int numBikes) {
        this.numBikes = numBikes;
    }

    public String getHubID() {
        return hubID;
    }

    public void setHubID(String hubID) {
        this.hubID = hubID;
    }
}

