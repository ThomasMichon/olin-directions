package edu.olin.maps.graph.weighted.space;

import edu.olin.maps.graph.access.Credentials;
import edu.olin.maps.graph.weighted.space.building.RestrictedEdge;
import java.util.HashSet;
import java.util.Set;

/**
 * Builds a subgraph based on edge restrictions and access credentials
 */
public class SpaceGraphSubgraphBuilder {
    
    /**
     * Takes a SpaceGraph composed of some edges which are restricted-access
     * via a CredentialFilter, and returns a graph without those edges which
     * cannot be accessed via the given Credentials
     * @param g A SpaceGraph to take a subset of
     * @param c The Credentials to apply when taking the subset
     * @return A new SpaceGraph with unaccessable edges removed
     */
    public static SpaceGraph buildSubgraph(SpaceGraph g, Credentials c){
        Set<SpaceVertex> vertices = new HashSet<SpaceVertex>(g.getVertices().size());
        vertices.addAll(g.getVertices());
        Set<SpaceEdge> edges = new HashSet<SpaceEdge>();
        for(SpaceEdge e: g.getEdges()){
            if(!(e instanceof RestrictedEdge)){ //not a restricted edge
                edges.add(e);
                continue;
                }
            RestrictedEdge re = (RestrictedEdge) e;
            if(re.accept(c)){ edges.add(re); }
            }
        return new SpaceGraph(vertices,edges);
        }
    
    }
