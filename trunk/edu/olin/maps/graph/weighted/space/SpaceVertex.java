package edu.olin.maps.graph.weighted.space;

import edu.olin.maps.graph.Point3D;
import edu.olin.maps.graph.*;

/**
 * A Vertex associated with a point in 3D space
 * @author jstanton
 */
public class SpaceVertex extends Vertex {
    
    private Point3D location = null;

    /**
     * Creates a new SpaceVertex with the given attributes
     * @param location A Point3D describing where this vertex exists in 3-space
     */
    public SpaceVertex(Point3D location){
        super();
        if(location==null){ throw new IllegalArgumentException("Location must be not null"); }
        this.location = location;
        }
    
    /**
     * Obtain the location of this SpaceVertex in 3-space
     * @return The location of this SpaceVertex in 3-space
     */
    public Point3D getLocation(){ return location; }
    
    /**
     * Obtain a String representation of this SpaceVertex
     * @return The String: <code>ClassName(id)</code>
     */
    public String toString(){
        return getClass().getSimpleName()+"("+getID()+")";
        }

    }
