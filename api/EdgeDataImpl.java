package api;

 // This interface represents the set of operations applicable on a
 // directional edge(src,dest) in a (directional) weighted graph.

//The variables of the class
public class EdgeDataImpl implements EdgeData {
    private int Src;
    private int Dest;
    private double Weight;
    private String Info;
    private int Tag;

    //empty constructor
    public EdgeDataImpl(){
        this.Src=-1;
        this.Dest=-1;
        this.Weight=-1;
        this.Info= null;
        this.Tag=-1;
    }
    //constructor
    public EdgeDataImpl(int src, int dest, double w) {
        this.Src=src;
        this.Dest=dest;
        this.Weight=w;
        this.Info= null;
        this.Tag=-1;
    }

    //The id of the source node of this edge.
    @Override
    public int getSrc() {
        return this.Src;
    }

    //The id of the destination node of this edge
    @Override
    public int getDest() {
        return this.Dest;
    }

    //@return the weight of this edge (positive value).
    @Override
    public double getWeight() {
        return this.Weight;
    }

    //Returns the remark (meta data) associated with this edge.
    @Override
    public String getInfo() {
        return this.Info;
    }

    //Allows changing the remark (meta data) associated with this edge.
    @Override
    public void setInfo(String s) {
        this.Info=s;
    }

    //Temporal data (aka color: e,g, white, gray, black)
    // which can be used be algorithms
    @Override
    public int getTag() {
        return this.Tag;
    }

    //This method allows setting the "tag" value for temporal marking an edge - common
    //practice for marking by algorithms.
    //@param t - the new value of the tag
    @Override
    public void setTag(int t) {
        this.Tag=t;
    }
}

