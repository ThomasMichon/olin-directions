package edu.olin.maps.graph.weighted.space.building;

public class Building {
    
    private int id = -1;
    private String shortName = null;
    private String longName = null;
    
    public Building(int id, String shortName, String longName){
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
    
}
