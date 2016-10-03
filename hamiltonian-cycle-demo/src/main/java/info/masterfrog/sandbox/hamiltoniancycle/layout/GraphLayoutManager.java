package info.masterfrog.sandbox.hamiltoniancycle.layout;

import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GraphLayoutManager {
    private static GraphLayoutManager instance;

    private GraphLayoutManager() {
        // do nothing
    }

    public static GraphLayoutManager getInstance() {
        if (instance == null) {
            instance = new GraphLayoutManager();
        }

        return instance;
    }

    public void generateRandomLayout(Graph g, Rectangle bounds) {
        // setup
        Set<Point> points = new HashSet<>();
        int x;
        int y;

        // generate layout one vertex at a time
        for (int i = 0; i < g.getVertexCount(); i++) {
            // try to generate a new random position for vertex i
            do {
                x = (int) Math.round(Math.random() * bounds.width);
                y = (int) Math.round(Math.random() * bounds.height);
            } while (points.contains(new Point(x, y)));

            Point p = new Point(x, y);
            g.setVertexLayout(i, p);
            points.add(p);
        }
    }
}
