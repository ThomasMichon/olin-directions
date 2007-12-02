package edu.olin.maps.graph.viz;

import edu.olin.maps.IDGenerator;
import edu.olin.maps.graph.Edge;
import edu.olin.maps.graph.Path;
import edu.olin.maps.graph.Vertex;
import edu.olin.maps.graph.weighted.space.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Translates a SpaceGraph into a dot file for GraphViz
 */
public class SpaceGraphViz {
    
    /**
     * Gets a DOT file representation of the graph g
     * @param g A SpaceGraph
     * @return A DOT representation of the Graph
     */
    public static String getDot(SpaceGraph g){
        return getDot(g,"space_graph_"+IDGenerator.nextID());
        }

    /**
     * Take a SpaceGraph and a title and return GraphVIZ DOT language for it
     * @param g
     * @param title
     * @return 
     */
    public static String getDot(SpaceGraph g, String title){
        return getDot(g,title,null);
        }
    
    /**
     * Take a SpaceGraph, title and a Path and return GraphVIZ DOT language for it
     * @param g 
     * @param nickname 
     * @return 
     */
    public static String getDot(SpaceGraph g, String nickname, Path p){
        Set<SpaceVertex> vertices = g.getVertices();
        Map<SpaceVertex, Set<SpaceEdge>> adj = g.getAdjacencyMap();
        List<? extends Edge> edges = p.getEdges();
        
        StringBuffer rtn = new StringBuffer();
        String n = "\n"; String nt = "\n\t";
        rtn.append("digraph "+nickname+" {" + nt);
        rtn.append("rankdir=LR;" + nt);
        rtn.append("label=\""+nickname+"\";" + nt);
        rtn.append("labelloc=\"top\";" + nt);
        rtn.append("labelfontsize=40;" + nt);
        rtn.append("node [shape = doublecircle, style = filled, fillcolor = gray80];");
        for(Vertex v: p.getVertices()){
            if(v==p.getOrigin()||v==p.getDest()){ continue; }
            rtn.append(" ");
            rtn.append(v.getID());
            }
        rtn.append(";"+nt);
        if(p.getNumEdges()>0){
            System.err.println("Set start node colors");
            rtn.append("node [shape = doublecircle, fillcolor = springgreen, style = filled ]; "+p.getOrigin().getID()+";"+nt);
            rtn.append("node [shape = doublecircle, fillcolor = tomato, style = filled  ]; "+p.getDest().getID()+";"+nt);
            }
        rtn.append("node [shape = circle, style = filled , fillcolor = transparent ];" + nt);
        
        for(SpaceVertex v: vertices){
            Set<SpaceEdge> outbound = adj.get(v);
            if(outbound==null || outbound.size()==0){
                rtn.append(v.getID()+";" + nt);
                continue;
                }
            for(SpaceEdge e: outbound){
                String color = edges.contains(e) ? "red" : "black";
                rtn.append(e.getOrigin().getID()+" -> "+e.getDest().getID()+"["+"label=\""+e.getWeight()+"\","+"color=\""+color+"\"];" + nt);
                }
            }
        rtn.append("}");
        return rtn.toString();
        }
    
    }
