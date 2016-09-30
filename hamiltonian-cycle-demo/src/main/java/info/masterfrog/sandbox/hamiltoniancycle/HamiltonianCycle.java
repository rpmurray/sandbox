package info.masterfrog.sandbox.hamiltoniancycle;

import java.util.Arrays;

public class HamiltonianCycle {
    private int vertexCount, pathCount;
    private int[] path;
    private int[][] graph;

    /**
     * Function to find cycle
     **/
    public boolean findHamiltonianCycle(Graph g) {
        vertexCount = g.getVertexCount();
        path = new int[vertexCount];

        Arrays.fill(path, -1);
        graph = g.getGraphMatrix();
        path[0] = 0;
        pathCount = 1;
        boolean hasSolution = solve(0);

        return hasSolution;
    }

    /**
     * function to find paths recursively
     **/
    public boolean solve(int vertex) {
        /** solution **/
        if (graph[vertex][0] == 1 && pathCount == vertexCount) {
            return true;
        }
        /** all vertices selected but last vertex not linked to 0 **/
        if (pathCount == vertexCount) {
            return false;
        }

        for (int v = 0; v < this.vertexCount; v++) {
            /** if connected **/
            if (graph[vertex][v] == 1) {
                /** add to path **/
                path[pathCount++] = v;
                /** remove connection **/
                graph[vertex][v] = 0;
                graph[v][vertex] = 0;

                /** if vertex not already selected  solve recursively **/
                if (!isPresent(v)) {
                    solve(v);
                }

                /** restore connection **/
                graph[vertex][v] = 1;
                graph[v][vertex] = 1;
                /** remove path **/
                path[--pathCount] = -1;
            }
        }

        return false;
    }

    /**
     * function to check if path is already selected
     **/
    public boolean isPresent(int v) {
        for (int i = 0; i < pathCount - 1; i++)
            if (path[i] == v)
                return true;
        return false;
    }

    public String toString() {
        String s = "Hamiltonian Cycle: ";
        if (path.length < vertexCount) {
            s += "None";
        } else {
            for (int i = 0; i <= vertexCount; i++) {
                s += path[i] + " ";
            }
        }

        return s;
    }
}
