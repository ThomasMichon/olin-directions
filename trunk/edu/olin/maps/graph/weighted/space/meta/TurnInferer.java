package edu.olin.maps.graph.weighted.space.meta;

import edu.olin.maps.graph.weighted.space.meta.MetaEdge;
import edu.olin.maps.graph.weighted.space.SpaceEdge;

/**
 * Infers turn info between two incident SpaceEdges
 */
public class TurnInferer {
    
    //Turn threshold constants
    public static final int THRESH_SHARPTURN = 135;
    public static final int THRESH_TURN = 60;
    public static final int THRESH_BEAR = 15;
    
    private static boolean isAdjacent(SpaceEdge origin, SpaceEdge dest){
        return origin!=null && dest!=null &&
            (origin.getDest()==dest.getOrigin() || dest.getDest()==origin.getOrigin() );
        }
    
    public static double getAngleDegrees(SpaceEdge origin, SpaceEdge dest){
        if(!isAdjacent(origin,dest)){ return Double.NaN; }
        Vector3D a = new Vector3D(origin);
        Vector3D b = new Vector3D(dest);
        double ab = a.mag()*b.mag();
        double cosTheta = a.dot(b)/ab;
        Vector3D crossAB = a.cross(b);
        double sinTheta = crossAB.mag()/ab;
        return -Math.toDegrees(Math.atan2(sinTheta,cosTheta)*Math.signum(crossAB.z)); //returns -180 to 180 degrees
        }
    
    public static Turn inferTurn(MetaEdge e){
        return inferTurn(e.getOrigin(),e.getDest());
        }
    
    public static Turn inferTurn(SpaceEdge origin, SpaceEdge dest){
        if(!isAdjacent(origin,dest)){ return null; }
        double degrees = getAngleDegrees(origin,dest);
        double absdeg = Math.abs(degrees);
        System.err.println("angle is "+degrees);
        int type = -1;
        if(absdeg>=THRESH_SHARPTURN){ type = Turn.TYPE_SHARPTURN;
            }else if(absdeg>=THRESH_TURN){ type = Turn.TYPE_TURN;
            }else if(absdeg>=THRESH_BEAR){ type = Turn.TYPE_BEAR;
            }else{ type = Turn.TYPE_CONTINUE;
            }
        int dir = type==Turn.TYPE_CONTINUE ? Turn.DIR_CENTER :
            degrees < 0 ? Turn.DIR_LEFT : Turn.DIR_RIGHT;
        return new Turn(type,dir);
        }
    
    }
