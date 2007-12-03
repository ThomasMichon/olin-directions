package edu.olin.maps.graph.weighted.space.meta;

import edu.olin.maps.IDGenerator;
import edu.olin.maps.graph.weighted.space.*;

/**
 * A class representing an edge within an edge graph of a SpaceGraph
 * see SpaceEdgeGraph for more details about an edge graph<br>
 * An edge assocated with two SpaceEdges<br>
 * MetaEdges are immutable
 * @author jstanton
 */
public class MetaEdge {
    
    private SpaceEdge origin = null;
    private SpaceEdge dest = null;
    private Turn turn = null;
    private int id;
    
    /**
     * Creates a new MetaEdge with the given parameters.
     * @param origin The origin SpaceEdge of this edge
     * @param dest The destination SpaceEdge of this edge
     */
    public MetaEdge(SpaceEdge origin, SpaceEdge dest){
        this.id = IDGenerator.nextID();
        this.origin = origin;
        this.dest = dest;
        this.turn = TurnInferer.inferTurn(origin,dest);
        }
    
    /**
     * Returns an edge which is the reverse of this one, i.e. the destination and origin are swapped
     * @return A new edge with origin/dest swapped
     */
    public MetaEdge reverse(){
        return new MetaEdge(getDest(),getOrigin());
        }
    
    /**
     * Returns the turn which was inferred about this MetaEdge
     */
    public Turn getTurn(){ return turn; }
    
    /**
     * Returns the central vertex in this MetaEdge
     * @return The vertex common to both this.origin and this.dest
     */
    public SpaceVertex getCentralVertex(){
        return origin.getDest();
        }
    
    /**
     * Accessor method
     * @return The origin SpaceEdge of this edge
     */
    public SpaceEdge getOrigin(){ return origin; }
    /**
     * Accessor method
     * @return The destination SpaceEdge of this edge
     */
    public SpaceEdge getDest(){ return dest; }
    
    /**
     * Returns a string representation of this WeightedEdge
     * @return The string <CODE>ClassName(id,origin.id->dest.id)</CODE>
     */
    public String toString(){
        return getClass().getSimpleName()+"("+id+","+getOrigin()+"->"+getDest()+","+getTurn()+")";
        }
    
    }
