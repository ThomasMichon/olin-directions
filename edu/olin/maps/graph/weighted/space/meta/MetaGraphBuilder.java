package edu.olin.maps.graph.weighted.space.meta;

import edu.olin.maps.graph.weighted.space.SpaceEdge;
import edu.olin.maps.graph.weighted.space.SpaceGraph;
import java.util.HashSet;
import java.util.Set;

/**
 * Factory for building MetaGraphs from SpaceGraphs
 */
public class MetaGraphBuilder {
    
    public static MetaGraph buildMetaGraph(SpaceGraph g){
        if(g==null){ return null; }
        Set<SpaceEdge> vertices = g.getEdges(); //the edges in g are the vertices in the metagraph
        Set<MetaEdge> edges = new HashSet<MetaEdge>(); //empty set of edges in the metagraph to start with
        MetaGraph mg = new MetaGraph(vertices,edges); //make a new MetaGraph with all vertices & no edges
        for(SpaceEdge e: vertices){
            for(SpaceEdge f: vertices){ //for each pair e,f of edges in g
                if(e==f){ continue; } //...where e!=f
                if(!e.incidentTo(f)){ continue; } //...and {e,f} is a 2-edge path in g, A.K.A e.dest()==f.origin()
                mg.addEdge( new MetaEdge(e,f) ); //we make a new edge in the metagraph
                //turn info in the MetaEdge is inferred upon the MetaEdge's creation
                }
            }
        return mg; //build the metagraph
        }
    
    }
