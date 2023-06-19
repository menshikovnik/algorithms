package Solve.graphics.additional;

import java.awt.*;

public class Edge {
        private final Vertex start;
        private final Vertex end;
        private Color color;

    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
        this.color = Color.black;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge edge)) return false;

        return (start.equals(edge.start) && end.equals(edge.end)) ||
                (start.equals(edge.end) && end.equals(edge.start));
    }
}
