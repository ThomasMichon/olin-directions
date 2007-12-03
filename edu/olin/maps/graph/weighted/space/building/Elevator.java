package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.access.CredentialFilter;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * An Edge which represents an Elevator.
 * Elevators are chainable with other Elevators
 * @author jstanton
 */
public class Elevator extends RestrictedEdge {
 
    public Elevator(SpaceVertex origin, SpaceVertex dest, CredentialFilter filter){
        super(origin,dest,filter);
        }
    
    /*
     * Corridors chain only with other Elevators
     */
    public boolean chainWith(Edge other){
        return other!=null && other instanceof Elevator;
        //only chain with other Elevators
        }
    
    }
