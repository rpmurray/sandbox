package info.masterfrog.sandbox.hamiltoniancycle.util;

import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;

import java.util.Arrays;

public class HamiltonianCycleUtil {
    private int vertexCount, pathCount;
    private int[] path;
    private int[][] graph;

    public boolean findHamiltonianCycle(Graph g) {
        vertexCount = g.getVertexCount();
        path = new int[vertexCount];

        Arrays.fill(path, -1);
        graph = g.getGraphMatrixClone();
        path[0] = 0;
        pathCount = 1;
        boolean hasSolution = solve(0);

        return hasSolution;
    }

    private boolean solve(int vertex) {
        // setup
        boolean hasSolution = false;

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
                    hasSolution = solve(v);

                    if (hasSolution) {
                        return true;
                    }
                }

                /** restore connection **/
                graph[vertex][v] = 1;
                graph[v][vertex] = 1;
                /** remove path **/
                path[--pathCount] = -1;
            }
        }

        return hasSolution;
    }

    private boolean isPresent(int v) {
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
                s += (path[i % vertexCount] + 1) + " ";
            }
        }

        return s;
    }
}
