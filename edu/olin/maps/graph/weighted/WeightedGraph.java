package edu.olin.maps.graph.weighted;

import edu.olin.maps.graph.Graph;
import edu.olin.maps.graph.Vertex;
import java.util.Map;
import java.util.Set;

/**
 * A weighted undirected multi-graph consisting of <CODE>Vertex</CODE>es and <CODE>WeightedEdge</CODE>s
 * @author jstanton
 */
public interface WeightedGraph extends Graph {
    
    /**
     * Get the Set of Edges contained in this Graph
     * The Set which is returned <B>MUST NOT be modified</B> or the WeightedGraph could enter an inconsistent state
     * @return A Set containing all Edges in this Graph
     */
    public Set<? extends WeightedEdge> getEdges();
    
    /**
     * Builds an adjacency table for this WeightedGraph.<br>
     * An adjacency table is a Map from a Vertex to a Set of outbound Edges.<br>
     * To move out of a vertex in a graph, select a vertex and use this adjacency table to determine which edges may be followed
     * @return The adjacency Map for this Graph as described above
     */
    public Map<? extends Vertex, ? extends Set<? extends WeightedEdge>> getAdjacencyMap();
    
    }
