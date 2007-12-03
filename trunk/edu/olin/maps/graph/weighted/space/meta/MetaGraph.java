package edu.olin.maps.graph.weighted.space.meta;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.Path;
import edu.olin.maps.graph.Vertex;
import edu.olin.maps.graph.weighted.space.SpaceEdge;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A weighted undirected multi-graph witch vertices which are points in 3-space.<br>
 * A SpaceGraph contains a set of SpaceEdgees and a set of MetaEdges.<br>
 * This class exists to create a graph with forced spatial components.
 * @author jstanton
 */
public class MetaGraph {
    
    /**
     * The set of {@link SpaceEdge}es contained in this graph
     */
    protected Set<SpaceEdge> vertices = null; //the set of all vertices in this graph
    /**
     * The set of {@link MetaEdge}s contained in this graph
     */
    protected Set<MetaEdge> edges = null; //the set of all edges in this graph
    
    /**
     * Creates an empty graph
     */
    public MetaGraph(){ this(new HashSet(), new HashSet()); }
    
    /**
     * Creates a graph with the given set of Vertexes and Edges
     * @param vertices The set of vertices in the graph
     * @param edges The set of edges in the graph.<br>
     * All origin and destination vertices in this set must be in the vertices set
     */
    public MetaGraph(Set<SpaceEdge> vertices, Set<MetaEdge> edges){
        if(vertices==null){ throw new IllegalArgumentException("Vertices cannot be null"); }
        if(edges==null){ throw new IllegalArgumentException("Edges cannot be null"); }
        this.vertices = new HashSet();
        this.edges = new HashSet();
        for(SpaceEdge v: vertices){ addVertex(v); }
        for(MetaEdge e: edges){ addEdge(e); }
        }
    
    /**
     * Adds an Edge e to this graph
     * @param e The <CODE>Edge</CODE> to add to this Graph.<br>
     * <CODE>e</CODE> must not be null, and <CODE>e.getOrigin()</CODE> and <CODE>e.getDest()</CODE> must both be <CODE>Vertex</CODE>es in this Graph.
     * @return <CODE>false</CODE> if <CODE>e</CODE> is already in this graph<br>
     * <CODE>true</CODE> otherwise
     */
    public boolean addEdge(MetaEdge e){
        if(e==null){ throw new IllegalArgumentException("Edges cannot be null"); }
        SpaceEdge origin = e.getOrigin();
        SpaceEdge dest = e.getDest();
        if(!vertices.contains(origin)){
            throw new IllegalArgumentException("Edge origin vertex "+origin+" is not in set of vertices");
            }
        if(!vertices.contains(dest)){
            throw new IllegalArgumentException("Edge destination vertex "+dest+" is not in set of vertices");
            }
        return edges.add(e);
        }
    
    /**
     * Adds a Vertex v to this graph
     * @param v The Vertex to add to this Graph.<br>
     * <CODE>v</CODE> must not be null.
     * @return <CODE>false</CODE> if <CODE>v</CODE> is already in this Graph<br>
     * <CODE>true</CODE> otherwise
     */
    public boolean addVertex(SpaceEdge v){
        if(v==null){
            throw new IllegalArgumentException("Vertices cannot be null");
            }
        return vertices.add(v);
        }
    
    /**
     * Access the set of all the edges in this graph.<br>
     * The set which is returned <B>MUST NOT be modified</B> or this graph will be in an undefined state.
     * @return The Set of all edges in this graph
     */
    public Set<MetaEdge> getEdges(){
        return edges;
        }
    
    /**
     * Access the set of all the vertices in this graph.<br>
     * The set which is returned <B>MUST NOT be modified</B> or this graph will be in an undefined state
     * @return The Set of all vertices in this graph
     */
    public Set<SpaceEdge> getVertices(){
        return vertices;
        }
    
    /**
     * Get a string representation of this graph
     * @return The string (including newlines)
     * <pre>
     * ClassName(numVertices,numEdges):
     *    vertex1
     *    vertex2
     *    ...
     *    vertexV
     *    edge1
     *    edge2
     *    ...
     *    edgeE
     * </pre>
     */
    public String toString(){
        StringBuffer rtn = new StringBuffer();
        rtn.append(getClass().getSimpleName()+"("+vertices.size()+","+edges.size()+"):");
        for(SpaceEdge v: vertices){ rtn.append("\n\t"+v); }
        for(MetaEdge e: edges){ rtn.append("\n\t"+e); }
        return rtn.toString();
        }
    
    /**
     * Builds an adjacency table for this graph.<br>
     * An adjacency table is a Map from a Vertex to a Set of outbound Edges.<br>
     * To move out of a vertex in a graph, select a vertex and use this adjacency table to determine which edges may be followed
     * @return The adjacency Map for this Graph as described above
     */
    public Map<SpaceEdge, Set<MetaEdge>> getAdjacencyMap(){
        Map<SpaceEdge, Set<MetaEdge>> m = new HashMap(vertices.size());
        for(MetaEdge e: edges){
            SpaceEdge o = e.getOrigin();
            Set<MetaEdge> appended = m.containsKey(o) ? m.get(o) : new HashSet();
            appended.add(e);
            m.put(o,appended);
            }
        return m;
        }

    /**
     * Is a given Vertex in this Graph?
     * @param v Any Vertex v (may or may not be in this graph)
     * @return <CODE>true</CODE> if <CODE>v!=null</CODE> and <CODE>v</CODE> is in this graph.<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Vertex v){
        return v!=null && vertices.contains(v);
        }

    /**
     * Is a given Edge in this Graph?<br>
     * Are the endpoints of the edge in the graph, and is the actual edge in the graph?<br>
     * Note that this is object-wise, so an edge with the same endpoints does not count
     * @param e Any Edge e (may or may not be in this Graph)
     * @return <CODE>true</CODE> if <CODE>e!=null</CODE>, the object <CODE>e</CODE> is in this graph, and the two endpoints of <CODE>e</CODE> are in this Graph<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Edge e) {
        return e!=null && edges.contains(e) && contains(e.getOrigin()) && contains(e.getDest());
        }

    /**
     * Is a given Path in this Graph?
     * @param p Any Path p (may or may not be in this Graph)
     * @return <CODE>true</CODE> if all Edges and Vertexes in <CODE>p</CODE> are in this Graph<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Path p) {
        for(Edge e: p.getEdges()){
            if(!contains(e)){ return false; }
            }
        return true;
        }
    
    }
