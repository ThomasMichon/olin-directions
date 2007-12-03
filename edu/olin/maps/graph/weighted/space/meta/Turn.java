package edu.olin.maps.graph.weighted.space.meta;

/**
 * A class representing a simple Turn
 * There are various types, and a Turn can be reversed
 * A Turn is typically used in a MetaEdge and MetaEdgeGraph
 * Although you could utilise it in other fashions
 */
public class Turn {
    
    /**
     * these can be anything, we don't care
     * Note that turn types are invariant
     * even if you take the turn in reverse
     */
    public static final int TYPE_SHARPTURN  =  0;
    public static final int TYPE_TURN       =  1;
    public static final int TYPE_BEAR       =  2;
    public static final int TYPE_CONTINUE   =  3;
    
    /**
     * We have to pick these carefully
     * so that reversing directions is like multiplying
     * by -1.  Picking Left=1, right=1, center=0 works
     */
    public static final int DIR_LEFT        = -1;
    public static final int DIR_CENTER      =  0;
    public static final int DIR_RIGHT       =  1;
    
    private int type;
    private int dir;
    
    /**
     * Creates a new Turn with the given type and direction
     */
    public Turn(int type, int dir){
        this.type = type;
        this.dir = dir;
        }
    
    public Turn reverse(){
        return new Turn(type,-dir);
        //-dir only works because of our choice of the dir constants
        //read the comment above the DIR_* comments for further explanation
        }
    
    public boolean equals(Object other){
        //trivial stuff + same type/direction
        return other!=null &&
               other instanceof Turn &&
               ((Turn)other).type == this.type &&
               ((Turn)other).dir == this.dir;
        }
    
    public String typeString(){
        switch(type){
            case TYPE_SHARPTURN:    return "Sharp Turn";
            case TYPE_TURN:         return "Turn";
            case TYPE_BEAR:         return "Bear";
            case TYPE_CONTINUE:     return "Continue";
            default:                return "Unknown Turn Type";
            }
        }
    
    public String dirString(){
        switch(dir){
            case DIR_LEFT:      return "Left";
            case DIR_CENTER:    return "Straight";
            case DIR_RIGHT:     return "Right";
            default:            return "Unknown Direction";
            }
        }
    
    public String toString(){
        return typeString()+" "+dirString();
        }
    
    }
