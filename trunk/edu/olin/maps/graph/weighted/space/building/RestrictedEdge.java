package edu.olin.maps.graph.weighted.space.building;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.access.*;
import edu.olin.maps.graph.weighted.space.SpaceEdge;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * Represents an edge which requires access credentials.
 * This is abstract because you should instantiate a subclass of it
 * @author jstanton
 */
public abstract class RestrictedEdge extends SpaceEdge implements CredentialFilter, Chainable {

    protected CredentialFilter filter = null;
    
    public RestrictedEdge(SpaceVertex origin, SpaceVertex dest, CredentialFilter filter){
        super(origin,dest);
        this.filter = filter;
        }
    
    public boolean accept(Credentials c){
        if(filter==null){ return true; } //a null access filter means a permissive edge
        return filter.accept(c);
        }
    
    /**
     * The default answer for a RestrictedEdge is to
     * never chain with another edge
     * This default can be changed in a subclass
     */
    public boolean chainWith(Edge other){
        return false; //no, not yours
        }
    
    }
