package graph;

import draw.DrawingApi;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public abstract class Graph {
    private final DrawingApi drawingApi;
    private final Map<Integer, Point2D> circlesCoordinates = new HashMap<>();
    private final double graphRadius;
    private final Point2D graphCenter;
    private double corner;
    private static double vertexRadius;


    public Graph(DrawingApi api) {
        this.drawingApi = api;
        long width = drawingApi.getDrawingAreaWidth();
        long height = drawingApi.getDrawingAreaHeight();

        graphRadius = ((double) Math.min(width, height)) / 2 * 0.85;
        graphCenter = new Point2D.Double(width / 2.0, height / 2.0);
        vertexRadius = graphRadius / 20;
    }

    public abstract void getGraph();

    public abstract void drawGraph();

    protected void setCorner(int countVertex) {
        corner = 2 * Math.PI / countVertex;
    }

    private void drawVertex(int v) {
        if (!circlesCoordinates.containsKey(v)) {
            int drawnVertexesCnt = circlesCoordinates.size();
            double x = graphCenter.getX() + graphRadius * Math.cos(corner * drawnVertexesCnt);
            double y = graphCenter.getY() + graphRadius * Math.sin(corner * drawnVertexesCnt);
            circlesCoordinates.put(v, new Point2D.Double(x, y));
            drawingApi.drawCircle(x, y, vertexRadius);
        }
    }

    private void connectVertexes(int v, int u) {
        Point2D from = circlesCoordinates.get(v);
        Point2D to = circlesCoordinates.get(u);
        drawingApi.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
    }

    protected void drawEdge(int v, int u) {
        drawVertex(v);
        drawVertex(u);
        connectVertexes(v, u);
    }
}
