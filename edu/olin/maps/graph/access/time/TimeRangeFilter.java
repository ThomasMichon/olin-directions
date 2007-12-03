package edu.olin.maps.graph.access.time;

import edu.olin.maps.graph.access.*;
import edu.olin.maps.graph.access.tokens.Token;
import java.util.HashSet;
import java.util.Set;

/**
 * Filters access credentials and allows passage if the
 * credential's time is in a specified time range
 * @author jstanton
 */
public class TimeRangeFilter implements CredentialFilter {
    
    private TimeRange range;
    
    public TimeRangeFilter(TimeRange range){
        this.range = range;
        }
    
    public boolean accept(Credentials c) {
        return range.contains(c.getTime());
        }
    
    }
