package edu.olin.maps.graph;

import edu.olin.maps.graph.weighted.space.meta.Vector3D;

/**
 * A point in 3D space
 * @author jstanton
 */
public class Point3D {
    
    private double x,y,z;
    
    /**
     * Constructs a new Point3D with the given attributes
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     * @param z The z-coordinate of the point
     */
    public Point3D(double x, double y, double z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        }
    
    /**
     * Get the x-coordinate of this Point3D
     * @return The x-coordinate of this Point3D
     */
    public double getX(){ return x; }
    /**
     * Get the y-coordinate of this Point3D
     * @return Get the y-coordinate of this Point3D
     */
    public double getY(){ return y; }
    /**
     * Get the z-coordinate of this Point3D
     * @return Get the z-coordinate of this Point3D
     */
    public double getZ(){ return z; }
    
    /**
     * Calculate <CODE>other.x - this.x</CODE>
     * @param other The other Point3D
     * @return The difference in x-coordinates
     */
    public double getDeltaX(Point3D other){
        return other.x - this.x;
        }
    
    /**
     * Calculate <CODE>other.y - this.y</CODE>
     * @param other The other Point3D
     * @return The difference in y-coordinates
     */
    public double getDeltaY(Point3D other){
        return other.y - this.y;
        }
    
    /**
     * Calculate <CODE>other.z - this.z</CODE>
     * @param other The other Point3D
     * @return The difference in z-coordinates
     */
    public double getDeltaZ(Point3D other){
        return other.z - this.z;
        }
   
    /**
     * Calculates the distance between to points in 3D space
     * (the length of a line segment between the two points)
     * @param other The other Point3D
     * @return The distance between the two points
     */
    public double distanceTo(Point3D other){
        double dx = getDeltaX(other);
        double dy = getDeltaY(other);
        double dz = getDeltaZ(other);
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
        }
    
    /**
     * Returns a String representation of this Path
     * @return The String "<code>(x,y,z)</code>"
     */
    public String toString(){
        return "("+x+","+y+","+z+")";
        }

    /**
     * Get a Vector3D pointing to another Point3D
     * @return a Vector3D which points from this point to the other point
     */
    public Vector3D vectorTo(Point3D other){
        if(other==null||other==this){ return null; }
        return new Vector3D(this,other);
        }
    
    /**
     * Does what you think it does
     */
    public void setX(double x) {
        this.x = x;
        }

    /**
     * Does what you think it does
     */
    public void setY(double y) {
        this.y = y;
        }

    /**
     * Does what you think it does
     */
    public void setZ(double z) {
        this.z = z;
        }
    
    }
