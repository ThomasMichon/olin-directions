package edu.olin.maps.graph.access.time;

/**
 * A simple Time consisting of a time of day and a day of week (0-based, 0=Sunday)
 * @author jstanton
 */
public class Time implements Comparable {
    
    private int dow = 0; //0-based day of week, 0 is Sunday => 6 is Saturday
    private int mod = 0; //minutes since midnight
    
    
    private static String dow2str(int dow){
        switch(dow){
            case 0: return "Sun";
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 4: return "Thu";
            case 5: return "Fri";
            case 6: return "Sat";
            default: return "???";
            }
        }
    private static String int2str2(int n){
        return (n<9 ? "0" : "")+n;
        }
    
    public int getMinutes(){ return mod%60; }
    
    public int getHours(){ return mod/60; }
    
    public int getMOD(){ return mod; }
    public int getDOW(){ return dow; }
    
    
    public String toString(){
        return dow2str(dow)+int2str2(getHours())+int2str2(getMinutes());
        }

    public boolean equals(Object o){
        if(o==null||!(o instanceof Time)){ throw new IllegalArgumentException("Cannot compare this to "+o); }
        Time t = (Time) o;
        return this.dow==t.dow && this.mod==t.mod;
        }
    
    public int compareTo(Object o) {
        if(o==null||!(o instanceof Time)){ throw new IllegalArgumentException("Cannot compare this to "+o); }
        Time t = (Time) o;
        if(this.dow < t.dow){ return -1; }
        if(this.dow > t.dow){ return 1; }
        if(this.mod < t.mod){ return -1; }
        if(this.mod > t.mod){ return 1; }
        return 0; //they are the same time
        }
    
    }
