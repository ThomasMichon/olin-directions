package edu.olin.maps.graph.access.time;

/**
 * Represents a simple range of times
 * Does not work for wraparound times (SAT PM to SUN AM for example)
 * @author jstanton
 */
public class TimeRange {
    
    private Time start;
    private Time end;
    
    public TimeRange(Time start, Time end){
        this.start = start;
        this.end = end;
        }
    
    public boolean contains(Time t){
        if(t==null){ return false; }
        return t.compareTo(start)>=0 && t.compareTo(end)<=0;
        }
    
    public boolean equals(Object other){
        assert other instanceof Time;
        TimeRange tr = (TimeRange) other;
        return tr.start.equals(this.start) && tr.end.equals(this.end);
        }
    
    }
