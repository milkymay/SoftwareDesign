package application;

import draw.DrawingApi;
import draw.DrawingApiFx;
import graph.Graph;
import graph.GraphTypeHolder;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxApplication extends Application implements GraphApplication {
    private static final int width = 600;
    private static final int height = 600;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graph");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DrawingApi drawingApi = new DrawingApiFx(gc, width, height);
        Graph graph = GraphTypeHolder.createGraph(drawingApi);
        graph.getGraph();
        graph.drawGraph();
        Group root = new Group();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, Color.WHITE));
        primaryStage.show();
    }

    @Override
    public void startApplication() {
        Application.launch(this.getClass());
    }
}
