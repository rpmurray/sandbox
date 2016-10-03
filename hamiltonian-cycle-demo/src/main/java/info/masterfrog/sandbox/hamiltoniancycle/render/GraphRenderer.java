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

    public void draw(Graphics2D graphics2D) {
        Rectangle bounds = graphics2D.getClipBounds();
        graphics2D.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);
        drawEdges(graphics2D);
        drawVertices(graphics2D);
    }

    private void drawVertices(Graphics2D graphics2D) {
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
        for (Pair<Integer> e : graph.getEdges()) {
            // setup
            int v1 = e.getX();
            int v2 = e.getY();
            Point p1 = graph.getVertexLayout(v1);
            Point p2 = graph.getVertexLayout(v2);

            // draw
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(edgeWidth));
            graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void setGraph(Graph g) {
        this.graph = g;
    }
}
