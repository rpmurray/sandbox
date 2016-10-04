package info.masterfrog.sandbox.hamiltoniancycle.main;

import info.masterfrog.sandbox.hamiltoniancycle.render.RenderingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class GraphicsPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private JFrame frame; // jframe to put the graphics into
    private List<RenderingEngine> renderingEngines;

    public GraphicsPanel(Rectangle frameSize, List<RenderingEngine> renderingEngines) {
        // handle parent
        super();

        // handle local
        this.renderingEngines = renderingEngines;

        // init
        init(frameSize);
    }

    private void init(Rectangle frameSize) {
        initScreen();
        initListeners();
        initFrame(frameSize);
    }

    private void initScreen() {
        this.setFocusable(true);
    }

    private void initListeners() {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    private void initFrame(Rectangle frameSize) {
        // init frame
        frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(100, 50);
        frame.setSize(frameSize.height, frameSize.width);
        frame.setVisible(true);
        frame.setContentPane(this);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void paintComponent(Graphics graphics) {
        // setup
        Graphics2D graphics2D = (Graphics2D) graphics;
        Rectangle bounds = graphics2D.getClipBounds();

        // clear bounds
        graphics2D.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // render frame
        for (RenderingEngine renderingEngine : renderingEngines) {
            renderingEngine.draw((Graphics2D) graphics);
        }
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
    }

    public void keyReleased(KeyEvent event) {
        // do nothing
    }

    public void keyTyped(KeyEvent event) {
        // do nothing
    }

    public void mouseExited(MouseEvent event) {
        // do nothing
    }

    public void mouseEntered(MouseEvent event) {
        // do nothing
    }

    public void mouseMoved(MouseEvent event) {
        // do nothing
    }

    public void mousePressed(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // do nothing
    }
}
