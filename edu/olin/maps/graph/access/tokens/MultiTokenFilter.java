package edu.olin.maps.graph.access.tokens;

import edu.olin.maps.graph.access.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Allows passage if a Credential contains either ALL or at least ONE token
 * @author jstanton
 */
public class MultiTokenFilter implements CredentialFilter {
    
    private Set<Token> tokens = new HashSet<Token>();
    private boolean isAnd = false;
    
    public MultiTokenFilter(Set<Token> tokens, boolean isAnd){
        if(tokens!=null){ this.tokens.addAll(tokens); }
        this.isAnd = isAnd;
        }
    
    public boolean accept(Credentials c) {
        if(c==null||tokens.size()==0){ //no credentials provided
            return tokens.size()==0; //if there are tokens required, return false, otherwise return true
            }
        for(Token t: tokens){
            boolean has = c.hasToken(t);
            if( has^isAnd ){ //clever trick
                //if you don't have it but need it,
                //or if you have one but only need one
                return !isAnd;
                }
            }
        return isAnd; //return the other possibility
        }
    
    }
