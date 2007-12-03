package edu.olin.maps.graph.generator;

import edu.olin.maps.*;
import edu.olin.maps.graph.Point3D;
import edu.olin.maps.graph.weighted.space.*;
import java.util.HashSet;
import java.util.Set;

public class RandomGraphGenerator {
    
    private static Point3D randomPoint(int range){
        int x = Random.r.nextInt(range);
        int y = Random.r.nextInt(range);
        return new Point3D(x,y,0);
        }
    
    private static SpaceEdge randomEdge(Set<SpaceVertex> v){
        SpaceVertex[] vertices = v.toArray(new SpaceVertex[]{});
        if(vertices.length<2){
            throw new IllegalArgumentException("Could not avoid creating a loop... terminating...");
            }
        SpaceVertex a = vertices[Random.r.nextInt(vertices.length)];
        SpaceVertex b = a;
        while(b==a){ //pick one which is not the same vertex
            b = vertices[Random.r.nextInt(vertices.length)];
            }
        return new SpaceEdge(a,b);
        }
    
    public static SpaceVertex randomVertex(SpaceGraph g){
        SpaceVertex[] v = g.getVertices().toArray(new SpaceVertex[]{});
        return v[Random.r.nextInt(v.length)];
        }
    
    /**
     * Creates a random SpaceGraph with the given attributes
     * @param v The number of vertices to create
     * @param e The number of edges to create
     */
    public static SpaceGraph generateRandomSpaceGraph(int v, int e){
        Set<SpaceVertex> vertices = new HashSet<SpaceVertex>(v);
        Set<SpaceEdge> edges = new HashSet<SpaceEdge>(e);
        for(int i=0; i<v; i++){
            vertices.add( new SpaceVertex(randomPoint(100)) );
            }
        for(int i=0; i<e; i++){
            SpaceEdge r = randomEdge(vertices);
            edges.add(r);
            }
        return new SpaceGraph(vertices,edges);
        }
    
    }
