package info.masterfrog.sandbox.hamiltoniancycle.model;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private int[][] vertices;
    private Set<Pair<Integer, Integer>> edges;

    public Graph(int vertexCount) {
        vertices = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                vertices[i][j] = 0;
            }
        }
        edges = new HashSet<>();
    }

    /**
     * Generates a graph by walking a path through all vertices, thereby guaranteeing it has a hamiltonian cycle,
     * and padding it with extra edges at random, up to a certain maximum number of extra edges in total
     * @param vertexCount   number of vertices (size) in the generated graph
     * @param randomWeight  value between 0.0 and 1.0 which determines the likelihood a random edge will be added
     *                      0.5 means 50% of the time it will, 0.0 means never, 1.0 means always
     * @param minExtraEdges minimum number of extra edges to add,
     *                      0 <= minExtraEdges < floor((vertexCount * vertexCount) / 2) - floor(vertexCount / 2) - 1,
     *                      if -1 no minimum is enforced
     * @param maxExtraEdges maximum number of extra edges to add,
     *                      0 <= maxExtraEdges < floor((vertexCount * vertexCount) / 2) - floor(vertexCount / 2) - 1,
     *                      if -1 no maximum is enforced
     * @return Graph
     * @throws Exception
     */
    public static Graph generateByPathWalk(int vertexCount, double randomWeight, int minExtraEdges, int maxExtraEdges) throws Exception {
        // setup
        Graph graph = new Graph(vertexCount);
        int extraEdgesCount = 0;

        // define extra edge boundaries
        // -1 is a special value, otherwise this should be 0
        int extraEdgesLowerBoundary = -1;
        // the root of this formula defines the most edges we can have total
        // total edges = 1/2 vertex space size (edges = 2 vertices) - 1/2 diagonals in vertex space (no self referencing edges)
        // further, we can only have vertex count less than that of extra edges,
        // since our deterministic path counts towards the total possible edges
        int extraEdgesUpperBoundary = (int) Math.floor((vertexCount * vertexCount) / 2) - (int) Math.floor(vertexCount / 2) - vertexCount;

        // validate extra edge criteria
        if (minExtraEdges < extraEdgesLowerBoundary || minExtraEdges > extraEdgesUpperBoundary) {
            throw new Exception("min number of extra edges out of bounds: " + minExtraEdges + "|" + extraEdgesLowerBoundary + ":" + extraEdgesUpperBoundary);
        }
        if (maxExtraEdges < extraEdgesLowerBoundary || maxExtraEdges > extraEdgesUpperBoundary) {
            throw new Exception("max number of extra edges out of bounds: " + maxExtraEdges + "|" + extraEdgesLowerBoundary + ":" + extraEdgesUpperBoundary);
        }

        // deterministically generate hamiltonian cycle in graph and add in other random edges
        do {
            for (int i = 0; i < vertexCount; i++) {
                // add base hamiltonian cycle edge if needed
                if (!graph.hasEdge(i, (i + 1) % vertexCount)) {
                    graph.addEdge(i, (i + 1) % vertexCount);
                }

                // add extra edges randomly
                for (int j = 0; j < vertexCount; j++) {
                    // don't generate self-connecting edges
                    if (j == i) {
                        break;
                    }

                    // if within min and max thresholds, try and add an edge
                    if ((minExtraEdges == -1 || minExtraEdges > extraEdgesCount) &&
                        (maxExtraEdges == -1 || maxExtraEdges < extraEdgesCount)) {
                        // randomly determine if an edge should be added from i to j
                        boolean random = (Math.random() <= randomWeight);

                        // add it if so and it doesn't exist
                        if (random && !graph.hasEdge(i, j)) {
                            graph.addEdge(i, j);
                            extraEdgesCount++;
                        }
                    }
                }
            }
        } while (minExtraEdges != -1 && minExtraEdges > extraEdgesCount);

        return graph;
    }

    public static Graph generateRandomGraph(int vertexCount) throws Exception {
        // create base graph
        Graph graph = new Graph(vertexCount);

        // randomly generate paths
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i != j) {
                    Boolean createPath = Math.round(Math.random()) == 1;
                    if (createPath) {
                        graph.addEdge(i, j);
                    }
                }
            }
        }

        return graph;
    }

    public void addEdge(int v1, int v2) throws Exception {
        if (v1 < 0 || v1 > vertices.length) {
            throw new Exception("vertex 1 out of bounds");
        }
        if (v2 < 0 || v2 > vertices[v1].length) {
            throw new Exception("vertex 2 out of bounds");
        }
        vertices[v1][v2] = 1;
        vertices[v2][v1] = 1;
        edges.add(new Pair<>(Math.min(v1, v2), Math.max(v1, v2)));
    }

    public void removeEdge(int v2, int v1) throws Exception {
        if (v2 < 0 || v2 > vertices.length) {
            throw new Exception("vertex 1 out of bounds");
        }
        if (v1 < 0 || v1 > vertices[v2].length) {
            throw new Exception("vertex 2 out of bounds");
        }
        vertices[v2][v1] = 0;
        vertices[v1][v2] = 0;
        edges.remove(new Pair<>(Math.min(v1, v2), Math.max(v1, v2)));
    }

    public int getVertexCount() {
        return vertices.length;
    }

    public int[] getVertex(int idx) {
        return vertices[idx];
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public boolean hasEdge(int v1, int v2) {
        return edges.contains(new Pair(Math.min(v1, v2), Math.max(v1, v2)));
    }

    // TODO: Hack, remove this!
    public int[][] getGraphMatrixClone() {
        return java.util.Arrays.stream(vertices).map(el -> el.clone()).toArray($ -> vertices.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;

        Graph graph = (Graph) o;

        return Arrays.deepEquals(vertices, graph.vertices);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(vertices);
    }


    public String toSequence() {
        String s = "";

        int[][] vertexSpace = getGraphMatrixClone();
        for (int i = 0; i < getVertexCount(); i++) {
            for (int j = 0; j < getVertexCount(); j++) {
                s += String.valueOf(vertexSpace[i][j]);
            }
        }

        return s;
    }

    public static Graph fromSequence(int vertexCount, String s) throws Exception{
        Graph graph = new Graph(vertexCount);

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (s.charAt(j * vertexCount + i) == '1') {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }

    @Override
    public String toString() {
        String s = "";
        int spacing = String.valueOf(vertices.length).length();
        s += "Graph{" + vertices.length + "," + edges.size() + "}:\n";
        s += "       ";
        for (int i = 0; i < vertices.length; i++) {
            s += String.format("%-" + spacing + "d", i + 1) + " ";
        }
        s += "\n" + toSimpleString(true);

        return s;
    }

    public String toSimpleString(boolean showLabel) {
        String s = "";
        int spacing = String.valueOf(vertices.length).length();

        for (int i = 0; i < vertices.length; i++) {
            s += "\n";
            if (showLabel) {
                s += String.format("%-5d  ", i + 1);
            }
            for (int j = 0; j < vertices[i].length; j++) {
                s += String.format("%-" + spacing + "d", vertices[i][j]) + " ";
            }
        }

        return s;
    }
}
