package com.jstanton.loader;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class GatheringParser extends Parser {
    
    private StringBuffer gatherer = new StringBuffer();
    
    public void startDocument(){
        stopGathering();
        super.startDocument();
        }
    
    public void endDocument(){
        stopGathering();
        super.endDocument();
        }
    
    public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attributes){
        stopGathering();
        super.startElement(namespaceURI,localName,qualifiedName,attributes);
        }
    
    public void endElement(String namespaceURI, String simpleName, String qualifiedName){
        stopGathering();
        super.endElement(namespaceURI,simpleName,qualifiedName);
        }
    
    public void characters(char buf[], int offset, int len){
        if(gatherer==null){ gatherer = new StringBuffer();}
        String s = new String(buf, offset, len);
        if(!s.trim().equals("")){ gatherer = gatherer.append(s); }
        }
    
    private void stopGathering(){
        if(gatherer!=null){
            String s = gatherer.toString();
            gatherer=null;
            if(s!=null){ textReady(s); }
            }
        }
    
    public void reset(){
        super.reset();
        stopGathering();
        }
    
    }
