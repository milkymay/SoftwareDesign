import application.AwtApplication;
import application.FxApplication;
import application.GraphApplication;
import graph.GraphTypeHolder;

import static graph.GraphTypeHolder.GraphType.LIST;
import static graph.GraphTypeHolder.GraphType.MATRIX;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected 2 args");
        }

        GraphApplication graphApplication = switch (args[0]) {
            case "fx" -> new FxApplication();
            case "awt" -> new AwtApplication();
            default -> throw new IllegalArgumentException("\"fx\" or \"awt\" must be 1st arg");
        };

        GraphTypeHolder.graph = switch (args[1]) {
            case "matrix" -> MATRIX;
            case "list" -> LIST;
            default -> throw new IllegalArgumentException("\"matrix\" or \"list\" must be 2nd arg");
        };

        graphApplication.startApplication();
    }
}