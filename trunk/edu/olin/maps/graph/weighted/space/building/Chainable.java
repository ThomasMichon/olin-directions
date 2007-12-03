package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.Edge;

/**
 * Determine whether an edge can be chained (compacted) with another one
 * @author jstanton
 */
public interface Chainable {
    
    /**
     * Can this edge and the other edge be chained
     * (e.g, going down two segments of the same hallway)
     * to simplify a path
     */
    public boolean chainWith(Edge other);
    
    }
