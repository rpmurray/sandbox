package info.masterfrog.sandbox.hamiltoniancycle;

/**
 **   Java Program to Implement Hamiltonian Cycle Algorithm
 **/

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DemoMain {
    private Scanner scanner;
    private HamiltonianCycle hc;

    private static boolean LOGGING = false;

    /**
     * Main function
     **/
    public static void main(String[] args) {
        try {
            // init
            DemoMain demoMain = new DemoMain();

            // startup message
            System.out.println("Hamiltonian Cycle Graph Generator\n");

            // execute
            //demoMain.graphCompareTest();
            //demoMain.generateGraphWithNumberOfAttempts();
            while (true) {
                // get number of vertices
                int vertexCount = demoMain.getVertexCount();

                demoMain.generateGraphStateSpace(vertexCount);
            }
        } catch (Exception e) {
            System.out.print("FATAL ERROR!\n" + e);
        }
    }

    public DemoMain() throws Exception {
        scanner = new Scanner(System.in);
        hc = new HamiltonianCycle();
    }

    private int getVertexCount() {
        System.out.println("\n\nEnter number of vertices\n");
        int vertexCount = scanner.nextInt();
        //int vertexCount = 3;
        System.out.println();

        return vertexCount;
    }

    private void graphCompareTest() throws Exception{
        Graph g1 = new Graph(3);
        g1.addPath(0, 2);
        g1.addPath(1, 2);
        g1.addPath(0, 1);

        Graph g2 = new Graph(3);
        g2.addPath(0, 2);
        g2.addPath(1, 2);

        System.out.println("[G1 != G2]");
        System.out.print("G1} " + g1);
        System.out.print("G2} " + g2);
        System.out.println("G1 == G1? " + g1.equals(g1));
        System.out.println("G2 == G2? " + g2.equals(g2));
        System.out.println("G1 == G2? " + g1.equals(g2));
        System.out.println("G2 == G1? " + g2.equals(g1));
        System.out.println();

        g2.addPath(0, 1);

        System.out.println("[G1 == G2]");
        System.out.print("G1} " + g1);
        System.out.print("G2} " + g2);
        System.out.println("G1 == G1? " + g1.equals(g1));
        System.out.println("G2 == G2? " + g2.equals(g2));
        System.out.println("G1 == G2? " + g1.equals(g2));
        System.out.println("G2 == G1? " + g2.equals(g1));
        System.out.println();
    }

    private void generateGraphStateSpace(int vertexCount) throws Exception {
        // setup
        //Graph graph;
        //Graph priorGraph = null;
        //boolean solutionFound = false;

        // try to find a graph with a hamiltonian cycle so many times
        //String stateSpaceBase = String.format("%" + stateSpaceSize + "s", "");
        /*
        int[] stateSpaceBase = new int[stateSpaceSize];
        for (int i = 0; i < stateSpaceSize; i++) {
            stateSpaceBase[i] = 0;
        }
        */
        Set<String> binarySequenceStateSpace = generateBinarySequenceStateSpace(vertexCount * vertexCount);

        System.out.println("\nThe state space for a binary sequence of size " + (vertexCount * vertexCount) + " is:\nSize={" + binarySequenceStateSpace.size() + "}\n" + binarySequenceStateSpace);

        Set<Graph> vertexStateSpace = generateVertexStateSpaceFromBinarySequenceStateSpace(vertexCount, binarySequenceStateSpace);

        System.out.println("\nThe state space for a vertex in a " + vertexCount + " vertex graph is:\nSize={" + vertexStateSpace.size() + "}");
        int i = 0;
        for (Graph g : vertexStateSpace) {
            i++;
            System.out.println("\n" + i + "} " + g.toSimpleString(false));
        }

//            graph = new Graph(vertexCount);
//            // generate unique graph
//            for (int j = 0; j < vertexSpace; j++) {
//                if (i == j) {
//                    continue;
//                }
//                graph.addPath(i, j);
//
//            // print graph matrix
//            if (LOGGING) {
//                System.out.println(String.format("%-5s ", (i + 1) + "]") + graph);
//            }
//
//            // determine if hamiltonian cycle exists
//            solutionFound = hc.findHamiltonianCycle(graph);
//
//            // abort if a solution was found
//            if (solutionFound) {
//                break;
//            } else {
//
//            }
//        }

        // print result
//        if (solutionFound) {
//            System.out.println("Solution found!");
//            System.out.println(graph);
//            System.out.println(hc);
//        } else {
//            System.out.println("No solution");
//        }
    }

    private Set<String> generateBinarySequenceStateSpace(int stateSpaceSize) {
        Set<String> stateSpace = new HashSet<String>();
        PermutationGenerator permutationGenerator = new PermutationGenerator();
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

            Set<String> stateSpaceSubset = permutationGenerator.find(String.valueOf(stateSpaceString));
            if (LOGGING) {
                System.out.println("State Space Subset[" + i + "]: " + stateSpaceSubset);
            }

            stateSpace.addAll(stateSpaceSubset);
        }

        return stateSpace;
    }

    private Set<Graph> generateVertexStateSpaceFromBinarySequenceStateSpace(int vertexCount, Set<String> binarySequeenceStateSpace) throws Exception {
        Set<Graph> vertexStateSpace = new HashSet<Graph>();

        for (String state : binarySequeenceStateSpace) {
            boolean skip = false;
            for (int i = 0; i < vertexCount; i++) {
                if (state.substring(i * vertexCount + i, (i * vertexCount) + i + 1).equals("1")) {
                    skip = true;
                    break;
                }
            }

            if (!skip) {
                Graph graph = convertStringToGraph(vertexCount, state);
                vertexStateSpace.add(graph);
            }
        }

        return vertexStateSpace;
    }

    private Graph convertStringToGraph(int vertexCount, String string) throws Exception{
        Graph graph = new Graph(vertexCount);

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (string.charAt(j * vertexCount + i) == '1') {
                    graph.addPath(i, j);
                }
            }
        }

        return graph;
    }

    private Graph generateNextGraph(int vertexCount, int graphIndex, Graph priorGraph) {
        Graph graph = priorGraph;

        for (int i = 0; i < graphIndex % vertexCount / 2; i++) {

        }

        return graph;
    }

    private void generateGraphWithNumberOfAttempts() throws Exception {
        // get number of vertices
        System.out.println("Enter number of vertices\n");
        int vertexCount = scanner.nextInt();
        System.out.println();

        // get number of attempts
        System.out.println("Enter number of attempts to find a graph of this size with a hamiltonian cycle\n");
        String scan = scanner.next();
        System.out.println();
        int attemptCount = scan.equals("") ? 1 : Integer.parseInt(scan);

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
            solutionFound = hc.findHamiltonianCycle(graph);

            // abort if a solution was found
            if (solutionFound) {
                break;
            }
        }

        // print result
        if (solutionFound) {
            System.out.println("Solution found!");
            System.out.println(graph);
            System.out.println(hc);
        } else {
            System.out.println("No solution");
        }
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
}
