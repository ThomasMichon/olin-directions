package edu.olin.maps;

import edu.olin.maps.graph.*;
import edu.olin.maps.graph.generator.*;
import edu.olin.maps.graph.shortestpath.*;
import edu.olin.maps.graph.weighted.space.*;
import edu.olin.maps.graph.weighted.space.meta.*;
import edu.olin.maps.loader.NavDataLoader;
import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        parseDemo(); //options:  generalDemo(), turnDemo(), metaDemo(), uiDemo(), parseDemo()
        //System.exit(0);
        }
    
    public static void parseDemo(){
        Object o = NavDataLoader.load();
        }
    
    public static void uiDemo(){
        edu.olin.maps.ui.Navigator.main(null);
        }
    
    public static void metaDemo(){
        //demonstration of the Turn class
        SpaceVertex v0 = new SpaceVertex(new Point3D(0,0,0)); //origin
        SpaceVertex vU = new SpaceVertex(new Point3D(0,1,0)); //"up" on the y-axis
        SpaceVertex vR = new SpaceVertex(new Point3D(1,0,0)); //"right" on the x-axis
        SpaceVertex vD = new SpaceVertex(new Point3D(0,-1,0)); //"down" on the y-axis
        SpaceVertex vL = new SpaceVertex(new Point3D(-1,0,0)); //"left" on the x-axis
        
        Set<SpaceVertex> vertices = new HashSet<SpaceVertex>(5);
        vertices.add(v0);
        vertices.add(vU); vertices.add(vD);
        vertices.add(vR); vertices.add(vL);
        
        Set<SpaceEdge> edges = new HashSet<SpaceEdge>(8);
        edges.add(new SpaceEdge(v0,vR));
        edges.add(new SpaceEdge(vR,vD));
        edges.add(new SpaceEdge(vD,vU));
        edges.add(new SpaceEdge(vU,vL));
        edges.add(new SpaceEdge(vL,vD));
        edges.add(new SpaceEdge(vL,v0));
        
        SpaceGraph g = new SpaceGraph(vertices,edges);
        MetaGraph m = MetaGraphBuilder.buildMetaGraph(g);
        
        System.err.println("\n----- -----\nSpace Graph:");
        System.err.println(g);
        System.err.println("\n----- -----\nMeta Graph:");
        System.err.println(m);
        }
    
    public static void turnDemo(){
        //demonstration of the Turn class
        SpaceVertex v0 = new SpaceVertex(new Point3D(0,1,0)); //"up" on the y-axis
        SpaceVertex v1 = new SpaceVertex(new Point3D(0,0,0)); //origin
        SpaceVertex v2 = new SpaceVertex(new Point3D(1,0,0)); //"right" on the x-axis
        SpaceEdge e1 = new SpaceEdge(v0,v1); //moves "down" towards origin
        SpaceEdge e2 = new SpaceEdge(v1,v2); //moves "right" towards v2
        Turn n = TurnInferer.inferTurn(e1,e2); //should be a left turn
        System.err.println(n); //let's see if it is

        Turn t = new Turn(Turn.TYPE_SHARPTURN,Turn.DIR_LEFT);
        System.err.println(t);
        System.err.println(t.reverse());
        }
    
    public static void generalDemo(){
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
        
        /*
        //print out our graph using GraphViz to visualise it
        //uncomment this code blcok if you want a neat GraphVIZ demo
        //You must have GraphVIZ installed on your computer and in your PATH variable
        String graphTitle = "shortest_path_from_"+a.getID()+"_to_"+b.getID();
        String f = VizGenerator.viz(SpaceGraphViz.getDot(g,graphTitle,p));
        open(f);
        */
        
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