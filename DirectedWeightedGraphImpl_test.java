package api;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DirectedWeightedGraphImpl_test {


    @Test
    public void nodeSize() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.1,2.2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        g.removeNode(5);
        g.removeNode(0);
        g.removeNode(0);
        int s = g.nodeSize();
        assertEquals(1,s);

    }

    @Test
    public void edgeSize() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        g.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        g.addNode(l);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
    }


    @Test
    public void connect() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        g.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        g.addNode(l);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);
        assertNull(g.getEdge(1,0));
        g.removeEdge(2,1);
        g.connect(1,0,1);
        double w = g.getEdge(1,0).getWeight();
        assertEquals(w,1);
    }

    @Test
    public void removeNode() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        g.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        g.addNode(l);
        g.connect(1,0,1);
        g.connect(2,0,5.0);
        g.connect(3,0,3);
        g.connect(0,1,1);
        g.connect(0,2,5.0);
        g.connect(0,3,3);
        g.removeNode(4);
        g.removeNode(0);
        assertNull( g.getEdge(1,0));
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
        g.connect(1,2,1);
        g.connect(2,1,5.0);
        g.connect(3,1,3);
         e = g.edgeSize();
        assertEquals(3,e);
    }

    @Test
    public void removeEdge() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        g.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        g.addNode(l);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);
        assertNull(g.getEdge(0,3));
    }

    @Test
    public void addNode() {
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        GeoLocation T =new GeoLocationImpl(1.1,2.2,0.0);
        NodeData l = new NodeDataImpl(T,11);
        g.addNode(l);
        assertEquals(1,g.nodeSize());
        T =new GeoLocationImpl(1.1,2.2,0.0);
        l = new NodeDataImpl(T,0);
        g.addNode(l);
        T =new GeoLocationImpl(1,2,0.0);
        l = new NodeDataImpl(T,1);
        g.addNode(l);
        T =new GeoLocationImpl(1.5,2.5,0.0);
        l = new NodeDataImpl(T,2);
        g.addNode(l);
        T =new GeoLocationImpl(10,5,0.0);
        l = new NodeDataImpl(T,3);
        g.addNode(l);
        assertEquals(5,g.nodeSize());
    }


}