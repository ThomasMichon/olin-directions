package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.access.CredentialFilter;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * An Edge which represents a Hallway.
 * Stairs are chainable with other Stairs
 * 
 * @author jstanton
 */
public class Stairs extends RestrictedEdge {
 
    public Stairs(SpaceVertex origin, SpaceVertex dest, CredentialFilter filter){
        super(origin,dest,filter);
        }
    
    /*
     * Corridors chain only with other Stairs
     */
    public boolean chainWith(Edge other){
        return other!=null && other instanceof Stairs;
        //only chain with other Stairs
        }
    
    }
