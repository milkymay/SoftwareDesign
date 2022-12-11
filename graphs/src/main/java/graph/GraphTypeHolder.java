package graph;

import draw.DrawingApi;

public class GraphTypeHolder {
    public static GraphType graph;

    public static Graph createGraph(DrawingApi drawingApi) {
        return switch (graph) {
            case LIST -> new ListGraph(drawingApi);
            case MATRIX -> new MatrixGraph(drawingApi);
        };
    }

    public enum GraphType {
        MATRIX, LIST
    }
}
