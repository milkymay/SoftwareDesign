package draw;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingApiAwt extends DrawingApi {
    private final List<Line2D> edges;
    private final List<Ellipse2D> circles;

    public DrawingApiAwt(int width, int height) {
        super(width, height);
        this.edges = new ArrayList<>();
        this.circles = new ArrayList<>();
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        circles.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        edges.add(new Line2D.Double(x1, y1, x2, y2));
    }

    public List<Line2D> getEdges() {
        return edges;
    }

    public List<Ellipse2D> getCircles() {
        return circles;
    }
}
