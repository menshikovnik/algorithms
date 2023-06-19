package Solve.logic.graph;

import java.util.Arrays;
import java.util.Iterator;

public class AdjMatrixGraph implements Graph {

    private boolean[][] adjMatrix;
    private boolean[][] edgeAvailability;
    private boolean[] vertexAvailability;
    private int vCount;
    private int eCount = 0;

    public AdjMatrixGraph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        edgeAvailability = new boolean[vertexCount][vertexCount];
        vertexAvailability = new boolean[vertexCount];
        vCount = vertexCount;
    }

    public AdjMatrixGraph() {
        this(0);
    }

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    @Override
    public void addEdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            edgeAvailability = Arrays.copyOf(edgeAvailability, maxV + 1);
            vertexAvailability = Arrays.copyOf(vertexAvailability, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new boolean[maxV + 1];
                edgeAvailability[i] = i < vCount ? Arrays.copyOf(edgeAvailability[i], maxV + 1) : new boolean[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            edgeAvailability[v1][v2] = true;
            eCount++;
            // для наследников
            if (!(this instanceof Digraph)) {
                adjMatrix[v2][v1] = true;
                edgeAvailability[v2][v1] = true;
            }
        }
        vertexAvailability[v1] = true;
        vertexAvailability[v2] = true;
    }

    @Override
    public boolean[][] getBooleanAdjMatrix() {
        boolean[][] copy = new boolean[vertexCount()][];
        for (int i = 0; i < vertexCount(); i++) {
            copy[i] = Arrays.copyOf(adjMatrix[i], vertexCount());
        }
        return copy;
    }



    @Override
    public void removeEdge(int v1, int v2) {
        if (adjMatrix[v1][v2] && edgeAvailability[v1][v2]) {
            edgeAvailability[v1][v2] = false;
            eCount--;
            // для наследников
            if (!(this instanceof Digraph)) {
                edgeAvailability[v2][v1] = false;
            }
        }
    }

    @Override
    public Iterable<Integer> adjacency(int v) {
        return new Iterable<>() {
            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {
                for (int i = 0; i < vCount; i++) {
                    if (edgeAvailability[v][i]) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vCount; i++) {
                            if (edgeAvailability[v][i]) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    @Override
    public boolean isAdj(int v1, int v2) {
        return adjMatrix[v1][v2];
    }

    public void restoreEdge(int v1, int v2) {
        if (adjMatrix[v1][v2] && !edgeAvailability[v1][v2]) {
            edgeAvailability[v1][v2] = true;
            eCount++;
            // для наследников
            if (!(this instanceof Digraph)) {
                edgeAvailability[v2][v1] = true;
            }
        }
        vertexAvailability[v1] = true;
        vertexAvailability[v2] = true;
    }

    public void removeVertex(int v) {
        if (v < 0 || v >= vertexCount()) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        // Удалить ребра, связанные с вершиной v
        for (int i = 0; i < vertexCount(); i++) {
            if (edgeAvailability[v][i]) {
                removeEdge(v, i);
            }
            if (edgeAvailability[i][v]) {
                removeEdge(i, v);
            }
        }

        vertexAvailability[v] = false;
    }

    public void restoreVertex(int v) {
        if (v < 0 || v >= vertexCount()) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        vertexAvailability[v] = true;

        // Восстановить ребра, связанные с вершиной v
        for (int i = 0; i < vertexCount(); i++) {
            if (adjMatrix[v][i] && !edgeAvailability[v][i]) {
                restoreEdge(v, i);
            }
            if (adjMatrix[i][v] && !edgeAvailability[i][v]) {
                restoreEdge(i, v);
            }
        }
    }

    public boolean isVertexAvailable(int v) {
        if (v < 0 || v >= vertexCount()) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        return vertexAvailability[v];
    }
    public boolean isEdgeAvailable(int v1, int v2) {
        return edgeAvailability[v1][v2];
    }
}
