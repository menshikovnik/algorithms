package Solve.logic;

import Solve.logic.graph.AdjMatrixGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Logic {
    public static ArrayList<Integer> findVertices(AdjMatrixGraph graph, int N, int startVertex) {
        int vertices = graph.vertexCount();
        ArrayList<Integer> verticesNotSatisfyingCondition = new ArrayList<>();
        if (!graph.isVertexAvailable(startVertex)) {
            throw new IllegalArgumentException("Invalid start vertex");
        }

        boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);
        bfs(startVertex, visited, vertices, N + 1, graph); // Увеличиваем глубину на 1, так как стартовая вершина также будет учитываться

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (!visited[vertex] && graph.isVertexAvailable(vertex)) {
                verticesNotSatisfyingCondition.add(vertex);
            }
        }

        return verticesNotSatisfyingCondition;
    }

    public static boolean isGraphConnected(AdjMatrixGraph graph, int depth) {
        int vertices = graph.vertexCount();
        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            if (graph.isVertexAvailable(i)) {
                Arrays.fill(visited, false); // Сбрасываем флаги посещенных вершин перед каждым обходом
                bfs(i, visited, vertices, depth, graph);

                // Проверяем, все ли вершины были посещены
                for (int k = 0; k < visited.length; k++) {
                    if (!visited[k] && graph.isVertexAvailable(k)) {
                        return false; // Если есть непосещенные вершины, граф не связный
                    }
                }

            }
        }

        return true; // Если все вершины были посещены в каждом обходе, граф связный
    }


    private static void bfs(int startVertex, boolean[] visited, int vertices, int depth, AdjMatrixGraph graph) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startVertex);
        visited[startVertex] = true;
        int[] levels = new int[vertices];
        Arrays.fill(levels, Integer.MAX_VALUE);
        levels[startVertex] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            int currentLevel = levels[currentVertex];

            if (currentLevel >= depth) {
                continue; // Превышена максимальная глубина
            }

            for (int neighbor = 0; neighbor < vertices; neighbor++) {
                if (graph.isEdgeAvailable(currentVertex, neighbor) && !visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    levels[neighbor] = Math.min(levels[neighbor], currentLevel + 1);
                }
            }
        }
        for (int i = 0; i < vertices; i++) {
            if (levels[i] > depth - 1) {
                visited[i] = false; // Сбрасываем флаг посещения вершины
            }
        }
    }
}

