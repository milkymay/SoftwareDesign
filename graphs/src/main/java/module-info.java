module idk.graphs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens graph to javafx.fxml;
    exports graph;
    exports draw;
    opens draw to javafx.fxml;
}