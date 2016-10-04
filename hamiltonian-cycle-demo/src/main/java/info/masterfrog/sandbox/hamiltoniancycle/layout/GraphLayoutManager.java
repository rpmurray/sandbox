package info.masterfrog.sandbox.hamiltoniancycle.layout;

import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

            // set vertex layout
            Point p = new Point(x, y);
            g.setVertexLayout(i, p);
            points.add(p);
        }
    }

    public void generateRandomGridLayout(Graph g, Rectangle bounds) {
        // setup
        Map<Integer, Set<Integer>> xGridCoordinates = new HashMap<>();
        Map<Integer, Set<Integer>> yGridCoordinates = new HashMap<>();
        Set<Point> gridCoordinates = new HashSet<>();
        int xGridCoordinate;
        int yGridCoordinate;
        int xTranslationFactor;
        int yTranslationFactor;
        int x;
        int y;

        // generate grid coordinate possibilities
        for (int i = 1; i <= Math.ceil(g.getVertexCount() / 2.0); i++) {
            xGridCoordinates.put(i, new HashSet<>());
            yGridCoordinates.put(i, new HashSet<>());
        }

        // calculate grid to bounds translation factors
        xTranslationFactor = bounds.width / ((int) Math.ceil(g.getVertexCount() / 2.0) + 1);
        yTranslationFactor = bounds.height / ((int) Math.ceil(g.getVertexCount() / 2.0) + 1);

        // generate layout one vertex at a time
        for (int i = 0; i < g.getVertexCount(); i++) {
            // generate a new random set of graph coordinates for vertex i
            do {
                do {
                    xGridCoordinate = (int) Math.ceil(Math.random() * (g.getVertexCount() / 2.0));
                } while (xGridCoordinates.get(xGridCoordinate).size() >= 2);
                do {
                    yGridCoordinate = (int) Math.ceil(Math.random() * (g.getVertexCount() / 2.0));
                } while (yGridCoordinates.get(yGridCoordinate).size() >= 2);
            } while (gridCoordinates.contains(new Point(xGridCoordinate, yGridCoordinate)));

            // record generated grid coordinates
            xGridCoordinates.get(xGridCoordinate).add(i);
            yGridCoordinates.get(yGridCoordinate).add(i);
            gridCoordinates.add(new Point(xGridCoordinate, yGridCoordinate));

            // calculate real coordinates via translation factors
            x = xGridCoordinate * xTranslationFactor;
            y = yGridCoordinate * yTranslationFactor;

            // set vertex layout
            g.setVertexLayout(i, new Point(x, y));
        }
    }
}
