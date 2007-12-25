package edu.olin.maps.loader;

import com.jstanton.loader.GatheringParser;
import edu.olin.maps.graph.Point3D;
import edu.olin.maps.graph.access.tokens.Token;
import edu.olin.maps.graph.weighted.space.*;
import edu.olin.maps.graph.weighted.space.building.*;
import java.util.*;
import org.xml.sax.*;

/*
 * this is not done yet, still have to add access parsing
 * You get the idea of how it works, though
 * @author jstanton
 * I wouldn't modify this code yet
 */

public class NavDataParser extends GatheringParser {

    private Map<Integer,Token> tokens = new HashMap<Integer,Token>();
    private Map<Integer,Building> buildings = new HashMap<Integer,Building>();
    private Map<Integer,SpaceVertex> vertices = new HashMap<Integer,SpaceVertex>();
    private Map<Integer,SpaceEdge> edges = new HashMap<Integer,SpaceEdge>();
    private SpaceGraph g = new SpaceGraph();
    
    private int indentLevel = 0;
    private int spacesPerIndent = 4;
    private void indent(){ indentLevel++; }
    private void dedent(){ if(indentLevel>0){ indentLevel--; } }
    private void println(String s){
        for(int i=0; i<indentLevel*spacesPerIndent; i++){
            System.out.print(" ");
            }
        System.out.println(s);
        }
    
    //state vars
    private double currentLevel = 0.0;
    private int currentBuilding = 0;
    private SpaceEdge currentEdge = null;
    
    public void elementOpened(String name, Attributes attrs){
        if(name==null){ return; }
        if(name.equals("graph")){
            println("");
            println("Loading graph "+get(attrs,"id")+" (\""+get(attrs,"name")+"\")");
            println("");
            }
        if(name.equals("tokens")){
            tokens.clear();
            println("Creating access tokens...");
            indent();
            }
        if(name.equals("buildings")){
            buildings.clear();
            println("Creating buildings...");
            indent();
            }
        if(name.equals("vertexes")){
            vertices.clear();
            println("Creating vertices...");
            indent();
            }
        if(name.equals("edges")){
            edges.clear();
            println("Creating edges...");
            indent();
            }
        if(name.equals("token")){
            int id = getInt(attrs,"id");
            String nick = get(attrs, "nick");
            tokens.put(id,Token.getAccessToken(id,nick));
            println("New token: "+id+"(\""+nick+"\")");
            }
        if(name.equals("building")){
            int id = getInt(attrs,"id");
            String shortName = get(attrs, "short");
            String longName = get(attrs, "long");
            buildings.put(id,new Building(id,shortName,longName));
            println("New building: "+id+"(\""+longName+"\" aka \""+shortName+"\")");
            }
        if(name.equals("level")){
            currentLevel = getDouble(attrs,"id");
            currentBuilding = getInt(attrs,"building");
            }
        if(name.equals("vertex")){
            int id = getInt(attrs,"id");
            String nick = get(attrs,"nick");
            double x = getDouble(attrs,"x");
            double y = getDouble(attrs,"y");
            double z = getDouble(attrs,"z");
            vertices.put(id, new SpaceVertex(nick,new Point3D(x,y,z)));
            println("New vertex in "+currentBuilding+"/"+currentLevel+": "+id+"(\""+nick+"\") at position "+vertices.get(id).getLocation());
            }
        if(name.equals("edge")){
            //edge id="10" type="hall" origin="8" dest="9" reverse="1" />
            int id = getInt(attrs,"id");
            String type = get(attrs,"type",true);
            int origin = getInt(attrs,"origin");
            int dest = getInt(attrs,"dest");
            boolean reverse = false; //do we add the reverse of the edge too?
            if(get(attrs,"reverse",false)!=null){
                reverse = ( getInt(attrs,"reverse")==1 );
                }
            if(vertices.get(origin)==null){
                throw new IllegalArgumentException("Origin vertex #"+origin+" for edge #"+id+" not in the graph");
                }
            if(vertices.get(dest)==null){
                throw new IllegalArgumentException("Dest vertex #"+dest+" for edge #"+id+" not in the graph");
                }
            SpaceEdge e = buildEdge(vertices.get(origin),vertices.get(dest),type);
            edges.put(id,e);
            println("New edge: "+e);
            if(reverse){
                SpaceEdge rev = e.reverse();
                edges.put(-id,rev);
                indent();
                println("...and the reverse, "+rev);
                dedent();
                }
            
            //vertices.put(id, new SpaceVertex(nick,new Point3D(x,y,z)));
            //println("New vertex in "+currentBuilding+"/"+currentLevel+": "+id+"(\""+nick+"\") at position "+vertices.get(id).getLocation());
            }
        
        }

    
    public SpaceEdge buildEdge(SpaceVertex origin, SpaceVertex dest, String type){
        if(origin==null){
            throw new IllegalArgumentException("Edge origin cannot be null");
            }
        if(dest==null){
            throw new IllegalArgumentException("Edge dest cannot be null");
            }
        if(type==null||type.length()==0){
            throw new IllegalArgumentException("Cannot have a null edge type");
            }
        if(type.equals("door")){
            return new Door(origin,dest,null);
            }
        if(type.equals("elevator")){
            return new Elevator(origin,dest,null);
            }
        if(type.equals("stairs")||type.equals("stair")||type.equals("stairwell")){
            return new Stairs(origin,dest,null);
            }
        if(type.equals("outdoors")||type.equals("outside")){
            return new Outdoors(origin,dest);
            }
        if(type.equals("hallway")||type.equals("hall")||type.equals("corridor")){
            return new Hallway(origin,dest);
            }
        throw new IllegalArgumentException("Unknown edge type: "+type);
        }
    
    
    public void elementClosed(String name){
        if(name.equals("tokens")){
            dedent();
            println("Done creating access tokens");
            println("");
            }
        if(name.equals("buildings")){
            dedent();
            println("Done creating buildings");
            println("");
            }
        if(name.equals("vertexes")){
            dedent();
            println("Done creating vertices");
            println("");
            }
        if(name.equals("edge")){
            currentEdge = null;
            }
        if(name.equals("edges")){
            dedent();
            println("Done creating edges");
            println("");
            }
        }

