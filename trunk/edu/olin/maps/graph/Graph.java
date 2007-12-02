package edu.olin.maps.graph;

import java.util.Map;
import java.util.Set;

/**
 * An undirected multigraph consisting of <CODE>Vertex</CODE>es and <CODE>Edge</CODE>s
 * @author jstanton
 */
public interface Graph {
    
    /**
     * Get the Set of Edges contained in this Graph
     * The Set which is returned <B>MUST NOT be modified</B> or the WeightedGraph could enter an inconsistent state
     * @return A Set containing all Edges in this Graph
     */
    public Set<? extends Edge> getEdges();
    
    /**
     * Get the Set of Vertexes contained in this Graph
     * The Set which is returned <B>MUST NOT be modified</B> or the Graph could enter an inconsistent state
     * @return A Set containing all Edges in this Graph
     */
    public Set<? extends Vertex> getVertices();
    
    /**
     * Builds an adjacency table for this Graph.<br>
     * An adjacency table is a Map from a Vertex to a Set of outbound Edges.<br>
     * To move out of a vertex in a graph, select a vertex and use this adjacency table to determine which edges may be followed
     * @return The adjacency Map for this Graph as described above
     */
    public Map<? extends Vertex, ? extends Set<? extends Edge>> getAdjacencyMap();
    
    /**
     * Is a given Vertex in this Graph?
     * @param v Any Vertex v (may or may not be in this graph)
     * @return <CODE>true</CODE> if <CODE>v!=null</CODE> and <CODE>v</CODE> is in this graph.<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Vertex v);

    /**
     * Is a given Edge in this Graph?<br>
     * Are the endpoints of the edge in the graph, and is the actual edge in the graph?<br>
     * Note that this is object-wise, so an edge with the same endpoints does not count
     * @param e Any Edge e (may or may not be in this Graph)
     * @return <CODE>true</CODE> if <CODE>e!=null</CODE>, the object <CODE>e</CODE> is in this graph, and the two endpoints of <CODE>e</CODE> are in this Graph<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Edge e);
    
    /**
     * Is a given Path in this Graph?
     * @param p Any Path p (may or may not be in this Graph)
     * @return <CODE>true</CODE> if all Edges and Vertexes in <CODE>p</CODE> are in this Graph<br>
     * <CODE>false</CODE> otherwise
     */
    public boolean contains(Path p);
    
    }
