package edu.olin.maps.graph.weighted.space.meta;

import edu.olin.maps.graph.Point3D;
import edu.olin.maps.graph.weighted.space.SpaceEdge;
import edu.olin.maps.graph.weighted.space.SpaceVertex;


/**
 * A simple 3D vector class
 * @author jstanton
 */
public class Vector3D {
    
    public static final Vector3D i = new Vector3D(1,0,0);
    public static final Vector3D j = new Vector3D(0,1,0);
    public static final Vector3D k = new Vector3D(0,0,1);

    public double x;
    public double y;
    public double z;

    
    public Vector3D(SpaceEdge e){
        this(e.getOrigin(),e.getDest());
        }
    
    public Vector3D(SpaceVertex origin, SpaceVertex dest){
        this(origin.getLocation(), dest.getLocation());
        }
    
    public Vector3D(Point3D origin, Point3D dest){
        this( origin.getDeltaX(dest), origin.getDeltaY(dest), origin.getDeltaZ(dest) );
        }
    
    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        }
    
    private Vector3D carbonCopy(){ return new Vector3D(x,y,z); }
    
    public Vector3D add(Vector3D v){
        Vector3D nv = carbonCopy();
        nv.x += v.x;
        nv.y += v.y;
        nv.z += v.z;
        return nv;
        }
    
    public Vector3D subtract(Vector3D v){
        Vector3D nv = carbonCopy();
        nv.x -= v.x;
        nv.y -= v.y;
        nv.z -= v.z;
        return nv;
        }
    
    public Vector3D multiply(double scalar){
        Vector3D nv = carbonCopy();
        nv.x *= scalar;
        nv.y *= scalar;
        nv.z *= scalar;
        return nv;
        }
    
    public Vector3D divide(double scalar){
        Vector3D nv = carbonCopy();
        nv.x /= scalar;
        nv.y /= scalar;
        nv.z /= scalar;
        return nv;
        }
    
    public double mag2(){
        return (x*x)+(y*y)+(z*z);
        }
    
    public double mag(){
        return Math.sqrt(mag2());
        }
    
    public Vector3D norm(){
        return divide(mag());
        }
    
    public double dot(Vector3D v){
        return (x*v.x)+(y*v.y)+(z*v.z);
        }
    
    public Vector3D cross(Vector3D v){
        return new Vector3D( (y*v.z - v.y*z), (z*v.x - v.z*x), (x*v.y - v.x*y) );
        }
    
    public String toString(){
        return "("+x+","+y+","+z+")";
        }
    
    }