    public void textReady(String s){
        if(s==null||s.length()==0){ return; }
        System.err.println("internal text, "+s.length()+" characters");
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static String get(Attributes attrs, String name){
        return get(attrs,name,false);
        }
    private static String get(Attributes attrs, String name, boolean failIfNotFound){
        if(attrs==null||name==null){ return null; }
        for(int i=0; i<attrs.getLength(); i++){
            String lname = attrs.getLocalName(i);
            if(lname==null){ lname = attrs.getQName(i); }
            if(lname==null){ continue; }
            if(lname.equals(name)){ return attrs.getValue(i); }
            }
        if(failIfNotFound){
            throw new IllegalArgumentException("Attribute "+name+" not specified in datafile");
            }
        return null;
        }
    
    private static int getInt(Attributes attrs, String name){
        String s = get(attrs,name,true); //fails if we do not find it
        try{
            return Integer.parseInt(s);
            }catch(Exception e){
                throw new NumberFormatException("The value specified for "+name+" is not an integer");
            }
        }
    
    private static double getDouble(Attributes attrs, String name){
        String s = get(attrs,name,true); //fails if we do not find it
        try{
            return Double.parseDouble(s);
            }catch(Exception e){
                throw new NumberFormatException("The value specified for "+name+" is not a double");
            }
        }
    
    private static boolean getBoolean(Attributes attrs, String name){
        String s = get(attrs,name,true); //fails if we do not find it
        try{
            return Boolean.parseBoolean(s);
            }catch(Exception e){
                throw new NumberFormatException("The value specified for "+name+" is not a boolean");
            }
        }
    
    public void reset(){
        super.reset();
        //todo: write this
        }

    public SpaceGraph getGraph() {
        return g;
        }

    }
