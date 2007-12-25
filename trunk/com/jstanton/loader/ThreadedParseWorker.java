package com.jstanton.loader;

import java.io.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

public class ThreadedParseWorker implements Runnable {
    
    private Parser p = null;
    private String file = null;
    
    private ThreadedParseWorker(Parser p, String file){
        this.p = p;
        this.file = file;
        }
    
    public Parser getParser(){ return p; }
    
    public void run(){
        p.reset();
        SAXParserFactory factory = SAXParserFactory.newInstance(); // Use the default (non-validating) parser
        try{
            SAXParser saxParser = factory.newSAXParser();
            Reader r = new BufferedReader(new FileReader(file));
            InputSource is = new InputSource(r);
            saxParser.parse(is, p);
            while(p.isParsing()){ Thread.yield(); } //wait for parsing to finish
            }catch(Exception e){
                e.printStackTrace();
                System.err.println("Error Loading XML: error in ThreadedParseWorker:\n"+e);
                return;
            }
        }
    
    public static Thread getWorker(Parser p, String file){
        return new Thread(new ThreadedParseWorker(p,file));
        }
    
    }