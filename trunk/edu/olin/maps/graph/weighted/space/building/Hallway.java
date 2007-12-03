package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.access.CredentialFilter;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * An Edge which represents a Hallway.
 * Hallways are chainable with other Hallways
 * You cannot restrict access within a hallway, as it is just an open room
 * @author jstanton
 */
public class Hallway extends RestrictedEdge {
 
    public Hallway(SpaceVertex origin, SpaceVertex dest){
        super(origin,dest,null);
        }
    
    /*
     * Hallways chain only with other Hallways
     */
    public boolean chainWith(Edge other){
        return other!=null && other instanceof Hallway;
        //only chain with other Hallways
        }
    
    }
