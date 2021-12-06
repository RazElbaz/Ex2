package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class MyGraph extends JFrame {


    public MyGraph() throws HeadlessException {
        this.add(new GraphP());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
//        this.add(new GraphP());
        this.setVisible(true);
    }

    public  class GraphP extends JPanel {
        LinkedList<Point2D> points=new LinkedList<Point2D>();
        LinkedList<EdgeData> edges=new LinkedList<>();
        DirectedWeightedGraph G=new DirectedWeightedGraphImpl();


        public GraphP(DirectedWeightedGraph graph) {
            this.G=graph;
        }

        public GraphP() {
            DirectedWeightedGraph G=null;
        }


        public void points() {
            while (this.G.nodeIter().hasNext()) {
                NodeData cnt=this.G.nodeIter().next();
                Point2D p = new Point2D.Double(cnt.getLocation().x(),cnt.getLocation().y());
                points.add(p);
                repaint();
            }
        }
        public void edges() {
            while (this.G.edgeIter().hasNext()) {
                EdgeData cnt=this.G.edgeIter().next();
                edges.add(cnt);
                repaint();
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            points();
            edges();

            for (Point2D p:points) {
                g.setColor(new Color(234, 26, 171));
                g.fillOval((int) p.getX() - 10, (int) p.getY() - 10, 20, 20);
            }
            for(EdgeData e:edges) {
                Double dist = points.get(e.getSrc()).distance(points.get(e.getDest()));
                String distS = dist.toString().substring(0,dist.toString().indexOf(".")+2);
                g.drawLine((int)G.getNode(e.getSrc()).getLocation().x(),(int)G.getNode(e.getSrc()).getLocation().y(),(int)G.getNode(e.getDest()).getLocation().x(),(int)G.getNode(e.getDest()).getLocation().y() );
                g.drawString(distS, (int)((G.getNode(e.getSrc()).getLocation().x()+G.getNode(e.getDest()).getLocation().x())/2),(int)((G.getNode(e.getSrc()).getLocation().y()+G.getNode(e.getDest()).getLocation().y())/2));
            }
        }

    }


    public static void main(String[] args) {
        new MyGraph();
    }
}