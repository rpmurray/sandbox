package info.masterfrog.sandbox.hamiltoniancycle;

import java.util.Arrays;

public class Graph {
    private int[][] vertices;

    public Graph(int vertexCount) {
        vertices = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                vertices[i][j] = 0;
            }
        }
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
                        graph.addPath(i, j);
                    }
                }
            }
        }

        return graph;
    }

    public void addPath(int vertex1, int vertex2) throws Exception {
        if (vertex1 < 0 || vertex1 > vertices.length) {
            throw new Exception("vertex 1 out of bounds");
        }
        if (vertex2 < 0 || vertex2 > vertices[vertex1].length) {
            throw new Exception("vertex 2 out of bounds");
        }
        vertices[vertex1][vertex2] = 1;
        vertices[vertex2][vertex1] = 1;
    }

    public void removePath(int vertex1, int vertex2) throws Exception {
        if (vertex1 < 0 || vertex1 > vertices.length) {
            throw new Exception("vertex 1 out of bounds");
        }
        if (vertex2 < 0 || vertex2 > vertices[vertex1].length) {
            throw new Exception("vertex 2 out of bounds");
        }
        vertices[vertex1][vertex2] = 0;
        vertices[vertex2][vertex1] = 0;
    }

    public int getVertexCount() {
        return vertices.length;
    }

    public int[] getVertex(int idx) {
        return vertices[idx];
    }

    // TODO: Hack, remove this!
    public int[][] getGraphMatrix() {
        return vertices;
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

    @Override
    public String toString() {
        String s = "";
        int spacing = String.valueOf(vertices.length).length();
        s += "Graph[" + vertices.length + "]:\n";
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
