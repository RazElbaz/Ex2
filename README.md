# Ex2
The goal of this program to planning and realizing intentional and weighted graphs in Java
# introduction:
The project is actually the design and implementation of directed and weighted graphs in the JAVA programming language.
First, we had to design and implement four interfaces that were designed to produce a directed graph: class for nodes, class for edges, class for GeoLocationand class for graph.
Second, we implemented an interface of the algorithms of the graph, the breakdown of the function names and explanations will appear below.
Next, we implemented a graphical interface that includes a menu that allows you to load graphs from files, save them, edit them, and run algorithms on them.
We wrote JUnit tests to implement the functions of the graph and the algorithms of the graph, which are designed to test their correctness and performance.
The entire task check will be done through three public static functions defined in the main class: Ex2.java.

## Planning of the main departments:
**EdgeDataImpl**  This class implement EdgeData interface, represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph.

**NodeDataImpl**  This class implement NodeData interface, represents the set of operations applicable on a node in a weighted graph.

**GeoLocationImpl** This class implement GeoLocation interface, represents a geo location <x,y,z>, (aka Point3D data).

**DirectedWeightedGraphImpl**This class implement #DirectedWeightedGraph interface:The interface has a road-system or communication network in mind and should support a large number of nodes (over 100,000).
The DirectedWeightedGraphImpl class represents a Directional Weighted Graph,with effective representation.
**implementaion:**
We implemented the graph for the nodes using the data structure: HashMap with Integer and NodeData parameters. For the Edges we did using the data structure: HashMap for the Edges with Integer parameters and another HashMap with Integer and EdgeData parameters

## algorithems:

**DirectedWeightedGraphAlgorithmsImpl** This class implement DirectedWeightedGraphAlgorithms interface, represents a Directed (positive) Weighted Graph Theory Algorithms.
The functions we implement:

 1. void **init**(DirectedWeightedGraph g)- initialize the graph on which this set of the algorithms.
 2. DirectedWeightedGraph **getGraph**()- returns the graph of this class.
 3. DirectedWeightedGraph  **copy**()- computes a deep copy of this weighted graph.
 4. boolean  **isConnected**()- returns true if and only if there is a valid path from each node to each.
    We have created 2 auxiliary functions that we use for the function isConnected : 
 DirectedWeightedGraph **Transpose**()- make a transpose graph to check for isConnected function.
 **BFS**(NodeData n, DirectedWeightedGraph g)- A search algorithm is an algorithm used to move on graph nodes for the most part while searching for a node that maintains a         strike.
   
 5. double **shortestPathDist**(int src, int dest)- computes the length of the shortest path between src to dest, if there is no path we return -1.
    We have created auxiliary function that we use for the function:
    **Dijkstra**(int key)- solves the problem of finding the easiest route from point in graph to destination in a weighted graph.

 6. List<NodeData> **shortestPath**(int src, int dest)-return list of shortest path between src to dest, , if there is no path we return null.
 7. NodeData **center**()- finds the NodeData which minimizes the max distance to all the other nodes.
 
 8. List<NodeData> **tsp**(List<NodeData> cities)- return a list of consecutive nodes which go over all the nodes in cities.
 9. **save**(file)- saves this weighted (directed) graph to the given, the file is JSON format.
 10. **load**(file)- loads a graph to this graph algorithm, the file is JSON format.
 

## Graphical Interface:
 GUI: A graphical interface that includes a menu that allows you to load graphs from files, save them, edit them and run algorithms on them.

## GUI:
Our class GUI
Implements the algorithms we have created and turns them into a graph.
In our graph it is possible to receive and save a Json file, and perform runs of each of the functions that we Implement in the other class.
 Because the graph directs each pink circle basically indicates the dest of each side.
 
## GUI:How to run?
Download all the files in the git to your computer
Open CMD line
And records:
 G.json = The file that we load to the program
java - jar "EX2.JAR location on your computer" "G.json location on your computer"
 
 ## Performence
 We tested the program we built with several different sizes of graphs.
Graph with 1000 vertices: 1.5 seconds, SAVE 2 seconds, CENTER 10 minutes, ISCONNECTED 2 seconds
Graph with 10000 vertices: LOAD 2 seconds, SAVE 3 seconds,CENTER 29 minutes, ISCONNECTED 3 seconds
Graph with 100000 vertices: LOAD 8 seconds, SAVE 17 seconds, ISCONNECTED 9 seconds

