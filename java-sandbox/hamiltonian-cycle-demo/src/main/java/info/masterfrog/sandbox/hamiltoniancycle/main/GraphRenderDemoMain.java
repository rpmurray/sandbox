package info.masterfrog.sandbox.hamiltoniancycle.main;

/**
 **   Java Program to Implement Hamiltonian Cycle Algorithm
 **/

import info.masterfrog.sandbox.hamiltoniancycle.layout.GraphLayoutManager;
import info.masterfrog.sandbox.hamiltoniancycle.model.Graph;
import info.masterfrog.sandbox.hamiltoniancycle.render.GraphRenderingEngine;
import info.masterfrog.sandbox.hamiltoniancycle.render.RenderingEngine;
import info.masterfrog.sandbox.hamiltoniancycle.render.TextRenderingEngine;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphRenderDemoMain {
    private GraphicsPanel graphicsPanel;
    private GraphRenderingEngine graphRenderingEngine;
    private TextRenderingEngine textRenderingEngine;
    private long timer;

    /*** main ***/

    public static void main(String[] args) {
        try {
            // init
            GraphRenderDemoMain demoMain = new GraphRenderDemoMain();

            // execute
            demoMain.graphRenderTest();
        } catch (Exception e) {
            System.out.println("FATAL ERROR!\n" + e + "\n" + Arrays.asList(e.getStackTrace()));
        }
    }

    /*** main end ***/


    /*** constructor ***/

    public GraphRenderDemoMain() throws Exception {
        this.graphRenderingEngine = new GraphRenderingEngine(5, 20);
        this.textRenderingEngine = new TextRenderingEngine();
        List<RenderingEngine> renderingEngines = new ArrayList<>();
        renderingEngines.add(graphRenderingEngine);
        renderingEngines.add(textRenderingEngine);
        this.graphicsPanel = new GraphicsPanel(new Rectangle(800, 800), renderingEngines);
    }

    /*** constructor end ***/


    /*** util ***/

    private void setTimer() {
        timer = System.nanoTime();
    }

    private double getTimerElapsed() {
        return (System.nanoTime() - timer) / 1000000.0;
    }

    private String displayExecutionTime() {
        return "Execution time: " + String.format("%,.1f", getTimerElapsed()) + "ms";
    }

    /*** util end ***/


    /*** graph render test ***/

    private void graphRenderTest() throws Exception {
        // setup
        setTimer();

        // generate graph
        Graph g = Graph.generateByPathWalk(8, Math.random()/*0.5*/, -1, -1);

        GraphLayoutManager.getInstance().generateRandomGridLayout(g, graphicsPanel.getBounds());
        graphRenderingEngine.setGraph(g);
        textRenderingEngine.setText(displayExecutionTime());
        textRenderingEngine.setPosition(new Point(20, 20));

        // draw
        graphicsPanel.getFrame().repaint();
    }

    /*** graph render test end ***/
}
