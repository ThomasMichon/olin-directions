package edu.olin.maps.graph.shortestpath;

import edu.olin.maps.graph.*;
import edu.olin.maps.graph.weighted.space.SpaceEdge;
import edu.olin.maps.graph.weighted.space.SpaceGraph;
import edu.olin.maps.graph.weighted.space.SpaceVertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * An implementation of DijkstraSolver's shortest-path algorithm
 * @author jstanton
 */
public class DijkstraSolver implements SpaceGraphSolver {

    private final Set<SpaceVertex> S = new HashSet<SpaceVertex>(); //set of settled nodes
    private final Map<SpaceVertex,Double> D = new HashMap<SpaceVertex,Double>(); //shortest-distance map
    private final Map<SpaceVertex,SpaceVertex> P = new HashMap<SpaceVertex,SpaceVertex>(); //predecessors map
    private SpaceGraph g = null;
    private final Comparator<SpaceVertex> sdCompar = new Comparator<SpaceVertex>(){
        //custom Comparator for sorting SpaceVertexes in a PriorityQueue
        public int compare(SpaceVertex a, SpaceVertex b){
            int d = Double.compare( getShortestDistance(a) , getShortestDistance(b) );
            if(d!=0){ return d; } //sort by distance first
            return (a.getID()>=b.getID()) ? 1 : -1; //at least we can sort by id
            }
        };
    private final PriorityQueue<SpaceVertex> Q = new PriorityQueue<SpaceVertex>(1,sdCompar); //unsettled nodes, in order of shortest known path first
    private Map<SpaceVertex, Set<SpaceEdge>> adjacencyMap = null;
    private Set<SpaceVertex> vertices = null;
    
    /**
     * Creates a new Dijkstra solver for this graph
     */
    public DijkstraSolver(SpaceGraph g){
        if(g==null){ throw new IllegalArgumentException("g cannot be null!"); }
        this.g = g;
        }
    
    /**
     * Is the given vertex's shortest-path info finalised?
     */
    private boolean isSettled(SpaceVertex v){
        return S.contains(v);
        }
    
    /**
     * What is the shortest path to v that we know so far?
     * This returns -1 if we don't know what the value is yet
     */
    private double getShortestDistance(SpaceVertex v){
        Double d = D.get(v); //this conveniently defaults to null if v is not in the keys
        return (d==null) ? Double.MAX_VALUE : d;
        }
    
    /**
     * Set b as the predecessor for a
     */
    private void setPredecessor(SpaceVertex a, SpaceVertex b){
        P.put(a,b); //update our predecessor list
        //b is the predecessor for a
        }
    
    /**
     * Obtain the predecessor for a
     */
    private SpaceVertex getPredecessor(SpaceVertex a){
        return P.get(a);
        }

    /**
     * Update the length of the shortest path from origin to v
     */
    private void setShortestDistance(SpaceVertex v, double d){
        /* When we change the shortest path for a vertex, we need
         * to remove it from the priority queue and then re-insert
         * it into the PriorityQueue with the new value
         */
        Q.remove(v); //remove this SpaceVertex from Q
        D.put(v,d); //update our map with the new value
        Q.add(v); //put in v with the new value so it gets sorted right
        }
    
    /**
     * Find the unsettled node with shortest known distance<br>
     * remove it and return it
     */
    private SpaceVertex extractMin(){
        return Q.poll(); //get the head of the Queue
        //because we prioritise low-numbered elements in the Queue first
        //we will always get the shortest-to-go unsettled path
        }
    
    /**
     * Nearest-neighbor relaxation
     */
    private void relaxNeighbors(SpaceVertex u){
        Set<SpaceEdge> outbound = adjacencyMap.get(u);
        if(outbound==null){ return; }
        for(SpaceEdge e: outbound){
            SpaceVertex v = e.getDest();
            assert !isSettled(v); //make sure we don't mess with settled Vertexes
            double sd = getShortestDistance(u) + u.getLocation().distanceTo(v.getLocation());
            if(sd < getShortestDistance(v)){ //aha! we have found a new shortest path
                setShortestDistance(v,sd); //marks v as unsettled
                setPredecessor(v, u);
                }
            }
        }

    
    /**
     * Initializes our stuff for the start node
     */
    private void init(SpaceVertex origin){
        adjacencyMap = g.getAdjacencyMap(); //cache the adjacency map
        vertices = g.getVertices();
        D.clear();
        S.clear();
        P.clear();
        Q.clear();
        Q.addAll(vertices);
        setShortestDistance(origin,0); //also marks the origin as settled
        }
    
    /**
     * Given two vertices this goes and finds the shortest edge between them
     */
    private SpaceEdge getShortestEdge(SpaceVertex origin, SpaceVertex dest){
        Set<SpaceEdge> allFrom = adjacencyMap.get(origin);
        SpaceEdge shortest = null;
        for(SpaceEdge e: allFrom){
            if(e==null){ continue; }
            if(e.getDest()!=dest){ continue; }
            if(shortest==null || e.getWeight()<shortest.getWeight() ){
                shortest = e;
                }
            }
        return shortest;
        }
    
    public Path getShortestPath(SpaceVertex origin, SpaceVertex dest){
        init(origin);

        while(!Q.isEmpty()){
            SpaceVertex u = extractMin(); //find & take out the "closest" vertex
            if(u==dest){ break; } //found our destination, we're done
            S.add(u); //move u from Q into S, effectively settling u
            relaxNeighbors(u); //run another round of relaxation
            }

        List<SpaceVertex> pv = buildPredecessorList(dest);
        Collections.reverse(pv);
        
        List<SpaceEdge> pe = new ArrayList<SpaceEdge>(pv.size());
        for(int i=0; i<pv.size()-1; i++){
            pe.add(getShortestEdge(pv.get(i),pv.get(i+1)));
            }
        
        return new Path(pe);
        }
    
    private List<SpaceVertex> buildPredecessorList(SpaceVertex u){
        List<SpaceVertex> rtn = new ArrayList<SpaceVertex>();
        rtn.add(u);
        while((u=getPredecessor(u))!=null){
            rtn.add(u);
            }
        return rtn;
        }
    
    }
