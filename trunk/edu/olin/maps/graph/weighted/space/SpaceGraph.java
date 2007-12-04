package edu.olin.maps.graph.weighted.space;

import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.Path;
import edu.olin.maps.graph.Vertex;
import edu.olin.maps.graph.weighted.WeightedGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A weighted undirected multi-graph witch vertices which are points in 3-space.<br>
 * A SpaceGraph contains a set of SpaceVertexes and a set of SpaceEdges.<br>
 * This class exists to create a graph with forced spatial components.
 * @author jstanton
 */
public class SpaceGraph implements WeightedGraph {
    
    /**
     * The set of {@link SpaceVertex}es contained in this graph
     */
    protected Set<SpaceVertex> vertices = null; //the set of all vertices in this graph
    /**
     * The set of {@link SpaceEdge}s contained in this graph
     */
    protected Set<SpaceEdge> edges = null; //the set of all edges in this graph
    
    /**
     * Creates an empty graph
     */
    public SpaceGraph(){ this(new HashSet(), new HashSet()); }
    
    /**
     * Creates a graph with the given set of Vertexes and Edges
     * @param vertices The set of vertices in the graph
     * @param edges The set of edges in the graph.<br>
     * All origin and destination vertices in this set must be in the vertices set
     */
    public SpaceGraph(Set<SpaceVertex> vertices, Set<SpaceEdge> edges){
        if(vertices==null){ throw new IllegalArgumentException("Vertices cannot be null"); }
        if(edges==null){ throw new IllegalArgumentException("Edges cannot be null"); }
        this.vertices = new HashSet();
        this.edges = new HashSet();
        for(SpaceVertex v: vertices){ addVertex(v); }
        for(SpaceEdge e: edges){ addEdge(e); }
        }
    
    /**
     * Adds an Edge e to this graph
     * @param e The <CODE>Edge</CODE> to add to this Graph.<br>
     * <CODE>e</CODE> must not be null, and <CODE>e.getOrigin()</CODE> and <CODE>e.getDest()</CODE> must both be <CODE>Vertex</CODE>es in this Graph.
     * @return <CODE>false</CODE> if <CODE>e</CODE> is already in this graph<br>
     * <CODE>true</CODE> otherwise
     */
    public boolean addEdge(SpaceEdge e){
        if(e==null){ throw new IllegalArgumentException("Edges cannot be null"); }
        Vertex origin = e.getOrigin();
        Vertex dest = e.getDest();
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
    public boolean addVertex(SpaceVertex v){
        if(v==null){
            throw new IllegalArgumentException("Vertices cannot be null");
            }
        return vertices.add(v);
        }
    
    /**
     * Removes SpaceVertex v from this graph
     * Throws an error if v is null or if v is used by n>0 edges in the graph
     * @return True if the vertex was removed; False otherwise
     */
    public boolean removeVertex(SpaceVertex v){
        if(v==null){
            throw new IllegalArgumentException("Vertices cannot be null");
            }
        for(SpaceEdge e: edges){
            if(v==e.getOrigin()||v==e.getDest()){
                throw new IllegalStateException("This vertex is in use by at least one edge in the graph; remove these edge(s) first");
                }
            }
        return vertices.remove(v);
        }
    
    /**
     * Removes SpaceEdge e from this graph
     * @return True if the edge was removed; False otherwise
     */
    public boolean removeEdge(SpaceEdge e){
        if(e==null){
            throw new IllegalArgumentException("Edges cannot be null");
            }
        return edges.remove(e);
        }
    
    /**
     * Removes SpaceVertex v and all edges which start at or end at e from this graph
     * Throws an error if v is null
     * @return True if the vertex was removed; False otherwise
     */
    public boolean removeVertexAndEdges(SpaceVertex v){
        if(v==null){
            throw new IllegalArgumentException("Vertices cannot be null");
            }
        for(SpaceEdge e: edges){
            if(v==e.getOrigin()||v==e.getDest()){
                removeEdge(e); //remove all incident edges to/from v
                }
            }
        return vertices.remove(v);
        }
    
    /**
     * Access the set of all the edges in this graph.<br>
     * The set which is returned <B>MUST NOT be modified</B> or this graph will be in an undefined state.
     * @return The Set of all edges in this graph
     */
    public Set<SpaceEdge> getEdges(){
        return edges;
        }
    
    /**
     * Access the set of all the vertices in this graph.<br>
     * The set which is returned <B>MUST NOT be modified</B> or this graph will be in an undefined state
     * @return The Set of all vertices in this graph
     */
    public Set<SpaceVertex> getVertices(){
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
        for(Vertex v: vertices){ rtn.append("\n\t"+v); }
        for(Edge e: edges){ rtn.append("\n\t"+e); }
        return rtn.toString();
        }
    
    /**
     * Builds an adjacency table for this graph.<br>
     * An adjacency table is a Map from a Vertex to a Set of outbound Edges.<br>
     * To move out of a vertex in a graph, select a vertex and use this adjacency table to determine which edges may be followed
     * @return The adjacency Map for this Graph as described above
     */
    public Map<SpaceVertex, Set<SpaceEdge>> getAdjacencyMap(){
        Map<SpaceVertex, Set<SpaceEdge>> m = new HashMap(vertices.size());
        for(SpaceEdge e: edges){
            SpaceVertex o = e.getOrigin();
            Set<SpaceEdge> appended = m.containsKey(o) ? m.get(o) : new HashSet();
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
     * Recalculate all edge weights
     * Invoke this after changing the location of a SpaceVertex in this graph
     * After you invoke this you may need to re-calculate shortest paths,
     * because weightings on each edge may have changed (only if you moved a vertex)
     * 
     */
    public void recalculateEdgeWeights(){
        for(SpaceEdge e: edges){
            if(e==null){ continue; }
            e.recalculateWeight();
            }
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
