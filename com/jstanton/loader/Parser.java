package com.jstanton.loader;

import org.xml.sax.Attributes;

public class Parser extends org.xml.sax.helpers.DefaultHandler implements SimpleParser {

    //vars
    private boolean parsing = false;
    public boolean isParsing(){ return parsing; }//tells us if we are currently parsing something
    
    //override DefaultHandler methods
    //subclasses SHOULD NOT override these
    public void startDocument(){
        parsing = true;
        documentOpened();
        }
    public void endDocument(){
        documentClosed();
        parsing = false;
        }
    public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attributes){
        String name = localName;
        if(name==null||name.length()==0){
            name = qualifiedName;
            }
        elementOpened(name,attributes);
        }
    public void endElement(String namespaceURI, String simpleName, String qualifiedName){
        String name = simpleName;
        if(name==null||name.length()==0){
            name = qualifiedName;
            }
        elementClosed(name);
        }
    public void characters(char buf[], int offset, int len){
        String s = new String(buf, offset, len);
        if(!s.trim().equals("")){ textReady(s); }
        }

    public static void err(Object o){ System.err.println(o); }
    
    public void reset(){ //prepares the parser for use by a SAXParser
        if(parsing){ throw new IllegalStateException("Cannot reset parser: parser is busy"); }
        parsing = false;
        }
    
    //these are event handlers, for a subclass to override
    //subclasses should not override any methods other than these
    //Default GenericParser Methods, override these in your subclass
    private static final boolean noisy = false; //are default methods noisy?
    public void documentOpened(){ if(noisy) err("Document Opened"); } //we have started parsing the document
    public void documentClosed(){ if(noisy) err("Document Closed"); } //we have finished parsing the document
    public void elementOpened(String name, Attributes attributes){ if(noisy) err("Element <"+name+"> Opened"); } //we have opened an element, it might have attributes
    public void elementClosed(String name){ if(noisy) err("Element </"+name+"> Closed"); } //we have closed an element
    public void textReady(String text){ if(noisy) err("Text Ready, "+text.length()+" Chars"); } //we found text in an element

    }
