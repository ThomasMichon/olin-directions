package com.jstanton.loader;

import org.xml.sax.Attributes;

public interface SimpleParser {
    public void documentOpened(); //we have started parsing the document
    public void documentClosed(); //we have finished parsing the document
    public void elementOpened(String name, Attributes attributes); //we have opened an element, it might have attributes
    public void elementClosed(String name); //we have closed an element
    public void textReady(String text); //we found text in an element
    }
