package edu.olin.maps.graph.access;

import edu.olin.maps.graph.access.time.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a program user's access credentials
 * An access credential is a set of access tokens which a user has and a Time
 * @author jstanton
 */
public class Credentials {
    
    private Set<Token> tokens = new HashSet<Token>();
    private Time time = null;
    
    
    /**
     * Creates a new Credentials with the given time and no accessTokens
     * @param time The current time of access
     */
    public Credentials(Time time){
        this(null,time);
        }
    /**
     * Creates a new Credentials with a set of AccessTokens and the given time
     * @param tokens A Set of AccessTokens that this user has
     * @param time The Time which the user is trying to access the system at
     */
    public Credentials(Set<Token> tokens, Time time){
        this.time = time;
        if(tokens!=null){
            this.tokens.addAll(tokens);
            }
        }
    
    /**
     * What Time is this Credential for?
     * @return The Time
     */
    public Time getTime(){ return time; }
    
    /**
     * Does the user have the specified Token?
     * 
     * @param t An Token
     * @return true if this user has the specified Token; false otherwise
     */
    public boolean hasToken(Token t){
        return tokens.contains(t);
        }
    
    }
