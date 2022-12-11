package graph;

import draw.DrawingApi;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ListGraph extends Graph {
    private int vertexCnt;
    private int[][] edges;

    public ListGraph(DrawingApi api) {
        super(api);
    }

    @Override
    public void getGraph() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter edges quantity: ");
        int edgeCnt = in.nextInt();
        edges = new int[edgeCnt][2];
        Set<Integer> vertexes = new HashSet<>();
        System.out.println("Enter edges: ");
        for (int i = 0; i < edgeCnt; i++) {
            for (int j = 0; j < 2; j++) {
                edges[i][j] = in.nextInt();
                vertexes.add(edges[i][j]);
            }
        }
        vertexCnt = vertexes.size();
    }

    @Override
    public void drawGraph() {
        setCorner(vertexCnt);
        for (int[] edge : edges) {
            drawEdge(edge[0], edge[1]);
        }
    }
}
