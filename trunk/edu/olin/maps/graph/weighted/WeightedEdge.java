package edu.olin.maps.graph.weighted;

import edu.olin.maps.graph.*;

/**
 * An edge assocated with a double-precision non-negative weighting<br>
 * A WeightedEdge is directional, moving from Origin to Dest in 3D space<br>
 * Edges are immutable
 * @author jstanton
 */
public class WeightedEdge extends Edge {
    
    private double weight;
    
    /**
     * Creates a new {@link WeightedEdge} with the specified parameters
     * @param origin The origin {@link Vertex} of this {@link WeightedEdge}
     * @param dest The destination {@link Vertex} of this {@link WeightedEdge}
     * @param weight A non-negative double-precision weight or cost associated with this edge
     */
    public WeightedEdge(Vertex origin, Vertex dest, double weight){
        super(origin,dest);
        this.setWeight(weight);
        }
    
    /**
     * Returns an edge which is the reverse of this one, i.e. the destination and origin are swapped
     * @return A new WeightedEdge with origin/dest swapped, but with the same weight
     */
    public WeightedEdge reverse(){
        return new WeightedEdge(getDest(),getOrigin(),getWeight());
        }
    
    /**
     * Returns the non-negative double-precision weighting associated with this edge
     * @return This edge's weight
     */
    public double getWeight(){ return weight; }
    
    /**
     * Returns a string representation of this WeightedEdge
     * @return The string <CODE>ClassName(id,origin.id->dest.id)</CODE>
     */
    public String toString(){
        return getClass().getSimpleName()+"("+getID()+","+getOrigin().getID()+"->"+getDest().getID()+")";
        }

    /**
     * Does what you think it does
     */
    public void setWeight(double weight) {
        this.weight = weight;
        }
    
    }
