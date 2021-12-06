package api;
import java.util.*;

//This interface represents a Directional Weighted Graph,
// As in: http://math.oxford.emory.edu/site/cs171/directedAndEdgeWeightedGraphs/
// The interface has a road-system or communication network in mind -
//and should support a large number of nodes (over 100,000).

//The variables of the class
public class DirectedWeightedGraphImpl implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> Edges;
    private int Node_Size;
    private int Edge_Size;
    private int MC;

    //empty constructor
    public DirectedWeightedGraphImpl() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.Edge_Size = this.Nodes.size();
        this.Node_Size = this.Edges.size();
        this.MC = 0;
    }
    //Deep copy constructor
    public DirectedWeightedGraphImpl(DirectedWeightedGraph graph) {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        c_nodes(graph, this.Nodes);
        c_edges(graph, this.Edges);
        this.Edge_Size = graph.edgeSize();
        this.Node_Size = graph.nodeSize();
    }

    private HashMap<Integer, NodeData> c_nodes(DirectedWeightedGraph graph, HashMap Nodes) {
        HashMap<Integer, NodeData> cnt = Nodes;
        for (int i = 0; i < graph.nodeSize(); i++) {
            this.addNode(graph.getNode(i));
        }
        return cnt;
    }

    private HashMap<Integer, HashMap<Integer, EdgeData>> c_edges(DirectedWeightedGraph graph, HashMap Edges) {
        HashMap<Integer, HashMap<Integer, EdgeData>> cnt = Edges;
        for (int i = 0; i < graph.nodeSize(); i++) {
            this.addNode(graph.getNode(i));
            for (int j = 0; j < graph.edgeSize(); j++) {
                EdgeData edge = graph.getEdge(i, j);
                if (edge != null) {
                    this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
                }
            }
        }
        return cnt;
    }

    //returns the node_data by the node_id,
    // @param key - the node_id
    //@return the node_data by the node_id, null if none.
    @Override
    public NodeData getNode(int key) {
        return Nodes.get(key);
    }

    //returns the data of the edge (src,dest), null if none.
    // Note: this method should run in O(1) time.
    // @param src
    //@param dest
    @Override
    public EdgeData getEdge(int src, int dest) {

        return Edges.get(src).get(dest);
    }

    //adds a new node to the graph with the given node_data.
    // Note: this method should run in O(1) time.
    @Override
    public void addNode(NodeData n) {
        //if there is no key
        if (!this.Nodes.containsKey((n.getKey()))) {
            this.Nodes.put(n.getKey(), n);
            this.Edges.put(n.getKey(), new HashMap<>());
            this.Node_Size++;
            this.MC++;
        }

    }
    //Connects an edge with weight w between node src to node dest.
    // Note: this method should run in O(1) time.
    //@param src - the source of the edge.
    //@param dest - the destination of the edge.
    //@param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
    @Override
    public void connect(int src, int dest, double w) {
        if (w > 0 && Nodes.containsKey(src) && Nodes.containsKey(dest)) {
            if (this.getEdge(src, dest) != null) {
                this.removeEdge(src, dest);
            }
            EdgeData ED = new EdgeDataImpl(src, dest, w);
            this.Edges.get(src).put(dest, ED);
            this.Edge_Size++;
            this.MC++;
        }

  }


    //    This method returns an Iterator for the
    //     collection representing all the nodes in the graph.
    //    Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
    //    @return Iterator<node_data>
    @Override
    public Iterator<NodeData> nodeIter() {
        int cnt_MC = this.MC;
        Collection<NodeData> node = this.Nodes.values();
        if (this.MC!= cnt_MC) {
            throw new RuntimeException();
        }
        return node.iterator();
    }

    //    This method returns an Iterator for all the edges in this graph.
    //    Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
    //    @return Iterator<EdgeData>
    @Override
    public Iterator<EdgeData> edgeIter() {
        int cnt_MC = this.MC;
        Collection<EdgeData> Ed = null;
        for (int i = 0; i < Node_Size; i++) {
            Ed = this.Edges.get(i).values();
        }
        if (this.MC!= cnt_MC)  {
            throw new RuntimeException();
        }
        return Ed.iterator();
    }

    // This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
    // Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
    //  @return Iterator<EdgeData>
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        int cnt_MC = this.MC;
        if (this.MC!= cnt_MC)  {
            throw new RuntimeException();
        }
        return this.Edges.get(node_id).values().iterator();
    }


    //Deletes the node (with the given ID) from the graph -
    // and removes all edges which starts or ends at this node.
    //This method should run in O(k), V.degree=k, as all the edges should be removed.
    //@return the data of the removed node (null if none).
    //@param key
    @Override
    public NodeData removeNode(int key) {
        if (this.Nodes.containsKey(key)) {
            Collection<Integer> edegs = Edges.keySet();
            for (int i = 0; i < edegs.size(); i++) {
                if (Edges.get(i).containsKey(key)) {
                    Edges.get(i).remove(key);
                    Edge_Size--;
                    MC++;
                }
            }
            HashMap h = this.Edges.remove(key);
            //when we remove Node we need to delete all the edges that he connect to them
            this.Edge_Size = this.Edge_Size - h.size();
            // every edge we delete we need to add +1 to MC
            this.MC = this.MC + h.size();
            NodeData nodeRemove = Nodes.remove(key);
            Node_Size--;
            MC++;
            return nodeRemove;
        }
        return null;
    }


    //Deletes the edge from the graph,
    //Note: this method should run in O(1) time.
    //@param src
    //@param dest
    //@return the data of the removed edge (null if none).
    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest)) {
            EdgeData ED = this.Edges.get(src).remove(dest);
            if (ED != null) {
                this.Edge_Size--;
                this.MC++;
            }
            return ED;
        }
        return null;
    }

    //Returns the number of vertices (nodes) in the graph.
    // Note: this method should run in O(1) time.
    @Override
    public int nodeSize() {
        return this.Node_Size;
    }

    //Returns the number of edges (assume directional graph).
    // Note: this method should run in O(1) time.
    @Override
    public int edgeSize() {
        return this.Edge_Size;
    }

    //Returns the Mode Count - for testing changes in the graph.
    @Override
    public int getMC() {
        return this.MC;
    }

}


