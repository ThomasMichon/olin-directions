package edu.olin.maps.graph;

import edu.olin.maps.IDGenerator;

/**
 * Represents a directed Edge in a Graph<br>
 * Each Edge travels from an origin Vertex to a destination Vertex in the Graph
 * @author jstanton
 */
public class Edge {
    
    private Vertex origin, dest;
    private int id;
    
    /**
     * Creates an Edge with the given parameters
     * @param origin The origin Vertex for this edge
     * @param dest The destination Vertex for this Edge
     */
    public Edge(Vertex origin, Vertex dest){
        if(origin==null){ throw new IllegalArgumentException("Origin vertex cannot be null"); }
        if(dest==null){ throw new IllegalArgumentException("Dest vertex cannot be null"); }
        this.origin = origin;
        this.dest = dest;
        this.id = IDGenerator.nextID();
        }
    
    /**
     * Create the reverse edge
     * @return A new Edge with origin/dest swapped
     */
    public Edge reverse(){
        return new Edge(dest,origin);
        }
    
    /**
     * Returns the weight for this Edge.  This always returns 1 in a simple Edge
     * @return The edge weight, or 1.0 if this is a simple Edge
     */
    public double getWeight(){ return 1; } //an unweighted edge is just one edge
    
    /**
     * Access this <CODE>Edge</CODE>'s <CODE>id</CODE>
     * @return The unique <CODE>id</CODE> associated with this Edge
     */
    public int getID(){ return id; }
    
    /**
     * Access this <CODE>Edge</CODE>'s origin <CODE>Vertex</CODE>
     * @return This <CODE>Edge</CODE>'s origin <CODE>Vertex</CODE>
     */
    public Vertex getOrigin(){ return origin; }
    /**
     * Access this <CODE>Edge</CODE>'s destination <CODE>Vertex</CODE>
     * @return This <CODE>Edge</CODE>'s destination <CODE>Vertex</CODE>
     */
    public Vertex getDest(){ return dest; }
    
    //can we get from this edge to other through some vertex?
    /**
     * <CODE>Edge e1</CODE> is incident to <CODE>Edge e2</CODE> if and only if <CODE>e1.dest()==e2.origin()</CODE>
     * @param other The Edge to which we are testing incidence
     * @return <CODE>true</CODE> if moving from this Edge to <CODE>other</CODE> through a single Vertex is possible<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean incidentTo(Edge other){
        return other.getOrigin() == this.getDest();
        }
    
    //can we get to this edge from other through some vertex?
    /**
     * <CODE>Edge e1</CODE> is incident from <CODE>Edge e2</CODE> if and only if <CODE>e1.origin()==e2.dest()</CODE>
     * @param other The Edge from which we are testing incidence
     * @return <CODE>true</CODE> if moving from Edge <CODE>other</CODE> to this Edge through a single Vertex is possible<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean incidentFrom(Edge other){
        return other.getDest() == this.getOrigin();
        }
    
    /**
     * Returns a String representation of this Edge
     * @return The String: <code><B>ClassName</B>(<B>id</B>,<B>origin</B>-><B>dest</B>)</code>
     */
    public String toString(){
        return getClass().getSimpleName()+"("+getID()+","+getOrigin().getID()+"->"+getDest().getID()+")";
        }
    
    }
