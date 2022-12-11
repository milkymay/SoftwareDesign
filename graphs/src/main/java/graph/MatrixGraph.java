package graph;

import draw.DrawingApi;

import java.util.Scanner;

public class MatrixGraph extends Graph {
    private int[][] matrix;
    private int n;

    public MatrixGraph(DrawingApi api) {
        super(api);
    }

    @Override
    public void getGraph() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter vertex quantity: ");
        n = in.nextInt();
        matrix = new int[n][n];
        System.out.println("Enter the matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
    }

    @Override
    public void drawGraph() {
        setCorner(n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] != 0) {
                    drawEdge(i, j);
                }
            }
        }
    }
}