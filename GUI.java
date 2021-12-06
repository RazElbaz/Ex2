package  api;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class GUI extends JFrame implements ActionListener, MouseListener {
    private DirectedWeightedGraph Dgraph;
    private DirectedWeightedGraphAlgorithms algorithms = new DirectedWeightedGraphAlgorithmsImpl();
    private int mc;

    public GUI(DirectedWeightedGraph Dg) {
        this.Dgraph = Dg;
        algorithms.init(Dgraph);
        this.mc = Dgraph.getMC();
        initGUI();
    }

    private void initGUI() {
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        menuBar.add(file);
        this.setMenuBar(menuBar);

        MenuItem save = new MenuItem("Save");
        save.addActionListener(this);

        MenuItem load = new MenuItem("Load");
        load.addActionListener(this);

        file.add(save);
        file.add(load);


        Menu algo = new Menu("AlgoOfGraph");
        menuBar.add(algo);

        MenuItem draw = new MenuItem("Draw graph");
        draw.addActionListener(this);

        MenuItem addVertex = new MenuItem("Add Node");
        addVertex.addActionListener(this);

        MenuItem addEdge = new MenuItem("connect");
        addEdge.addActionListener(this);

        MenuItem removeVertex = new MenuItem("Remove Node");
        removeVertex.addActionListener(this);

        MenuItem removeEdge = new MenuItem("Remove Edge");
        removeEdge.addActionListener(this);

        algo.add(draw);
        algo.add(addVertex);
        algo.add(removeVertex);
        algo.add(addEdge);
        algo.add(removeEdge);

        Menu algoFunc = new Menu("AlgoOfGraph function");
        menuBar.add(algoFunc);

        MenuItem isConnect = new MenuItem("Is Connected");
        isConnect.addActionListener(this);

        MenuItem sortPath = new MenuItem("Short-path");
        sortPath.addActionListener(this);

        MenuItem sortPathDist = new MenuItem("Short-path-dist");
        sortPathDist.addActionListener(this);

        MenuItem center = new MenuItem("center");
        center.addActionListener(this);

        MenuItem TSP = new MenuItem("TSP");
        TSP.addActionListener(this);

        algoFunc.add(sortPath);
        algoFunc.add(isConnect);
        algoFunc.add(TSP);
        algoFunc.add(center);
        algoFunc.add(sortPathDist);

        this.addMouseListener(this);
        setVisible(true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (Dgraph.getMC() != mc) {
                            drawGraph();
                            mc = Dgraph.getMC();
                        }
                    }
                }
            }
        });

        t.start();

    }

    public void actionPerformed(ActionEvent e) {
        DirectedWeightedGraphAlgorithms alg = new DirectedWeightedGraphAlgorithmsImpl();
        alg.init(Dgraph);
        String str = e.getActionCommand();

        if (str.equals("Save")) {
            saveGraphGui();
        }

        if (str.equals("Load")) {
            loadGraphGui();
        }

        if (str.equals("Draw graph")) {
            drawGraph();
        }

        if (str.equals("Add Node")) {
            addNodeGUI();
        }

        if (str.equals("connect")) {
            connectedGUI();
        }

        if (str.equals("Remove Node")) {
            removeNodeGUI();
        }

        if (str.equals("Remove Edge")) {
            removeEdgeGUI();
        }

        if (str.equals("Short-path")) {
            shortestPathGUI();
        }

        if (str.equals("Short-path-dist")) {
            shortestPathDistGUI();
        }

        if (str.equals("Is Connected")) {
            isConnectedGUI();
        }

        if (str.equals("center")) {
            CenterGUI();
        }

        if (str.equals("TSP")) {
            TSPGUI();
        }
    }

    // ActionEvent function
    private void saveGraphGui() {
        // try write to the file
        FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
        fd.setFile("*.json");
        fd.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });
        fd.setVisible(true);
        String folder = fd.getDirectory();
        String fileName = fd.getFile();
        if (fileName != null) {
            this.algorithms.save(folder + fileName);
        }
    }

    private void loadGraphGui() {
        // try read from the file
        FileDialog fd = new FileDialog(this, "Load Graph", FileDialog.LOAD);
        fd.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");

            }
        });
        fd.setVisible(true);
        String folder = fd.getDirectory();
        String fileName = fd.getFile();
        if (fileName != null) {
            this.algorithms.load(folder + fileName);
        }
    }

    private void addNodeGUI() {
        final JFrame window = new JFrame("Add Node");
        final JTextField vertexX = new JTextField();
        final JTextField vertexY = new JTextField();
        JLabel verX = new JLabel("       x: ");
        JLabel verY = new JLabel("       y: ");

        JButton enter = new JButton("Enter");

        GridLayout g1 = new GridLayout();
        g1.setRows(3);
        g1.setColumns(2);
        window.setLayout(g1);
        window.add(verX);
        window.add(vertexX);
        window.add(verY);
        window.add(vertexY);
        window.add(enter);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(vertexX.getText());
                    double y = Double.parseDouble(vertexY.getText());
                    GeoLocation p = new GeoLocationImpl(x, y,0.0);
                    Dgraph.addNode(new NodeDataImpl(p,1000));
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void connectedGUI() {
        final JFrame window = new JFrame("Add Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        final JTextField weightText = new JTextField();
        JLabel src = new JLabel(" source key: ");
        JLabel dest = new JLabel(" destination key: ");
        JLabel weight = new JLabel(" weight: ");
        JButton enter = new JButton("Enter");
        GridLayout g1 = new GridLayout();
        g1.setRows(4);
        g1.setColumns(2);
        window.setLayout(g1);
        window.add(src);
        window.add(srcText);
        window.add(dest);
        window.add(destText);
        window.add(weight);
        window.add(weightText);
        window.add(enter);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    double w = Double.parseDouble(weightText.getText());
                    Dgraph.connect(src, dest, w);
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void removeNodeGUI() {
        final JFrame window = new JFrame("remove node");
        final JTextField vertexKey = new JTextField();
        JLabel verKey = new JLabel("    node key: ");

        JButton enter = new JButton("Enter");


        GridLayout g1 = new GridLayout();
        g1.setRows(2);
        g1.setColumns(2);

        window.setLayout(g1);
        window.add(verKey);
        window.add(vertexKey);
        window.add(enter);

        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int key = Integer.parseInt(vertexKey.getText());
                    Dgraph.removeNode(key);
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void removeEdgeGUI() {
        final JFrame window = new JFrame("Remove Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();

        JLabel src = new JLabel("   source key: ");
        JLabel dest = new JLabel("   destination key: ");

        JButton enter = new JButton("Enter");

        GridLayout g1 = new GridLayout();
        g1.setRows(3);
        g1.setColumns(2);

        window.setLayout(g1);
        window.add(src);
        window.add(srcText);
        window.add(dest);
        window.add(destText);
        window.add(enter);

        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    Dgraph.removeEdge(src, dest);
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void isConnectedGUI() {
        String ans = "                "+ algorithms.isConnected();
        final JFrame window = new JFrame("Is Connected");
        JLabel isConnect = new JLabel(ans);
        isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
        JButton close = new JButton("Close");

        GridLayout g1 = new GridLayout();
        g1.setRows(2);

        window.setLayout(g1);
        window.add(isConnect);
        window.add(close);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
            }
        });
    }

    private void shortestPathGUI() {
        final JFrame window = new JFrame("Short-test-Path");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        JLabel srcLabel = new JLabel("  Src: ");
        JLabel destLabel = new JLabel(" Dest: ");
        JButton enter = new JButton("   Enter");

       GridLayout g1 = new GridLayout();
        g1.setColumns(2);
        g1.setRows(3);

        window.setLayout(g1);
        window.add(srcLabel);
        window.add(srcText);
        window.add(destLabel);
        window.add(destText);

        window.add(enter);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    shortPathDraw(src, dest);
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void shortestPathDistGUI() {
        final JFrame window = new JFrame("Short-test-Path-dest");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        JLabel srcLabel = new JLabel("  Src: ");
        JLabel destLabel = new JLabel(" Dest: ");
        JButton enter = new JButton("   Enter");

        GridLayout g1 = new GridLayout();
        g1.setColumns(2);
        g1.setRows(3);

        window.setLayout(g1);
        window.add(srcLabel);
        window.add(srcText);
        window.add(destLabel);
        window.add(destText);

        window.add(enter);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    //shortPathDraw(src, dest);
                    double w= algorithms.shortestPathDist(src, dest);
                    window.setVisible(false);
                    String ans = ""+ w;
                    final JFrame window = new JFrame(" shortestPathDist");
                    JLabel isConnect = new JLabel(ans);
                    isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
                    JButton close = new JButton("Close");

                    GridLayout g1 = new GridLayout();
                    g1.setRows(2);

                    window.setLayout(g1);
                    window.add(isConnect);
                    window.add(close);
                    window.setSize(300, 150);
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setVisible(true);

                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window.setVisible(false);
                        }
                    });

                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });

    }

    private void CenterGUI() {
        String ans = "                  "+ algorithms.center().getKey();
        final JFrame window = new JFrame("center");
        JLabel isConnect = new JLabel(ans);
        isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
        JButton close = new JButton("Close");

        GridLayout g1 = new GridLayout();
        g1.setRows(2);

        window.setLayout(g1);
        window.add(isConnect);
        window.add(close);
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
            }
        });
    }



    private void TSPGUI() {
        final JFrame window = new JFrame("tsp");
        final JTextField targetsText = new JTextField();
        JLabel targetsLabel = new JLabel("Enter List of NodeDate");
        JButton enter = new JButton("Enter");

        GridLayout g1 = new GridLayout();
        g1.setColumns(1);
        g1.setRows(2);

        window.setLayout(g1);
        window.add(targetsLabel);
        window.add(targetsText);
        window.add(enter);
        window.setSize(650, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String tar = targetsText.getText();
                    List<NodeData> targets = new ArrayList<NodeData>();
                    while (!tar.isEmpty()) {
                        NodeData target;
                        if (!tar.contains(",")) {
                            int t=Integer.parseInt(tar.substring(0));
                            target = Dgraph.getNode(t);
                            tar = "";
                        } else {
                            int t = Integer.parseInt(tar.substring(0, tar.indexOf(",")));
                            target = Dgraph.getNode(t);
                            tar = tar.substring(tar.indexOf(",") + 1);
                        }
                        targets.add(target);
                    }
                    TSPDraw(targets);
                    window.setVisible(false);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });
    }

    // draw the Graph

    public void drawGraph() {
        StdDraw.setCanvasSize(600, 500);
        setScale();
        // edge
           Iterator<NodeData> N = this.Dgraph.nodeIter();
           while(N.hasNext()){
           NodeData vertex  = N.next();
            Collection<EdgeData> edge = new LinkedList<EdgeData>();
            //for (int i=0; i<this.Dgraph.nodeSize();i++){
                Iterator<EdgeData> L = this.Dgraph.edgeIter(vertex.getKey());
                while (L.hasNext()){
                edge.add(L.next());
            }
            if (edge != null) {
                for (EdgeData e : edge) {
                    StdDraw.setPenRadius(0.005);
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    double x0 = this.Dgraph.getNode(e.getSrc()).getLocation().x();
                    double y0 = this.Dgraph.getNode(e.getSrc()).getLocation().y();
                    double x1 = this.Dgraph.getNode(e.getDest()).getLocation().x();
                    double y1 = this.Dgraph.getNode(e.getDest()).getLocation().y();
                    StdDraw.line(x0, y0, x1, y1);
                    StdDraw.circle(x1-3,y1-3,0.001);
                    StdDraw.setPenRadius(0.015);
                    StdDraw.setPenColor(StdDraw.RED);
                    if ((x0 - x1) != 0) {
                        double y = equasionY(x0, x1, y0, y1);

                        if (x1 < x0) {
                            StdDraw.point(x1 + 0.2, y);
                            double wY = equasionWeight(x0, x1, y0, y1);
                            double x = x0 - x1;
                            x = (x / 2) + x1;
                            StdDraw.setPenRadius(0.01);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.text(x, wY, "" + e.getWeight());
                        }
                        if (x1 > x0) {
                            StdDraw.point(x1 - 0.2, y);
                            double wY = equasionWeight(x0, x1, y0, y1);
                            double x = (x1 - x0);
                            x = (x / 2) + x0;
                            StdDraw.setPenRadius(0.01);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.text(x, wY, "" + e.getWeight());
                        }
                    } else if ((x0 - x1) == 0) {
                        if (y1 > y0) {
                            StdDraw.point(x1, y1 - 0.3);
                            StdDraw.setPenRadius(0.01);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.text(x1, (((y1 - y0) / 2) + y0), "" + e.getWeight());
                        }
                        if (y1 < y0) {
                            StdDraw.point(x0, y1 + 0.3);
                            StdDraw.setPenRadius(0.01);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.text(x1, (((y0 - y1) / 2) + y1), "" + e.getWeight());
                        }
                    }

                }
            }
        }
        // draw point, vertex
        Iterator<NodeData> n = this.Dgraph.nodeIter();
        while(n.hasNext()){
        NodeData vertex = n.next();
       // for (NodeData vertex : this.Dgraph.getV()) {
            StdDraw.setPenRadius(0.015);
            double x = vertex.getLocation().x();
            double y = vertex.getLocation().y();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x, y);
            String point = ""+ vertex.getKey();
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.text(x, y + 0.5, point); // the number of the vertex.
        }

    }

    // set X and Y scales

    public void setScale() {
        // scale
        double maxX = 0;
        double maxY = 0;
        Iterator<NodeData> n = this.Dgraph.nodeIter();
        while(n.hasNext()){
        NodeData vertex = n.next();
            if (maxX < vertex.getLocation().x()) {
                maxX = vertex.getLocation().x();
            }
            if (maxY < vertex.getLocation().y()) {
                maxY = vertex.getLocation().y();
            }
        }
        StdDraw.setXscale(-4, maxX + 7);
        StdDraw.setYscale(-4, maxY + 7);
    }

    // find Y point

    public double equasionY(double x0, double x1, double y0, double y1) { // to find the point of the direction.
        double f;
        double yM = y0 - y1;
        double xM = x0 - x1;
        double m = yM / xM;
        if (x1 > x0) {
            double x = (x1 - 0.2);
            f = (m * x) - (m * x1) + y1;
            return f;
        }
        if (x1 < x0) {
            double x = (x1 + 0.2);
            f = (m * x) - (m * x1) + y1;
            return f;
        }
        return 0;
    }

    // find weight point to write

    public double equasionWeight(double x0, double x1, double y0, double y1) { // find the point of the weight.
        double f;
        double yM = y0 - y1;
        double xM = x0 - x1;
        double m = yM / xM;
        if (x1 > x0) {
            double x = (x1 - x0);
            x = (x / 2) + x0;
            f = (m * x) - (m * x1) + y1;
            return f;
        }
        if (x1 < x0) {
            double x = x0 - x1;
            x = (x / 2) + x1;
            f = (m * x) - (m * x1) + y1;
            return f;
        }
        return 0;
    }

    // draw short path

    public void shortPathDraw(int src, int dest) {
        drawGraph();
        List<NodeData> shortPath = new ArrayList<NodeData>();
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        g.init(Dgraph);
        shortPath = g.shortestPath(src, dest);
        if (shortPath != null) {
            for (int i = 0; i < shortPath.size() - 1; i++) {
                double x = shortPath.get(i).getLocation().x();
                double y = shortPath.get(i).getLocation().y();
                if (shortPath.get(i + 1) != null) {
                    double x1 = shortPath.get(i + 1).getLocation().x();
                    double y1 = shortPath.get(i + 1).getLocation().y();
                    StdDraw.setPenRadius(0.005);
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    StdDraw.line(x, y, x1, y1);
                }
            }
        }
    }

    // draw TSP path

    public void TSPDraw(List<NodeData> targets) {
        drawGraph();
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        g.init(Dgraph);
        List<NodeData> TSP = new ArrayList<NodeData>();
        TSP = g.tsp(targets);
        if (TSP != null) {
            for (int i = 0; i < TSP.size() - 1; i++) {
                double x = TSP.get(i).getLocation().x();
                double y = TSP.get(i).getLocation().y();
                if (TSP.get(i + 1) != null) {
                    double x1 = TSP.get(i + 1).getLocation().x();
                    double y1 = TSP.get(i + 1).getLocation().y();
                    StdDraw.setPenRadius(0.005);
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    StdDraw.line(x, y, x1, y1);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {;}

    @Override
    public void mouseEntered(MouseEvent arg0) {;}

    @Override
    public void mouseExited(MouseEvent arg0) {;}

    @Override
    public void mousePressed(MouseEvent arg0) {;}

    @Override
    public void mouseReleased(MouseEvent arg0) {;}


    public static void main(String[] args) {
        DirectedWeightedGraph graph=new DirectedWeightedGraphImpl();
        DirectedWeightedGraphAlgorithms alg = new DirectedWeightedGraphAlgorithmsImpl();
        alg.init(graph);
        alg.load("C:\\Users\\97252\\IdeaProjects\\Ex2\\src\\api\\G1.json");
        new GUI(alg.getGraph());
    }

}