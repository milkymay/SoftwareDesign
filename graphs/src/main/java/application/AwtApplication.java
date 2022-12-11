package application;

import draw.DrawingApiAwt;
import graph.Graph;
import graph.GraphTypeHolder;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class AwtApplication implements GraphApplication {
    private static final int width = 600;
    private static final int height = 600;
    private List<Line2D> edges;
    private List<Ellipse2D> circles;

    @Override
    public void startApplication() {
        DrawingApiAwt drawingApi = new DrawingApiAwt(width, height);
        Graph graph = GraphTypeHolder.createGraph(drawingApi);
        graph.getGraph();
        graph.drawGraph();
        edges = drawingApi.getEdges();
        circles = drawingApi.getCircles();
        Frame frame = new AwtFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    class AwtFrame extends Frame {
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.black);
            for (Line2D edge : edges) {
                g2d.draw(edge);
            }
            g2d.setPaint(Color.pink);
            for (Ellipse2D circle : circles) {
                g2d.fill(circle);
            }
        }
    }
}