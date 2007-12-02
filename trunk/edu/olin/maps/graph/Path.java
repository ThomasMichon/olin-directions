package edu.olin.maps.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * A Path is a chain of <CODE>Edge</CODE>s with an associated total length.<br>
 * Each Path contains N (<CODE>N&gt;0</CODE>) edges v<sub>0</sub>...v<sub>N-1</sub>.  All paths must obey this rule:
 *  <code>v<sub>i</sub></code> must be incident <B>to</B>
 *  <code>v<sub>i+1</sub></code> for all <code>0&lt;=i&lt;N-1</code>
 * @author jstanton
 */
public class Path {
    
    /**
     * the edges of our path, in order
     */
    private List<? extends Edge> edges;
    
    /**
     * the total length of our path (in an unweighted graph, this is the number of edges)
     */
    private double length;
    
    /**
     * Constructs a Path with the given attributes
     * @param edges An ordered List of Edges in the Path.
     * The Path starts at the origin Vertex of the first Edge in the list
     * and ends at the destination Vertex of the last Edge in the List.
     */
    public Path(List<? extends Edge> edges){
        if(edges==null){
            throw new IllegalArgumentException("The list of edges must not be null!");
            }
        if(!isValidPath(edges)){
            throw new IllegalArgumentException("The list of edges specified does not form a single path");
            }
        this.edges = edges;
        this.length = calculatePathLength(edges);
        }
    
    /*
     * @return the length of this path
     */
    /**
     * Calculate the total weight of the path
     * @return The sum of the weights of the Edges in this Path
     */
    public double getTotalWeigth(){
        return length;
        }
    
    /**
     * How many Edges are in this Path?
     * @return The number of Edges visited in this Path.<br>
     * Note that if an Edge is visited twice, then it contributes twice to this value.
     */
    public int getNumEdges(){
        return edges.size();
        }
    
    /**
     * What Edges are visited in this Path?
     * @return The ordered List of Edges in this Path
     */
    public List<? extends Edge> getEdges(){
        return edges;
        }
    
    /**
     * What Vertices are visited in this Path?
     * @return The ordered List of all Edges visited in this Path
     */
    public List<Vertex> getVertices(){
        List<Vertex> rtn = new ArrayList(edges.size()+1);
        if(edges.size()>0){ //add the origin point if it exists
            rtn.add(edges.get(0).getOrigin());
            }
        for(Edge e: edges){ //then add all waypoints and the final destination
            rtn.add(e.getDest());
            }
        return rtn;
        }
    
    /**
     * checks to see if this actually is a path
     * go through each Edge and see if one is connected to the next
     * @param edges 
     * @return 
     */
    private static boolean isValidPath(List<? extends Edge> edges){
        if(edges.size()<=1){ return true; }
        Edge prev = null;
        for(Edge e: edges){
            if(prev!=null && !prev.incidentTo(e)){ return false; }
            prev = e;
            }
        return true;
        }
    
    /**
     * helper method for finding a path length
     * @param edges 
     * @return 
     */
    private static double calculatePathLength(List<? extends Edge> edges){
        double total = 0;
        for(Edge e : edges){
            total += e.getWeight();
            }
        return total;
        }
    
    /**
     * Returns a String representation of this Path
     * @return The String:
     * <pre>
     * ClassName(numEdges,totalWeight):
     *    edge_1
     *    edge_2
     *    ...
     *    edge_N
     * </pre>
     */
    public String toString(){
        StringBuffer rtn = new StringBuffer();
        rtn.append(getClass().getSimpleName()+"("+getNumEdges()+","+getTotalWeigth()+"):");
        for(Edge e: edges){ rtn.append("\n\t"+e); }
        return rtn.toString();
        }
    
    public Vertex getOrigin(){
        if(edges.size()==0){return null;}
        return edges.get(0).getOrigin();
        }
    
    public Vertex getDest(){
        if(edges.size()==0){return null;}
        return edges.get(edges.size()-1).getDest();
        }
    
    }
