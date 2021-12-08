package api;
import com.google.gson.*;
import java.io.*;
import java.util.*;

//The variables of the class
public class DirectedWeightedGraphAlgorithmsImpl implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph Graph;

    public DirectedWeightedGraphAlgorithmsImpl() {
        this.Graph = new DirectedWeightedGraphImpl();
    }

    //Inits the graph on which this set of algorithms operates on.
    @Override
    public void init(DirectedWeightedGraph g) {
        this.Graph = g;
    }

    //Returns the underlying graph of which this class works.
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.Graph;
    }

    //Computes a deep copy of this weighted graph.
    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph graph = new DirectedWeightedGraphImpl(this.Graph);
        return graph;
    }


    // Returns true if and only if (iff) there is a valid path from each node to each
    //other node. NOTE: assume directional graph (all n*(n-1) ordered pairs)
    //@return
    @Override
    public boolean isConnected() {
        if (this.getGraph().nodeSize() == 0 || this.getGraph().nodeSize() == 1) {
            return true;
        }
        // the node that we want to check
        NodeData head = null;
        Iterator<NodeData> H = this.getGraph().nodeIter();
        if (H.hasNext()) {
            head = H.next();
        }

        // crate transpose graph
        DirectedWeightedGraph transGraph = Transpose();
        //reset the tag in the original graph
        Iterator<NodeData> nodes_orig = this.getGraph().nodeIter();
        while (nodes_orig.hasNext()) {
            NodeData cnt = nodes_orig.next();
            cnt.setTag(0);
        }
        //reset the tag in the transpose graph
        Iterator<NodeData> nodes_trans = transGraph.nodeIter();
        while (nodes_trans.hasNext()) {
            NodeData cnt2 = nodes_trans.next();
            cnt2.setTag(0);

        }
        // BFS to the original graph
        BFS(head, this.Graph);
        Iterator<NodeData> nodes_bfs_o = this.getGraph().nodeIter();
        while (nodes_bfs_o.hasNext()) {
            NodeData cnt = nodes_bfs_o.next();
            // check  if the node is not visited
            if (cnt.getTag() != 1)
                return false;
        }
        // BFS to the transpose graph
        BFS(head, transGraph);
        Iterator<NodeData> nodes_bfs_t = transGraph.nodeIter();
        while (nodes_bfs_t.hasNext()) {
            NodeData cnt2 = nodes_bfs_t.next();
            // check  if the node is not visited
            if (cnt2.getTag() != 1)
                return false;
        }

        return true;
    }

    // make a transpose graph to check for isConnected function
    private DirectedWeightedGraph Transpose() {
        // crate a new graph
        DirectedWeightedGraph T = new DirectedWeightedGraphImpl();
        // add all the nodes
        Iterator<NodeData> nodes = this.getGraph().nodeIter();
        while (nodes.hasNext()) {
            NodeData q = nodes.next();
            NodeData p = new NodeDataImpl(q.getLocation(), q.getKey());
            T.addNode(p);
        }
        // add all the edges but transpose
        Iterator<EdgeData> e = this.getGraph().edgeIter();
        while (e.hasNext()) {
            EdgeData q = e.next();
            T.connect(q.getDest(), q.getSrc(), q.getWeight());
        }
        return T;
    }


    //A landscape search algorithm is an algorithm used to move on graph nodes for the most part while searching for a node that maintains a strike
    private void BFS(NodeData node, DirectedWeightedGraph g) {
        Queue<NodeData> queue = new LinkedList<>();
        node.setTag(1); //visit
        queue.add(node);
        while (queue.size()!=0) {
            NodeData current = queue.poll();
            // Go through all the neighboring vertexes
            Iterator<EdgeData> ed = this.getGraph().edgeIter(current.getKey());
            while (ed.hasNext()) {
                EdgeData e = ed.next();
                NodeData dest = g.getNode(e.getDest());
                if (dest.getTag() != 1) {
                    dest.setTag(1); // change to visit
                    queue.add(dest);
                }
            }
        }
    }

    // reset all the variables in the graph
    private void resetGraph() {
        Collection<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < this.Graph.nodeSize(); i++) {
            keys.add(this.Graph.getNode(i).getKey());
        }
        for (int node : keys) {
            NodeData n = this.Graph.getNode(node);
            n.setInfo("");
            n.setWeight(Double.MAX_VALUE);
            n.setTag(0);
        }
    }

    //Computes the length of the shortest path between src to dest
    // Note: if no such path --> returns -1
    // @param src - start node
    //@param dest - end (target) node
    // @return
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        resetGraph();
        HashMap<Integer, NodeData> Hash_parent = Algo_Dijkstra(src);
        NodeData cnt = this.Graph.getNode(dest);
        //check if there is no path
        if (!Hash_parent.containsKey(dest)) {
            return -1;
        }
        return cnt.getWeight();
    }

    //Solves the problem of finding the easiest route from point in graph to destination in a weighted graph
    //we used the Algorithm of Dijkstra
    private HashMap<Integer, NodeData> Algo_Dijkstra(int node_id) {
        //we use Lambda function that create weight comparator
        Comparator<NodeData> compare = (NodeData ONE, NodeData TWO) -> Double.compare(ONE.getWeight(), TWO.getWeight());
        PriorityQueue<NodeData> Prior_Queue = new PriorityQueue<NodeData>(this.Graph.nodeSize(), compare);
        //create hashmap ans that we will return
        HashMap<Integer, NodeData> ans = new HashMap<Integer, NodeData>();
        NodeData cnt_node = this.Graph.getNode(node_id);
        cnt_node.setWeight(0);
        Prior_Queue.add(cnt_node);
        while (Prior_Queue.size()!=0) {
            cnt_node = Prior_Queue.poll();
            // change the info to  node visited
            cnt_node.setInfo("visited");
            Iterator<EdgeData> cnt_edge = this.getGraph().edgeIter(cnt_node.getKey());
            //Go through all the neighboring vertexes
            while (cnt_edge.hasNext()) {
                EdgeData ED = cnt_edge.next();
                // one of  node neighbor
                NodeData dest = this.Graph.getNode(ED.getDest());
                // if not visited
                if (!dest.getInfo().equals("visited")) {
                    double weight = this.Graph.getEdge(cnt_node.getKey(), dest.getKey()).getWeight() + cnt_node.getWeight();
                    // the min distance
                    if (weight < dest.getWeight()) {
                        //if weight is the minimum distance set the new distance of dest
                        dest.setWeight(weight);
                        // if it is not the first time
                        if (ans.containsKey(dest.getKey())) {
                            //if it is not the first time replace his src to the shortest path
                            ans.replace(dest.getKey(), cnt_node);
                        } else {
                            //if it is the first time
                            ans.put(dest.getKey(), cnt_node);
                            // add the new node
                            Prior_Queue.add(dest);
                        }}}}}
        return ans;
    }

    //Computes the the shortest path between src to dest - as an ordered List of nodes:
    //src--> n1-->n2-->...dest
    // see: https://en.wikipedia.org/wiki/Shortest_path_problem
    //Note if no such path --> returns null;
    //@param src - start node
    // @param dest - end (target) node
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        resetGraph();
        //create Hashmap with the nodes of the path
        HashMap<Integer, NodeData> Hash_parent = Algo_Dijkstra(src);
        if (Hash_parent.containsKey(dest)) {
            LinkedList<NodeData> ans = new LinkedList<>();
            NodeData cnt = this.Graph.getNode(dest);
            // add the dest
            ans.add(cnt);
            // add all the other nodes in the path
            while (cnt != this.Graph.getNode(src)) {
                if(cnt != null){
                    cnt = Hash_parent.get(cnt.getKey());
                    //add the next nodes to the first place in ans list
                    ans.addFirst(cnt);
                }}
            if (cnt != null)
                return ans;
        }
        return null;//if the HashMap does not contain dest-key there is no path
    }

    //Finds the NodeData which minimizes the max distance to all the other nodes.
    //Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
    // @return the Node data to which the max shortest path to all the other nodes is minimized.
    @Override
    public NodeData center() {
        if (!isConnected()){
            return null;
        }
        resetGraph();
        NodeData ans = new NodeDataImpl();
        double ans_w = Double.MAX_VALUE;
        for (int i = 0; i < this.Graph.nodeSize(); i++) {
            double current = 0;
            for (int j = 0; j < this.Graph.nodeSize(); j++) {
                // for node i calculate all the paths to the other nodes
                double w = shortestPathDist(i, j);
                // sum all the paths
                if (current<w)
                    current=w;
            }
            //the min sum
            if (current < ans_w) {
                ans_w = current;
                ans = this.Graph.getNode(i);
            }
        }
        return ans;
    }

    // Computes a list of consecutive nodes which go over all the nodes in cities.
    //the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
    //the lower the better.
    //See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        for (int i =0; i<cities.size(); i++){
          if(this.Graph.getNode(cities.get(i).getKey())==null){
            return null;
          }
        }
        // if the graph is not connected return null
        if (this.isConnected()) {
            //create list of Nodedata that we will return
            List<NodeData> ans = new ArrayList<NodeData>();
            //create current list of Nodedata
            List<NodeData> current = new ArrayList<NodeData>();
            double cnt_weight;
            int k=0,dest = 0,src;

            // return the center node
            src = FirstTSP(cities);
            while (cities.size()!=0) {
                double min = Double.MAX_VALUE;
                for (int j = 0; j < cities.size(); j++) {
                    if (ans.size()!=0) {
                        //the last node in ans
                        src = ans.get(ans.size()-1).getKey();
                    } else {
                        if (k > 0) {
                            src = cities.get(k).getKey();
                        }}
                    // check the all path between the nods in the graph
                    cnt_weight = shortestPathDist(src, cities.get(j).getKey());
                    // save the node with the lowest path to the other nods
                    if (cnt_weight < min && cnt_weight != 0) {
                        min = cnt_weight;
                        current = shortestPath(src, cities.get(j).getKey());
                        dest = cities.get(j).getKey();
                    }}
                // remove the first src
                if (k == 0) {
                    for (int j = 0; j < cities.size(); j++) {
                        if (cities.get(j).getKey() == src) {
                            cities.remove(j);
                        }}}
                k++;
                // save the path form the cnt_Tsp to ans
                for (int l= 0; l < current.size(); l++) {
                    if (ans.isEmpty() || l != 0) {
                        ans.add(current.get(l));
                    }}
                //remove dest
                for (int j = 0; j < cities.size(); j++) {
                    if (cities.get(j).getKey() == dest) {
                        cities.remove(j);
                    }}}

            return ans;
        }
        return null;
    }

    //function that implement the first iteration of TSP function
    private int FirstTSP(List<NodeData> cities) {
        double cnt_weight,min = Double.MAX_VALUE ;
        int key = 0;
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                // check the all path between the nods in the graph
                cnt_weight = shortestPathDist(cities.get(i).getKey(), cities.get(j).getKey());
                // save the node with the lowest path to the other nods
                if (cnt_weight < min && cnt_weight != 0) {
                    min = cnt_weight;
                    key = cities.get(i).getKey();
                }
            }
        }
        return key;

    }


    //Saves this weighted (directed) graph to the given
    //file name - in JSON format
    //@param file - the file name (may include a relative path).
    //@return true - iff the file was successfully saved
    @Override
    public boolean save(String file) {
        JsonObject EN = new JsonObject();
        JsonArray E = new JsonArray();
        Iterator<EdgeData> ed = this.getGraph().edgeIter();
        //create the first dictionary in the json file that included the edges
        while (ed.hasNext()) {
            EdgeData e = ed.next();
            JsonObject cnt = new JsonObject();
            cnt.addProperty("src",  + e.getSrc());
            cnt.addProperty("w",  + e.getWeight());
            cnt.addProperty("dest",  + e.getDest());
            E.add(cnt);
        }
        JsonArray N = new JsonArray();
        Iterator<NodeData> nodes = this.getGraph().nodeIter();
        //create the second dictionary in the json file that included the nodes
        while (nodes.hasNext()) {
            NodeData n = nodes.next();
            JsonObject cnt = new JsonObject();
            cnt.addProperty("pos", "" + n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
            cnt.addProperty("id",  + n.getKey());
            N.add(cnt);
        }
        EN.add("Edges", E);
        EN.add("Nodes", N);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(EN);
        //The code was taken from Amichai's lecture:
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //This method loads a graph to this graph algorithm.
    //if the file was successfully loaded - the underlying graph
    //of this class will be changed (to the loaded one), in case the
    //graph was not loaded the original graph should remain "as is".
    //@param file - file name of JSON file
    //@return true - iff the graph was successfully loaded.

    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DirectedWeightedGraphImpl.class, new GraphJsonDeserializer());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            this.Graph = gson.fromJson(reader, DirectedWeightedGraphImpl.class);
            System.out.println(this.Graph);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
