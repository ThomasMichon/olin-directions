package edu.olin.maps.graph.shortestpath;

import edu.olin.maps.graph.Path;
import edu.olin.maps.graph.weighted.space.SpaceGraph;
import edu.olin.maps.graph.weighted.space.SpaceVertex;

/**
 * An interface for a class which finds the shortest path in a SpaceGraph
 * @author jstanton
 */
public interface SpaceGraphSolver {
    
    Path getShortestPath(SpaceVertex origin, SpaceVertex dest);
    
    }
