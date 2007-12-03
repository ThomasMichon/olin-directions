package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.access.CredentialFilter;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * An Edge which represents an open-air path segment.
 * Paths which are outside cannot be restricted - use a Door to represent a barrier
 * @author jstanton
 */
public class Outdoors extends RestrictedEdge {
    
    public Outdoors(SpaceVertex origin, SpaceVertex dest){
        super(origin,dest,null);
        }
    
    //for chaining, we retain the RestrictedEdge's default to returning false always
    
    }
