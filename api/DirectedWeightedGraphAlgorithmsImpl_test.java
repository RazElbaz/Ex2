package api;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DirectedWeightedGraphAlgorithmsImpl_test {

    private static DirectedWeightedGraph graph = new DirectedWeightedGraphImpl();
    private static DirectedWeightedGraphAlgorithms graphAlgorithms = new DirectedWeightedGraphAlgorithmsImpl();

    public DirectedWeightedGraphAlgorithmsImpl_test() {
        for (int i = 0; i < 5; i++) {
            GeoLocation p = new GeoLocationImpl(i, i + 1, i + 2);
            NodeData n = new NodeDataImpl(p, i);
            graph.addNode(n);
        }
        graphAlgorithms.init(graph);
    }


    @Test
    public void isConnected() {
        DirectedWeightedGraph graph1 = graphAlgorithms.copy();
        graph1.connect(0, 1, 1);
        graph1.connect(0, 2, 2);
        graph1.connect(0, 3, 3);
        DirectedWeightedGraphAlgorithms ag0 = new DirectedWeightedGraphAlgorithmsImpl();
        ag0.init(graph1);
        assertFalse(ag0.isConnected());
        graph1.connect(0, 1, 1);
        graph1.connect(1, 2, 2);
        graph1.connect(2, 3, 3);
        graph1.connect(3, 4, 2);
        ag0.init(graph1);
        assertFalse(ag0.isConnected());
        graph1.connect(4,0,5);
        ag0.init(graph1);
        assertTrue(ag0.isConnected());


    }

        @Test
    public void shortestPathDist() {
        DirectedWeightedGraph graph1 = graphAlgorithms.copy();
        graph1.connect(0, 1, 1);
        graph1.connect(0, 4, 1);
        graph1.connect(1, 2, 1);
        graph1.connect(1, 3, 5);
        graph1.connect(2, 3, 2);
        graph1.connect(4, 3, 1);
        graph1.connect(4, 1, 2);
        DirectedWeightedGraphAlgorithms graphA = new DirectedWeightedGraphAlgorithmsImpl();
        graphA.init(graph1);
        double ans = graphA.shortestPathDist(0, 3);
        assertEquals(2, ans);
    }

    @Test
    public void shortestPath() {
        DirectedWeightedGraph graph1 = graphAlgorithms.copy();
        graph1.connect(0, 1, 1);
        graph1.connect(0, 4, 1);
        graph1.connect(1, 2, 1);
        graph1.connect(1, 3, 5);
        graph1.connect(2, 3, 2);
        graph1.connect(4, 3, 1);
        graph1.connect(4, 1, 2);
        DirectedWeightedGraphAlgorithms graphA = new DirectedWeightedGraphAlgorithmsImpl();
        graphA.init(graph1);
        List<NodeData> ans =graphA.shortestPath(0,3);
        List<NodeData> a =new LinkedList<>();
        a.add(graph1.getNode(0));
        a.add(graph1.getNode(4));
        a.add(graph1.getNode(3));
        for (int i=0; i<3; i++){
        assertEquals(a.get(i).getKey(),ans.get(i).getKey());
    }}

    @Test
    public void init() {
        DirectedWeightedGraph g0 = graphAlgorithms.copy();
        DirectedWeightedGraphAlgorithms ag0 = new DirectedWeightedGraphAlgorithmsImpl();
        ag0.init(g0);
        assertEquals(g0, ag0.getGraph());
    }
    @Test
    public void copy() {
        DirectedWeightedGraph d = graphAlgorithms.copy();
        NodeData n = new NodeDataImpl(new GeoLocationImpl(5, 6, 7), 5);
        d.addNode(n);
        assertEquals(5, graph.nodeSize());
        assertEquals(6, d.nodeSize());
    }

    @Test
    public void getGraph() {
        DirectedWeightedGraph g0 =  graphAlgorithms.copy();
        DirectedWeightedGraphAlgorithms ag0 = new DirectedWeightedGraphAlgorithmsImpl();
        ag0.init(g0);
        DirectedWeightedGraph g1 = ag0.getGraph();
        assertEquals(g0, g1);
    }
    @Test
    public void center() {
        DirectedWeightedGraph Y= new DirectedWeightedGraphImpl();
        DirectedWeightedGraphAlgorithms graphA = new DirectedWeightedGraphAlgorithmsImpl();
        graphA.init(Y);
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,4);
        Y.addNode(l);
        T =new GeoLocationImpl(1.1,2.2,0.0);
        l = new NodeDataImpl(T,0);
        Y.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        Y.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        Y.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        Y.addNode(l);
        Y.connect(0, 1, 1);
        Y.connect(1, 2, 12);
        Y.connect(2, 3, 11);
        Y.connect(3, 4, 53);
        assertNull(graphA.center());
        Y.connect(1, 0, 22);
        Y.connect(2, 1, 13);
        Y.connect(3, 2, 22);
        Y.connect(4, 3, 13);
        Y.connect(1, 3, 25);
        Y.connect(1, 4, 14);
        Y.connect(2, 0, 23);
        Y.connect(2, 4, 12);
        Y.connect(3, 0, 24);
        Y.connect(3, 1, 13);
        Y.connect(4, 0, 22);
        Y.connect(4, 1, 13);
        Y.connect(4, 2, 11);
        Y.connect(0, 3, 1);
        Y.connect(0, 2, 1);
        Y.connect(0, 4, 1);
        NodeData t = Y.getNode(0);
        assertEquals(graphA.center().getKey(),t.getKey());
    }

    @Test
    public void testTSP() {
        DirectedWeightedGraph graph1 = new DirectedWeightedGraphImpl();
        GeoLocation t = new GeoLocationImpl(1,1,0);
        NodeData n1 = new NodeDataImpl(t,0);
         t = new GeoLocationImpl(5,1,0);
        NodeData n2 = new NodeDataImpl(t,1);
        t = new GeoLocationImpl(1,5,0);
        NodeData n3 = new NodeDataImpl(t,2);
        t = new GeoLocationImpl(5,5,0);
        NodeData n4 = new NodeDataImpl(t,3);
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addNode(n4);
        graph1.connect(n1.getKey(), n2.getKey(), 1);
        graph1.connect(n1.getKey(), n4.getKey(), 5);
        graph1.connect(n1.getKey(), n3.getKey(), 8);
        graph1.connect(n2.getKey(), n3.getKey(), 5);
        graph1.connect(n2.getKey(), n4.getKey(), 1);
        graph1.connect(n2.getKey(), n1.getKey(), 7);
        graph1.connect(n3.getKey(), n1.getKey(), 4);
        graph1.connect(n3.getKey(), n4.getKey(), 1);
        graph1.connect(n3.getKey(), n2.getKey(), 100);
        graph1.connect(n4.getKey(), n1.getKey(), 100);
        graph1.connect(n4.getKey(), n2.getKey(), 100);
        graph1.connect(n4.getKey(), n3.getKey(), 3);
        DirectedWeightedGraphAlgorithms g_al = new DirectedWeightedGraphAlgorithmsImpl();
        g_al.init(graph1);
        List<NodeData> targets = new ArrayList<NodeData>();
        targets.add(n1);
        targets.add(n4);
        targets.add(n3);
        List<NodeData> expected = new ArrayList<NodeData>();
        expected.add(n3);
        expected.add(n4);
        expected.add(n3);
        expected.add(n1);
        assertEquals(g_al.tsp(targets), expected);
    }
}