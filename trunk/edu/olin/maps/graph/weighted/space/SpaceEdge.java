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
        super(origin, dest, 0 ); //does not account for weighting
        recalculateWeight(); //updates with weighting as distance from origin to dest
        }
    
    /**
     * Returns an edge which is the reverse of this one, i.e. the destination and origin are swapped
     * @return A new edge with origin/dest swapped
     */
    public SpaceEdge reverse(){
        return new SpaceEdge(getDest(),getOrigin());
        }
    
    /**
     * You must invoke this if you altered the location of either this.origin or this.dest
     * So that this SpaceEdge knows its proper weight, rather than an incorrect one
     */
    public void recalculateWeight(){
        setWeight( getOrigin().getLocation().distanceTo(getDest().getLocation()) );
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
