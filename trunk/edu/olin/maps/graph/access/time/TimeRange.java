package edu.olin.maps.graph.access.time;

/**
 * Represents a simple range of times
 */
public class TimeRange {
    
    private Time start;
    private Time end;
    
    public TimeRange(Time start, Time end){
        this.start = start;
        this.end = end;
        }
    
    public boolean contains(Time t){
        
        return t.compareTo(start)>=0 && t.compareTo(end)<=0;
        }
    
    public boolean equals(Object other){
        assert other instanceof Time;
        TimeRange tr = (TimeRange) other;
        return tr.start.equals(this.start) && tr.end.equals(this.end);
        }
    
    }
