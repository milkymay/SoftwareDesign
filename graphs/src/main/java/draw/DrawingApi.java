package draw;

public abstract class DrawingApi {
    private final int width;
    private final int height;

    protected DrawingApi(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public long getDrawingAreaWidth() {
        return width;
    }

    public long getDrawingAreaHeight() {
        return height;
    }

    public abstract void drawCircle(double x, double y, double r);

    public abstract void drawLine(double x1, double y1, double x2, double y2);
}
