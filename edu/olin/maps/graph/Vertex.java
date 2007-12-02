package edu.olin.maps.graph;

import edu.olin.maps.IDGenerator;
import java.util.HashSet;
import java.util.Set;

/**
 * A single Vertex in a graph.  The vertex has a unique ID.
 * @author jstanton
 */
public class Vertex {
    
    //the id for this Vertex
    private int id;

    /**
     * Creates a new Vertex with a unique ID
     */
    public Vertex(){
        id = IDGenerator.nextID();
        }
    
    /**
     * Access this Vertex's ID
     * @return This vertex's unique non-negative identifier
     */
    public int getID(){ return id; }
    
    /**
     * Returns a String representation of this Vertex
     * @return The String "<CODE><B>ClassName</B>(<B>id</B>)</CODE>
     */
    public String toString(){
        return getClass().getSimpleName()+"("+id+")";
        }

    }
