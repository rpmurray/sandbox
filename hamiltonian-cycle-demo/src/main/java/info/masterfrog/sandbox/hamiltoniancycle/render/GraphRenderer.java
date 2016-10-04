package info.masterfrog.sandbox.hamiltoniancycle.render;

import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;
import info.masterfrog.sandbox.hamiltoniancycle.model.Pair;

import java.awt.*;

public class GraphRenderer {
    private Graph graph;
    private int edgeWidth;
    private int vertexDiameter;

    public GraphRenderer(int edgeWidth, int vertexDiameter) {
        this(new Graph(0), edgeWidth, vertexDiameter);
    }

    public GraphRenderer(Graph graph, int edgeWidth, int vertexDiameter) {
        this.graph = graph;
        this.edgeWidth = edgeWidth;
        this.vertexDiameter = vertexDiameter;
    }

    public void setEdgeWidth(int edgeWidth) {
        this.edgeWidth = edgeWidth;
    }

    public void setVertexDiameter(int vertexDiameter) {
        this.vertexDiameter = vertexDiameter;
    }

    public void draw(Graphics2D graphics2D) {
        // get bounds
        Rectangle bounds = graphics2D.getClipBounds();

        // clear bounds
        graphics2D.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // render edges
        drawEdges(graphics2D);

        // render vertices
        drawVertices(graphics2D);
    }

    private void drawVertices(Graphics2D graphics2D) {
        // render each vertex
        for (int v = 0; v < graph.getVertexCount(); v++) {
            // setup
            Point p = graph.getVertexLayout(v);
            int x = p.x - (vertexDiameter / 2);
            int y = p.y - (vertexDiameter / 2);
            int w = vertexDiameter;
            int h = vertexDiameter;

            // draw
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillOval(x, y, w, h);
        }
    }

    private void drawEdges(Graphics2D graphics2D) {
        // render each edge
        for (Pair<Integer> e : graph.getEdges()) {
            // setup
            int v1 = e.getX();
            int v2 = e.getY();
            Point p1 = graph.getVertexLayout(v1);
            Point p2 = graph.getVertexLayout(v2);

            // determine relative positions and adjust assignments so p1.x <= p2.x per typical orthogonal graphing
            if (p1.x > p2.x) {
                Point tmp = p1;
                p1 = p2;
                p2 = tmp;
            }

            // calculate renderable line segment for edge
            int xDiff = p1.x - p2.x;
            int yDiff = p1.y - p2.y;
            double slope = xDiff == 0 ? -1 : yDiff / xDiff;
            double length = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
            int paddingScale = (int) (vertexDiameter * 1.5);
            int x1 = xDiff == 0 ? p1.x : p1.x + paddingScale;
            int y1 = p1.y + (int) Math.floor(slope * paddingScale);
            int x2 = xDiff == 0 ? p2.x : p2.x - paddingScale;
            int y2 = p2.y - (int) Math.floor(slope * paddingScale);

            // set up color, stroke, etc.
            graphics2D.setColor(
                new Color(
                    (float) Math.random() * 0.25f + 0.25f,
                    (float) Math.random() * 0.25f + 0.25f,
                    (float) Math.random() * 0.25f + 0.25f,
                    1.0f
                )
            );
            graphics2D.setStroke(new BasicStroke(edgeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // draw
            graphics2D.drawLine(x1, y1, x2, y2);
        }
    }

    public void setGraph(Graph g) {
        this.graph = g;
    }
}
