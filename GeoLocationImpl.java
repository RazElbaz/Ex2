package api;

import org.w3c.dom.Node;
//This interface represents a geo location <x,y,z>, (aka Point3D data).

//The variables of the class
public class GeoLocationImpl implements GeoLocation {
    public double X;
    public double Y;
    public double Z;

    //constructor
    public GeoLocationImpl(double x,double y,double z) {
        this.X=x;
        this.Y=y;
        this.Z=Z;
    }
    //empty constructor
    public GeoLocationImpl() {
        this.X = -1;
        this.Y = -1;
        this.Z = -1;
    }

    //get x
    @Override
    public double x() {
        return this.X;
    }
    //get y
    @Override
    public double y() {
        return this.Y;
    }
    //get z
    @Override
    public double z() {
        return this.Z;
    }

    // find the distance
    @Override
    public double distance(GeoLocation g) {
        double dist= Math.pow(g.x()-this.X,2)+Math.pow(g.y()-this.Y,2) +Math.pow(g.z()-this.Z,2);
        return Math.sqrt(dist);
    }
}
