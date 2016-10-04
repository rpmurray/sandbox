package info.masterfrog.sandbox.hamiltoniancycle.main;

/**
 **   Java Program to Implement Hamiltonian Cycle Algorithm
 **/

import info.masterfrog.sandbox.hamiltoniancycle.layout.GraphLayoutManager;
import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;
import info.masterfrog.sandbox.hamiltoniancycle.render.GraphRenderer;
import info.masterfrog.sandbox.hamiltoniancycle.util.HamiltonianCycleUtil;
import info.masterfrog.sandbox.hamiltoniancycle.util.GraphVertexSequencePermutationGeneratorUtil;

import java.awt.*;
import java.util.*;

public class DemoMain {
    private GraphicsPanel graphicsPanel;
    private GraphRenderer graphRenderer;
    private Scanner scanner;
    private HamiltonianCycleUtil hamiltonianCycleUtil;
    private GraphVertexSequencePermutationGeneratorUtil graphVertexSequencePermutationGeneratorUtil;
    private long timer;

    private static boolean LOGGING = false;

    /*** main ***/

    public static void main(String[] args) {
        try {
            // init
            DemoMain demoMain = new DemoMain();

            // startup message
            System.out.println("Hamiltonian Cycle Graph Generator Demo");

            // execute
            //demoMain.graphCompareTest();
            //demoMain.graphConversionTest();
            while (true) {
                int menuChoice = demoMain.handleMenu();
                switch (menuChoice) {
                    case 1:
                        demoMain.generateRandomHamiltonianCycleGraphTest();
                        break;
                    case 2:
                        demoMain.generateGraphStateSpaceTest();
                        break;
                    case 3:
                        demoMain.generateHamiltonianCycleGraphWithNumberOfAttemptsTest();
                        break;
                    case 4:
                        demoMain.generateHamiltonianCycleGraphSetTest();
                        break;
                    case 0:
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Invalid menu choice: " + menuChoice);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("FATAL ERROR!\n" + e + "\n" + Arrays.asList(e.getStackTrace()));
        }
    }

    /*** main end ***/


    /*** constructor ***/

    public DemoMain() throws Exception {
        this.scanner = new Scanner(System.in);
        this.hamiltonianCycleUtil = new HamiltonianCycleUtil();
        this.graphVertexSequencePermutationGeneratorUtil = new GraphVertexSequencePermutationGeneratorUtil();
        this.graphRenderer = new GraphRenderer(5, 20);
        this.graphicsPanel = new GraphicsPanel(new Rectangle(800, 800), graphRenderer);
    }

    /*** constructor end ***/


    /*** util ***/

    private int handleMenu() {
        int choice = 0;

        System.out.println("Select one:\n" +
            "1) Generate Random Hamiltonian Cycle Graph Test\n" +
            "2) Generate Graph State Space Test\n" +
            "3) Generate Hamiltonian Cycle Graph With Number of Attempts Test\n" +
            "4) Generate Hamiltonian Cycle Graph Set\n" +
            "0) Quit\n"
        );

        choice = scanner.nextInt();

        return choice;
    }

    private int getVertexCount() {
        System.out.println("Enter number of vertices:\n");
        int vertexCount = scanner.nextInt();
        //int vertexCount = 3;
        System.out.println();

        return vertexCount;
    }

    private void setTimer() {
        timer = System.nanoTime();
    }

    private double getTimerElapsed() {
        double elapsed = (System.nanoTime() - timer) / 1000000.0;

        return elapsed;
    }

    private void displayExecutionTime() {
        System.out.println("Execution time: " + String.format("%,.1f", getTimerElapsed()) + "ms");
    }

    private void drawGraph(Graph g) {
        GraphLayoutManager.getInstance().generateRandomGridLayout(g, graphicsPanel.getBounds());
        graphRenderer.setGraph(g);
        graphicsPanel.getFrame().repaint();
    }

    /*** util end ***/


    /*** graph conversion test ***/

    private void graphConversionTest() throws Exception {
        Graph g;
        String s;

        // startup message
        System.out.println("+++ Graph Conversion Test +++\n");

        g = new Graph(3);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(0, 1);
        System.out.println("G} " + g.toSimpleString(false));

        s = g.toSequence();
        System.out.println("S} " + s);

        g = Graph.fromSequence(3, s);
        System.out.println("G} " + g.toSimpleString(false));
    }

    /*** graph conversion test end ***/


    /*** graph compare test ***/

    private void graphCompareTest() throws Exception {
        // startup message
        System.out.println("+++ Graph Compare Test +++\n");

        Graph g1 = new Graph(3);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(0, 1);

        Graph g2 = new Graph(3);
        g2.addEdge(0, 2);
        g2.addEdge(1, 2);

        System.out.println("[G1 != G2]");
        System.out.println("G1} " + g1);
        System.out.println("G2} " + g2);
        System.out.println("G1 == G1? " + g1.equals(g1));
        System.out.println("G2 == G2? " + g2.equals(g2));
        System.out.println("G1 == G2? " + g1.equals(g2));
        System.out.println("G2 == G1? " + g2.equals(g1));
        System.out.println();

        g2.addEdge(0, 1);

        System.out.println("[G1 == G2]");
        System.out.println("G1} " + g1);
        System.out.println("G2} " + g2);
        System.out.println("G1 == G1? " + g1.equals(g1));
        System.out.println("G2 == G2? " + g2.equals(g2));
        System.out.println("G1 == G2? " + g1.equals(g2));
        System.out.println("G2 == G1? " + g2.equals(g1));
        System.out.println();
    }

    /*** graph compare test end ***/


    /*** generate random hamiltonian cycle graph test ***/

    private void generateRandomHamiltonianCycleGraphTest() throws Exception {
        // startup message
        System.out.println("\n+++ Generate Random Hamiltonian Cycle Graph Test +++\n");

        // get number of vertices
        int vertexCount = getVertexCount();

        // set timer
        setTimer();

        // setup
        Graph graph;
        boolean solutionFound = false;

        // generate unique graph
        graph = Graph.generateByPathWalk(vertexCount, Math.random()/*0.5*/, -1, -1);

        // determine if hamiltonian cycle exists
        solutionFound = hamiltonianCycleUtil.findHamiltonianCycle(graph);

        // display result
        System.out.println(graph);
        if (solutionFound) {
            System.out.println("Solution found!");
            System.out.println(hamiltonianCycleUtil);
        } else {
            System.out.println("No solution");
        }
        displayExecutionTime();
        drawGraph(graph);
    }

    /*** generate random hamiltonian cycle graph test ***/


    /*** generate graph state space test ***/

    private void generateGraphStateSpaceTest() throws Exception {
        // startup message
        System.out.println("\n+++ Generate Graph State Space Test +++\n");

        // get number of vertices
        int vertexCount = getVertexCount();

        // set timer
        setTimer();

        // setup
        Set<String> binarySequenceStateSpace = generateBinarySequenceStateSpace(vertexCount);

        System.out.println("\nThe state space for a binary sequence of size " + (vertexCount * vertexCount) + " is:\nSize={" + binarySequenceStateSpace.size() + "}\n" + binarySequenceStateSpace);

        Set<String> vertexStateSpace = generateVertexStateSpaceFromBinarySequenceStateSpace(vertexCount, binarySequenceStateSpace);

        System.out.println("\nThe state space for a vertex in a " + vertexCount + " vertex graph is:\nSize={" + vertexStateSpace.size() + "}\n" + vertexStateSpace);

        // convert state space strings to set of graphs
        Set<Graph> graphs = new HashSet<Graph>();
        for (String state : vertexStateSpace) {
            graphs.add(Graph.fromSequence(vertexCount, state));
        }

        System.out.println("\nThe resultant set of " + vertexCount + " vertex graphs is:\nSize={" + graphs.size() + "}");
        int i = 0;
        for (Graph g : graphs) {
            i++;
            System.out.println("\n" + i + "} " + g.toSimpleString(false));
        }

        // display result
        displayExecutionTime();
    }

    private Set<String> generateBinarySequenceStateSpace(int rootSize) {
        Set<String> stateSpace = new HashSet<String>();
        int stateSpaceSize = rootSize * rootSize;
        String stateSpaceString = String.format("%0" + stateSpaceSize + "d", 0);
        stateSpace.add(String.format("%0" + stateSpaceSize + "d", 0));

        if (LOGGING) {
            System.out.println("State Space String[-1]: " + stateSpaceString + " " + stateSpaceString.length());
        }
        for (int i = 0; i < stateSpaceSize; i++) {
            stateSpaceString = stateSpaceString.substring(0, Math.max(i, 0)) + "1" + stateSpaceString.substring(Math.min(i + 1, stateSpaceSize));
            if (LOGGING) {
                System.out.println("State Space String[" + i + "]: " + stateSpaceString + " " + stateSpaceString.length());
            }

            Set<String> stateSpaceSubset = graphVertexSequencePermutationGeneratorUtil.find(String.valueOf(stateSpaceString), rootSize, stateSpaceSize);
            if (LOGGING) {
                System.out.println("State Space Subset[" + i + "]: " + stateSpaceSubset);
            }

            stateSpace.addAll(stateSpaceSubset);
        }

        return stateSpace;
    }

    private Set<String> generateVertexStateSpaceFromBinarySequenceStateSpace(int vertexCount, Set<String> binarySequenceStateSpace) throws Exception {
        Set<String> vertexStateSpace = new HashSet<String>();

        for (String state : binarySequenceStateSpace) {
/*            boolean skip = false;
            for (int i = 0; i < vertexCount; i++) {
                if (state.charAt(i * vertexCount + i) == '1') {
                    skip = true;
                    break;
                }
            }

            if (!skip) {*/
                vertexStateSpace.add(state);
            /*}*/
        }
        Set<Graph> graphSet = new HashSet<Graph>();
        for (String state : vertexStateSpace) {
            graphSet.add(Graph.fromSequence(vertexCount, state));
        }
        vertexStateSpace = new HashSet<String>();
        for (Graph g : graphSet) {
            vertexStateSpace.add(g.toSequence());
        }

        return vertexStateSpace;
    }

    /*** generate graph state space test end ***/


    /*** generate hamiltonian cycle graph with number of attempts test ***/

    private void generateHamiltonianCycleGraphWithNumberOfAttemptsTest() throws Exception {
        // startup message
        System.out.println("\n+++ Generate Hamiltonian Cycle Graph With Number of Attempts Test +++\n");

        // get number of vertices
        int vertexCount = getVertexCount();

        // set timer
        setTimer();

        // get number of attempts
        System.out.println("Enter number of attempts:\n");
        int attemptCount = scanner.nextInt();
        System.out.println();

        // setup
        Graph graph = null;
        Graph[] graphs = new Graph[attemptCount];
        boolean solutionFound;

        // init
        solutionFound = false;

        // try to find a graph with a hamiltonian cycle so many times
        for (int i = 0; i < attemptCount; i++) {
            // generate unique graph
            graph = generateUniqueGraph(vertexCount, i, graphs);
            graphs[i] = graph;

            // print graph matrix
            if (LOGGING) {
                System.out.println(String.format("%-5s ", (i + 1) + "]") + graph);
            }

            // determine if hamiltonian cycle exists
            solutionFound = hamiltonianCycleUtil.findHamiltonianCycle(graph);

            // abort if a solution was found
            if (solutionFound) {
                break;
            }
        }

        // display result
        if (solutionFound) {
            System.out.println("Solution found!");
            System.out.println(graph);
            System.out.println(hamiltonianCycleUtil);
        } else {
            System.out.println("No solution");
        }
        displayExecutionTime();
    }

    private Graph generateUniqueGraph(int vertexCount, int graphIndex, Graph[] existingGraphs) throws Exception {
        Graph graph;

        for (int i = 0; true; i++) {
            // log
            if (LOGGING) {
                System.out.print((i % 10 == 0 ? "x" : ".") + (i % 100 == 0 ? (" " + i + "\n") : ""));
            }

            // generate random graph
            graph = Graph.generateRandomGraph(vertexCount);

            // check against existing graphs for a match
            boolean matchFound = false;
            for (int j = 0; j < graphIndex; j++) {
                if (graph.equals(existingGraphs[j])) {
                    matchFound = true;
                }
            }

            // if not a match its a keeper
            if (!matchFound) {
                return graph;
            }
        }
    }

    /*** generate hamiltonian cycle graph with number of attempts test end ***/


    /*** generate hamiltonian cycle graph set test ***/

    private void generateHamiltonianCycleGraphSetTest() throws Exception {
        // startup message
        System.out.println("\n+++ Generate Hamiltonian Cycle Graphs Test +++\n");

        // get number of vertices
        int vertexCount = getVertexCount();

        int maxGraphsPossible = vertexCount;

        // get number of attempts
        int quantity = 0;
        do {
            System.out.println("Enter number of graphs:\n");
            quantity = scanner.nextInt();
            System.out.println();
        } while (quantity < 1 && quantity > maxGraphsPossible);

        // set timer
        setTimer();

        // setup
        HashMap<String, Graph> graphs = new HashMap<String, Graph>();
        for (int i = 0; i < quantity; i++) {
            // setup
            Graph g;

            // generate unique graph
            do {
                double randomWeight = 0.5;
                g = Graph.generateByPathWalk(vertexCount, randomWeight, -1, -1);
            } while (graphs.containsKey(g.toSequence()));

            // add to graph set
            graphs.put(g.toSequence(), g);
        }

        // display result
        int i = 0;
        for (String id : graphs.keySet()) {
            Graph g = graphs.get(id);
            i++;
            System.out.println("\n" + i + "} " + g.toSimpleString(false));
        }
        displayExecutionTime();
    }

    /*** generate hamiltonian cycle graph set test ***/
}
