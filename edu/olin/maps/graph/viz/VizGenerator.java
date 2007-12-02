package edu.olin.maps.graph.viz;

import edu.olin.maps.Random;
import edu.olin.maps.graph.weighted.space.SpaceGraph;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Useful for using GraphViz to write out pretty graphs
 */
public class VizGenerator {
        
    private static String randomHexChar(){
        return Integer.toHexString(Random.r.nextInt(16));
        }
    
    /**
     * Generates a random Hex GUID of some default length
     * @return A String
     */
    public static String guid(){ return guid(20); }
    
    /**
     * Generates a Random Hex GUID of the specified length
     * @param length how many chars
     * @return the specified random string
     */
    public static String guid(int length){
        String rtn = "";
        for(int i=0; i<length; i++){ rtn += randomHexChar(); }
        return rtn;
        }
    
    private static void writeString(String dot, String guid){
        try{
            FileWriter f = new FileWriter(guid+".dot",false);
            BufferedWriter b = new BufferedWriter(f);
            b.write(dot);
            b.close();
            f.close();
            }catch(Exception e){}
        }
    
    /**
     * Takes a SpaceGraph, generates a DOT file, writes a png and returns the filename it wrote
     * @param g 
     * @return 
     */
    public static String viz(SpaceGraph g){
        return viz(SpaceGraphViz.getDot(g,"somegraph"));
        }
    
    /**
     * Takes a DOT text, writes a png and returns the filename it wrote
     * @param dot 
     * @return 
     */
    public static String viz(String dot){
        String guid = "mygraphdemo";
        writeString(dot,guid);
        String dotpath = "dot.exe"; //dot.exe must be in your search path
        try{
            Runtime.getRuntime().exec( new String[]{
		dotpath,
		"-Tpng",
		guid+".dot",
		"-o",
		guid+".png"
		}).waitFor( );
            }catch(Exception e){System.err.println(e);}
        return guid+".png";
        }
    
    }
