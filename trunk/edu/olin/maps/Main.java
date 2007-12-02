package edu.olin.maps;

import edu.olin.maps.graph.*;
import edu.olin.maps.graph.generator.*;
import edu.olin.maps.graph.shortestpath.*;
import edu.olin.maps.graph.viz.*;
import edu.olin.maps.graph.weighted.space.*;
import java.awt.Desktop;
import java.net.URI;
import java.util.Map;

/**
 * A sample of what you can do with edu.olin.maps.graph.Graph and related classes
 * @author jstanton
 */
public class Main {
    
    private static void open(String file){
        try{
                Desktop.getDesktop().browse(new URI(file));
            }catch(Exception e){
                System.err.println("Open of "+file+" failed.");
            }
        }
    
    public static void main(String[] args){
        int n = 25; //number of vertices in our fake graph
        Timer.tic();
        SpaceGraph g = RandomGraphGenerator.generateRandomSpaceGraph(n,3*n);
        SpaceVertex a = RandomGraphGenerator.randomVertex(g);
        SpaceVertex b = a;
        while(b==a){ b = RandomGraphGenerator.randomVertex(g); }
        System.err.println("Building random stuff took "+Timer.tic()+" ms");

        System.err.println("Trying to go from "+a+" to "+b);
        Timer.tic(); //reset the timer
        SpaceGraphSolver s = new DijkstraSolver(g);
        Path p = s.getShortestPath(a,b);
        System.err.println("Finding shortest path took "+Timer.tic()+" ms");
        
        //print out our graph using GraphViz to visualise it
        String graphTitle = "shortest_path_from_"+a.getID()+"_to_"+b.getID();
        String f = VizGenerator.viz(SpaceGraphViz.getDot(g,graphTitle,p));
        open(f);
        
        System.exit(0);
        }
    
    public static String map2str(Map m){
        if(m==null){ return "null"; }
        StringBuffer rtn = new StringBuffer();
        rtn.append(m.getClass().getSimpleName()+"("+m.keySet().size()+"):");
        for(Object k: m.keySet()){
            rtn.append("\n\t"+k+" => "+m.get(k));
            }
        return rtn.toString();
        }
    
    }