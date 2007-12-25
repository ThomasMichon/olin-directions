package edu.olin.maps.loader;

import com.jstanton.loader.ThreadedParseWorker;
import edu.olin.maps.graph.weighted.space.SpaceGraph;

public class NavDataLoader {

    public static Object load(){
        String file = "./data/olin_mini.xml";
        NavDataParser p = new NavDataParser();
        Thread t = ThreadedParseWorker.getWorker(p, file);
        t.start();
        try{ t.join();
            }catch(InterruptedException ie){
                ie.printStackTrace();
                return null;
            }
        SpaceGraph g = p.getGraph(); //get the results from the parser
        p.reset();
        return null;
        }

    }