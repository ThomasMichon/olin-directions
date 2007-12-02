package edu.olin.maps;

/**
 * Useful for creating a unique non-negative integer ID number
 * @author jstanton
 */
public class IDGenerator {
    
    private static int nextID = 0;
    
    /**
     * Get the next available ID
     * @return An integer representing the next-available ID number
     */
    public static int nextID(){
        return nextID++;
        }
    
    }
