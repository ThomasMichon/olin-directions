package edu.olin.maps.graph.weighted.space;

import edu.olin.maps.graph.*;
import edu.olin.maps.graph.weighted.WeightedEdge;

/**
 * An edge assocated with two SpaceVertexes<br>
 * A SpaceEdge is directional, moving from origin to dest in 3-space<br>
 * The weighting of a SpaceEdge is the distance in 3-space between its origin and dest vertices<br>
 * SpaceEdges are immutable
 * @author jstanton
 */
public class SpaceEdge extends WeightedEdge {
    
    /**
     * Creates a new SpaceEdge with the given parameters.
     * @param origin The origin vertex of this edge
     * @param dest The destination vertex of this edge
     */
    public SpaceEdge(SpaceVertex origin, SpaceVertex dest){
        super(origin, dest, origin.getLocation().distanceTo(dest.getLocation()) );
        }
    
    /**
     * Returns an edge which is the reverse of this one, i.e. the destination and origin are swapped
     * @return A new edge with origin/dest swapped
     */
    public SpaceEdge reverse(){
        return new SpaceEdge(getDest(),getOrigin());
        }
    
    /**
     * Accessor method
     * @return The origin vertex of this edge
     */
    public SpaceVertex getOrigin(){ return (SpaceVertex) super.getOrigin(); }
    /**
     * Accessor method
     * @return The destination vertex of this edge
     */
    public SpaceVertex getDest(){ return (SpaceVertex) super.getDest(); }
    
    }
