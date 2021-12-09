package api;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class GUI extends JFrame implements ActionListener, MouseListener {
    private DirectedWeightedGraph Graph;
    private DirectedWeightedGraphAlgorithms Graph_Algorithms = new DirectedWeightedGraphAlgorithmsImpl();
    private int MC;


    public GUI(DirectedWeightedGraph graph) {
        this.Graph = graph;
        Graph_Algorithms.init(Graph);
        this.MC = Graph.getMC();
        init();
    }

    private void init() {
        //set the size of the window
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("The Directed Weighted Graph Algorithms by: Raz & Lior");
        //create the tab option File and his options
        MenuBar menuBar = new MenuBar();
        Menu File = new Menu("File");
        menuBar.add(File);
        this.setMenuBar(menuBar);
        MenuItem Load = new MenuItem("Load");
        Load.addActionListener(this);
        File.add(Load);
        MenuItem Save = new MenuItem("Save");
        Save.addActionListener(this);
        File.add(Save);

        //create the tab option Graph and his options
        Menu Graph = new Menu("Graph");
        menuBar.add(Graph);
        MenuItem addNode = new MenuItem("Add Node");
        addNode.addActionListener(this);
        MenuItem addEdge = new MenuItem("Connect");
        addEdge.addActionListener(this);
        MenuItem removeNode = new MenuItem("Remove Node");
        removeNode.addActionListener(this);
        MenuItem removeEdge = new MenuItem("Remove Edge");
        removeEdge.addActionListener(this);

        Graph.add(addNode);
        Graph.add(addEdge);
        Graph.add(removeNode);
        Graph.add(removeEdge);

        //create the tab Graph_Algorithms option File and his options
        Menu Graph_Algorithms = new Menu("Graph_Algorithms");
        menuBar.add(Graph_Algorithms);
        MenuItem isConnect = new MenuItem("Is Connected");
        isConnect.addActionListener(this);
        MenuItem sortPath = new MenuItem("Short Path");
        sortPath.addActionListener(this);
        MenuItem sortPathDist = new MenuItem("Short Path Dist");
        sortPathDist.addActionListener(this);
        MenuItem center = new MenuItem("Center");
        center.addActionListener(this);
        MenuItem TSP = new MenuItem("TSP");
        TSP.addActionListener(this);

        Graph_Algorithms.add(TSP);
        Graph_Algorithms.add(isConnect);
        Graph_Algorithms.add(sortPathDist);
        Graph_Algorithms.add(sortPath);
        Graph_Algorithms.add(center);

        this.addMouseListener(this);
        setVisible(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (GUI.this.Graph.getMC() != MC) {
                            repaint();
                            MC = GUI.this.Graph.getMC();
                        }
                    }} } });
        thread.start();
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        DirectedWeightedGraphAlgorithms graph = new DirectedWeightedGraphAlgorithmsImpl();
        graph.init(Graph);
        //check what is the action and go to the matching function
        if (action.equals("Save")) {Save();}
        if (action.equals("Load")) {Load();}
        if (action.equals("Add Node")) {addNode();}
        if (action.equals("Connect")) {Connect();}
        if (action.equals("Remove Node")) {removeNode();}
        if (action.equals("Remove Edge")) {removeEdge();}
        if (action.equals("Short Path")) {shortestPath();}
        if (action.equals("Short Path Dist")) {shortestPathDist();}
        if (action.equals("Is Connected")) {isConnected();}
        if (action.equals("Center")) {Center();}
        if (action.equals("TSP")) {tsp();}
    }

    private void Save() {
        FileDialog file = new FileDialog(this, "Save", FileDialog.SAVE);
        file.setFile("*.json");
        file.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });
        file.setVisible(true);
        String fileDirectory = file.getDirectory();
        String fileName = file.getFile();
        if (fileName != null) {
            this.Graph_Algorithms.save(fileDirectory+fileName);
        }
    }

    private void Load() {
        FileDialog file = new FileDialog(this, "Load", FileDialog.LOAD);
        file.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) { return name.endsWith(".json");   }
        });
        file.setVisible(true);
        String fileDirectory = file.getDirectory();
        String fileName = file.getFile();
        if (fileName != null) {
            this.Graph_Algorithms.load(fileDirectory+fileName);
            Graph = Graph_Algorithms.getGraph();
            new GUI(Graph);
            repaint();
        }
    }

    private void addNode() {
        final JFrame SCREEN = new JFrame("Add Node");
        final JTextField node_x = new JTextField();
        JLabel X = new JLabel("       X: ");
        X.setFont(new Font("Ariel", X.getFont().getStyle(), 15));
        final JTextField node_y = new JTextField();
        JLabel Y = new JLabel("       Y: ");
        Y.setFont(new Font("Ariel", Y.getFont().getStyle(), 15));
        final JTextField node_key = new JTextField();
        JLabel key = new JLabel("       Key: ");
        key.setFont(new Font("Ariel", key.getFont().getStyle(), 15));
        JButton enter = new JButton("Enter");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(4);
        gridLayout.setColumns(2);
        SCREEN.setLayout(gridLayout);

        SCREEN.add(X);
        SCREEN.add(node_x);
        SCREEN.add(Y);
        SCREEN.add(node_y);
        SCREEN.add(key);
        SCREEN.add(node_key);
        SCREEN.add(enter);
        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(node_x.getText());
                    double y = Double.parseDouble(node_y.getText());
                    int key=Integer.parseInt(node_key.getText());
                    GeoLocation p = new GeoLocationImpl(x, y,0.0);
                    Graph.addNode(new NodeDataImpl(p,key));
                    Graph = Graph_Algorithms.getGraph();

                    SCREEN.dispose();
                    repaint();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });
    }

    private void Connect() {
        final JFrame SCREEN = new JFrame("Add Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        final JTextField weightText = new JTextField();
        JLabel src = new JLabel("Source Key: ");
        src.setFont(new Font("Ariel", src.getFont().getStyle(), 15));
        SCREEN.add(src);
        SCREEN.add(srcText);
        JLabel dest = new JLabel("Destination key: ");
        dest.setFont(new Font("Ariel", dest.getFont().getStyle(), 15));
        SCREEN.add(dest);
        SCREEN.add(destText);
        JLabel weight = new JLabel("Weight: ");
        weight.setFont(new Font("Ariel", weight.getFont().getStyle(), 15));
        SCREEN.add(weight);
        SCREEN.add(weightText);
        JButton enter = new JButton("Enter");
        SCREEN.add(enter);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(2);
        gridLayout.setRows(4);
        SCREEN.setLayout(gridLayout);

        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //replace the text we got from the user to integer value and double value
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    double w = Double.parseDouble(weightText.getText());
                    //send the variables to the Graph function connect
                    Graph.connect(src, dest, w);
                    Graph = Graph_Algorithms.getGraph();
                    //close the screen
                    SCREEN.dispose();
                    //draw the Graph
                    repaint();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }}});

    }

    private void removeNode() {
        init();
        final JFrame SCREEN = new JFrame("Remove Node");
        final JTextField NodeKey = new JTextField();
        JLabel nodeKey = new JLabel("    Node Key: ");
        nodeKey.setFont(new Font("Ariel", nodeKey.getFont().getStyle(), 15));
        JButton Enter = new JButton("Enter");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);
        gridLayout.setColumns(2);
        SCREEN.setLayout(gridLayout);

        SCREEN.add(nodeKey);
        SCREEN.add(NodeKey);
        SCREEN.add(Enter);

        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //replace the text we got from the user to integer value
                    int key = Integer.parseInt(NodeKey.getText());
                    //send the variable to the function
                    Graph.removeNode(key);
                    Graph = Graph_Algorithms.getGraph();
                    //close the screen
                    SCREEN.dispose();
                    //draw the Graph
                    repaint();
            }
        });
    }

    private void removeEdge() {
        init();
        final JFrame SCREEN = new JFrame("Remove Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();

        JLabel Src = new JLabel("   Source Key: ");
        Src.setFont(new Font("Ariel", Src.getFont().getStyle(), 15));
        JLabel Dest = new JLabel("   Destination Key: ");
        Dest.setFont(new Font("Ariel", Dest.getFont().getStyle(), 15));
        JButton Enter = new JButton("Enter");


        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(2);
        gridLayout.setRows(3);
        SCREEN.setLayout(gridLayout);

        SCREEN.add(Src);
        SCREEN.add(srcText);
        SCREEN.add(Dest);
        SCREEN.add(destText);
        SCREEN.add(Enter);

        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);
        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //replace the text we got from the user to integer values
                    int src = Integer.parseInt(srcText.getText());
                    int dest = Integer.parseInt(destText.getText());
                    //send the variables to the function
                    Graph.removeEdge(src, dest);
                    Graph = Graph_Algorithms.getGraph();
                    //close the screen
                    SCREEN.dispose();
                    //drow the Graph
                    repaint();
                    SCREEN.setVisible(false);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }}});
    }

    private void isConnected() {
        String ans = "                "+ Graph_Algorithms.isConnected();
        final JFrame SCREEN = new JFrame("Is Connected");
        JLabel isConnect = new JLabel(ans);
        isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
        JButton close = new JButton("Close");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);

        SCREEN.setLayout(gridLayout);
        SCREEN.add(isConnect);
        SCREEN.add(close);

        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                SCREEN.setVisible(false);
            }
        });}

    private void shortestPath() {
        final JFrame SCREEN = new JFrame("Short Path");
        final JTextField src_text = new JTextField();
        final JTextField dest_text = new JTextField();

        JLabel src_label = new JLabel("Src: ");
        src_label.setFont(new Font("Ariel", src_label.getFont().getStyle(), 15));
        JLabel dest_label = new JLabel("Dest: ");
        dest_label.setFont(new Font("Ariel", dest_label.getFont().getStyle(), 15));
        JButton enter = new JButton("   Enter");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(2);
        gridLayout.setRows(3);
        SCREEN.setLayout(gridLayout);

        SCREEN.add(src_label);
        SCREEN.add(src_text);
        SCREEN.add(dest_label);
        SCREEN.add(dest_text);

        SCREEN.add(enter);
        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = new JFrame();
                    //replace the text we got from the user to integer values
                    int src = Integer.parseInt(src_text.getText());
                    int dest = Integer.parseInt(dest_text.getText());
                    //send the variables to the function
                    List<NodeData> shortPath1 = Graph_Algorithms.shortestPath(src, dest);
                    System.out.println(shortPath1);
                    String ans ="";
                    if (shortPath1 != null) {
                        for (int i = 0; i < shortPath1.size()-1; i++) {
                            ans = ans + shortPath1.get(i).getKey()+"-> ";
                        }
                        ans= ans +shortPath1.get(shortPath1.size()-1).getKey();
                        //the print of the sort path
                        JOptionPane.showMessageDialog(frame, "The shortest Path is: " + ans);
                        Graph = Graph_Algorithms.getGraph();
                        //close the screen
                        SCREEN.dispose();
                        //draw the Graph
                        repaint();
                    }
                    else {
                        //if the is no path print message to the user
                        JOptionPane.showMessageDialog(null, "There is no Path");

                    }

                    SCREEN.setVisible(false);
                } catch (Exception exception) {
                    //if the user insert bad variables
                    JOptionPane.showMessageDialog(null, "Invalid value");
                } }});
    }

    private void shortestPathDist() {
        final JFrame SCREEN = new JFrame("Short Path dist");
        final JTextField src_text = new JTextField();
        final JTextField dest_text = new JTextField();
        JLabel src_label = new JLabel("Src: ");
        src_label.setFont(new Font("Ariel", src_label.getFont().getStyle(), 15));
        JLabel dest_label = new JLabel("Dest: ");
        dest_label.setFont(new Font("Ariel", dest_label.getFont().getStyle(), 15));
        JButton enter = new JButton("   Enter");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(2);
        gridLayout.setRows(3);

        SCREEN.setLayout(gridLayout);
        SCREEN.add(src_label);
        SCREEN.add(src_text);
        SCREEN.add(dest_label);
        SCREEN.add(dest_text);

        SCREEN.add(enter);
        SCREEN.setSize(300, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //replace the text we got from the user to integer values and double value
                    int src = Integer.parseInt(src_text.getText());
                    int dest = Integer.parseInt(dest_text.getText());
                    //send the variables to the function
                    double w= Graph_Algorithms.shortestPathDist(src, dest);
                    SCREEN.setVisible(false);
                    String ans = ""+ w;
                    final JFrame SCREEN = new JFrame(" shortestPathDist");
                    JLabel isConnect = new JLabel(ans);
                    isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
                    JButton close = new JButton("Close");

                    GridLayout gridLayout1 = new GridLayout();
                    gridLayout1.setRows(2);

                    SCREEN.setLayout(gridLayout1);
                    SCREEN.add(isConnect);
                    SCREEN.add(close);
                    SCREEN.setSize(300, 150);
                    SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    SCREEN.setVisible(true);

                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SCREEN.setVisible(false);
                        }
                    });
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }}});

    }

    private void Center() {
        //if the Graph is not connected
        if (Graph_Algorithms.center() == null) {
            JOptionPane.showMessageDialog(null, "Is not Connected");
        } else {

            String ans = "                  " + Graph_Algorithms.center().getKey();
            final JFrame SCREEN = new JFrame("center");
            JLabel isConnect = new JLabel(ans);
            isConnect.setFont(new Font("Ariel", isConnect.getFont().getStyle(), 25));
            JButton close = new JButton("Close");

            GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(2);
            SCREEN.setLayout(gridLayout);

            SCREEN.add(isConnect);
            SCREEN.add(close);
            SCREEN.setSize(300, 150);
            SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SCREEN.setVisible(true);


            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SCREEN.dispose();
                }
            });
        }
    }


    private void tsp() {
        final JFrame SCREEN = new JFrame("tsp");
        final JTextField targetsText = new JTextField();
        JLabel targetsLabel = new JLabel("Enter List of NodeDate");
        targetsLabel.setFont(new Font("Ariel", targetsLabel.getFont().getStyle(), 25));
        JButton enter = new JButton("Enter");

        GridLayout gridLayout = new GridLayout();
        gridLayout.setColumns(1);
        gridLayout.setRows(2);
        SCREEN.setLayout(gridLayout);

        SCREEN.add(targetsLabel);
        SCREEN.add(targetsText);
        SCREEN.add(enter);
        SCREEN.setSize(650, 150);
        SCREEN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN.setVisible(true);

        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame jFrame = new JFrame();
                    //cut the list we got:
                    String List = targetsText.getText();
                    List<NodeData> nodeDataList = new ArrayList<NodeData>();
                    while (!List.isEmpty()) {
                        NodeData nodeData;
                        if (!List.contains(",")) {
                            int t=Integer.parseInt(List.substring(0));
                            nodeData = Graph.getNode(t);
                            List = "";
                        } else {
                            int t = Integer.parseInt(List.substring(0, List.indexOf(",")));
                            nodeData = Graph.getNode(t);
                            List = List.substring(List.indexOf(",") + 1);
                        }
                        nodeDataList.add(nodeData);
                    }
                    ArrayList<NodeData> TSP_List = (ArrayList<NodeData>) Graph_Algorithms.tsp(nodeDataList);
                    String str ="";
                    //save the list of the path in string:
                    if (TSP_List != null) {
                        for (int i = 0; i < TSP_List.size()-1; i++) {
                            str = str + TSP_List.get(i).getKey()+"->";
                        }
                        str= str +TSP_List.get(TSP_List.size()-1).getKey();
                        //print the Path
                        JOptionPane.showMessageDialog(jFrame, "The shortest Path is: " + str);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Is not connected");
                    }
                    SCREEN.dispose();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Invalid value");
                }
            }
        });
    }

    // this function draw the Graph
    public void paint(Graphics G) {
        HashMap<Integer, Double> get_X = new HashMap<>(Graph.nodeSize());
        HashMap<Integer, Double> get_Y = new HashMap<>(Graph.nodeSize());
        Graphics2D graphics = (Graphics2D) G;
        super.paint(graphics);
        graphics.setStroke(new BasicStroke(2));
        super.paint(graphics);
        Iterator<NodeData> Nodes = Graph.nodeIter();
        while (Nodes.hasNext()) {
            NodeData node_data = Nodes.next();
            GeoLocation geo = node_data.getLocation();
            get_X.put(node_data.getKey(), geo.x());
            get_Y.put(node_data.getKey(), geo.y());
        }
        Normalization(get_X, 70, this.getWidth() - 70);
        Normalization(get_Y, 70, this.getHeight() - 70);
        Iterator<NodeData> nodesIter = Graph.nodeIter();
        while (nodesIter.hasNext()) {
            NodeData node_data = nodesIter.next();
            int key = node_data.getKey();
            int x = get_X.get(key).intValue();
            int y = get_Y.get(key).intValue();
            graphics.setColor(Color.BLACK);
            graphics.fillOval(x, y, 7 * 2, 7 * 2);
            graphics.setColor(Color.BLACK);
            graphics.drawString("" + key, x, y-10);

        }
        Iterator<EdgeData> edgeIter = Graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData edge_data = edgeIter.next();
            GeoLocation Geo_src = Graph.getNode(edge_data.getSrc()).getLocation();
            GeoLocation Geo_dest = Graph.getNode(edge_data.getDest()).getLocation();
            graphics.setColor(Color.BLACK);
            graphics.drawLine((int) Geo_src.x(), (int) Geo_src.y(), (int) Geo_dest.x(), (int) Geo_dest.y());

        }

        Iterator<EdgeData> edgeIter1 = Graph.edgeIter();
        while (edgeIter1.hasNext()) {
            EdgeData edge1 = edgeIter1.next();
            int x1 = get_X.get(edge1.getSrc()).intValue();
            int y1 = get_Y.get(edge1.getSrc()).intValue();
            int x2 = get_X.get(edge1.getDest()).intValue();
            int y2 = get_Y.get(edge1.getDest()).intValue();
            //draw the edges
            graphics.setColor(Color.gray);
            graphics.drawLine(x1+10, y1+10, x2+10, y2+10);
            //draw the direction o
            graphics.setColor(Color.pink);
            graphics.fillOval(x2+7, y2+7, 7 * 2, 7 * 2);

        }
    }

//This function normalizes the graph drawing to the screen size
    private void Normalization(HashMap<Integer, Double> normalize, double SRCoutput, double DESToutput) {
        double min = Double.MAX_VALUE,max = Double.MIN_VALUE;
        for (Integer key : normalize.keySet()) {
            double cnt = normalize.get(key);
            if (cnt < min) {min = cnt;}
            if (cnt > max) {max = cnt;}
        }
        double current = ((DESToutput - SRCoutput) / (max - min));
        double finalMin = min;
        normalize.replaceAll((k, v) -> SRCoutput + current * (normalize.get(k) - finalMin));

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



}
