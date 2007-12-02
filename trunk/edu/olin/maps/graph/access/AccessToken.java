package edu.olin.maps.graph.access;

import java.util.HashMap;
import java.util.Map;

/**
 * Wheee!
 */

public class AccessToken {
    
    private int id;
    
    private static final Map<Integer,AccessToken> tokens = new HashMap();
    
    public static AccessToken getAccessToken(int id){
        if(tokens.containsKey(id)){
            return tokens.get(id);
            }
        AccessToken t = new AccessToken(id);
        tokens.put(id,t);
        return t;
        }
    
    private AccessToken(int id){
        this.id = id;
        }
    
    public int getID(){
        return id;
        }
    
    public boolean equals(Object other){
        if(other==null || !(other instanceof AccessToken)){
            return false;
            }
        return ((AccessToken) other).id == this.id;
        }
    
    }
