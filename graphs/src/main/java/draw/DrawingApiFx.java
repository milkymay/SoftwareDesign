package draw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingApiFx extends DrawingApi {
    private final GraphicsContext gc;

    public DrawingApiFx(GraphicsContext gc, int width, int height) {
        super(width, height);
        this.gc = gc;
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        gc.setFill(Color.PINK);
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        gc.setFill(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
    }
}
