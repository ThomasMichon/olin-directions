package edu.olin.maps.graph.access;

import edu.olin.maps.graph.access.tokens.Token;
import java.util.HashSet;
import java.util.Set;

/**
 * Allows passage by applying an AND or an OR to multiple filters
 */
public class MultiFilter implements CredentialFilter {
    
    private Set<CredentialFilter> filters = new HashSet<CredentialFilter>();
    private boolean isAnd = false;
    
    public MultiFilter(Set<CredentialFilter> filters, boolean isAnd){
        this.filters.addAll(filters);
        this.isAnd = isAnd;
        }
    
    public boolean accept(Credentials c) {
        if(c==null||filters.size()==0){ //no credentials provided
            return filters.size()==0; //if there are filters required, return false, otherwise return true
            }
        for(CredentialFilter f: filters){
            boolean accepts = f.accept(c);
            if( accepts^isAnd ){
                //if you don't have it but need it,
                //or if you have one but only need one
                return !isAnd;
                }
            }
        return isAnd; //return the other possibility
        }
    
    }
