package edu.olin.maps;

public class Timer {
    
    private static long lastTic = System.currentTimeMillis();
    
    public static long tic(){
        long t = System.currentTimeMillis() - lastTic;
        lastTic = System.currentTimeMillis();
        return t;
        }
    
    }
