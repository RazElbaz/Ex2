package api;

import java.awt.*;

//This interface represents the set of operations applicable on a
//node (vertex) in a (directional) weighted graph.


//The variables of the class
public class NodeDataImpl implements NodeData {
    private int Key;
    private GeoLocation Location;
    private double Weight;
    private String Info;
    private int Tag;


//empty constructor
    public NodeDataImpl(){
        this.Tag=-1;
        this.Key=-1;
        this.Weight=-1;
        this.Info=null;
        this.Location=null;

    }
    //constructor
    public  NodeDataImpl(GeoLocation Location,int Key) {
        this.Key=Key;
        this.Location=Location;
        this.Tag=-1;
        this.Weight=-1;
        this.Info=null;

    }

    //Returns the key (id) associated with this node.
    @Override
    public int getKey() {
        return this.Key;
    }

    // Returns the location of this node, if none return null.
    @Override
    public GeoLocation getLocation() {
        return this.Location;
    }

    //Allows changing this node's location.
    //@param p - new new location  (position) of this node.
    @Override
    public void setLocation(GeoLocation p) {
        this.Location=p;
    }

    //Returns the weight associated with this node.
    @Override
    public double getWeight() {
        return this.Weight;
    }

    //Allows changing this node's weight.
    //@param w - the new weight
    @Override
    public void setWeight(double w) {
        this.Weight=w;
    }

    //Returns the remark (meta data) associated with this node.
    @Override
    public String getInfo() {
        return this.Info;
    }

    // Allows changing the remark (meta data) associated with this node.
    @Override
    public void setInfo(String s) {
        this.Info=s;
    }

    //Temporal data (aka color: e,g, white, gray, black)
    //which can be used be algorithms
    @Override
    public int getTag() {
        return this.Tag;
    }

    // Allows setting the "tag" value for temporal marking an node - common
    // practice for marking by algorithms.
    //@param t - the new value of the tag
    @Override
    public void setTag(int t) {
        this.Tag=t;

    }
}
