# Ex2
The goal of this program to planning and realizing intentional and weighted graphs in Java

## Planning of the main departments:
**EdgeDataImpl**  This class implement EdgeData interface, represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph.

**NodeDataImpl**  This class implement NodeData interface, represents the set of operations applicable on a node in a weighted graph.

**GeoLocationImpl** This class implement GeoLocation interface, represents a geo location <x,y,z>, (aka Point3D data).

**DirectedWeightedGraphImpl**This class implement #DirectedWeightedGraph interface:The interface has a road-system or communication network in mind and should support a large number of nodes (over 100,000).
The DirectedWeightedGraphImpl class represents a Directional Weighted Graph,with effective representation.

## algorithems:

**DirectedWeightedGraphAlgorithmsImpl** This class implement DirectedWeightedGraphAlgorithms interface, represents a Directed (positive) Weighted Graph Theory Algorithms.
The functions we implement:
 0. void **init**(DirectedWeightedGraph g)- initialize the graph on which this set of the algorithms.
 1. DirectedWeightedGraph **getGraph**()- returns the graph of this class.
 2. DirectedWeightedGraph  **copy**()- computes a deep copy of this weighted graph.
 3. boolean  **isConnected**()- returns true if and only if there is a valid path from each node to each.
    We have created 2 auxiliary functions that we use for the function isConnected :
   DirectedWeightedGraph **Transpose**()- make a transpose graph to check for isConnected function.
   **BFS**(NodeData n, DirectedWeightedGraph g)- A search algorithm is an algorithm used to move on graph nodes for the most part while searching for a node that maintains a         strike.
   
 4. double **shortestPathDist**(int src, int dest)- computes the length of the shortest path between src to dest, if there is no path we return -1.
    We have created auxiliary function that we use for the function:
    **Dijkstra**(int key)- solves the problem of finding the easiest route from point in graph to destination in a weighted graph.

 5. List<NodeData> **shortestPath**(int src, int dest)-return list of shortest path between src to dest, , if there is no path we return null.
 6. NodeData **center**()- finds the NodeData which minimizes the max distance to all the other nodes.
    
  7.List<NodeData> **tsp**(List<NodeData> cities)- return a list of consecutive nodes which go over all the nodes in cities.

  8.**save**(file)- saves this weighted (directed) graph to the given, the file is JSON format.
   
  9.**load**(file)- loads a graph to this graph algorithm, the file is JSON format.
 
