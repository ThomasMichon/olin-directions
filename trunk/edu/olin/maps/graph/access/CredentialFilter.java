package edu.olin.maps.graph.access;

/**
 * Represents a filter which allows/denies passage to users based on their Credentials object
 */
public interface CredentialFilter {
    
    /**
     * Should the user with the specified Credentials be allowed to pass?
     * @param c The Credentials in question
     * @return true if the user should be accepted; false if the user should be rejected
     */
    public boolean allowPassage(Credentials c);
    
    }
