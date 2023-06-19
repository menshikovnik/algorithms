package Solve.graphics;

import Solve.graphics.additional.Edge;
import Solve.graphics.additional.ManipulateModes;
import Solve.graphics.additional.ObjectModes;
import Solve.graphics.additional.Vertex;
import Solve.logic.graph.AdjMatrixGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Canvas extends JComponent {
    private Graphics2D g;
    private Image img;

    private final int vertexSize = 60;

    private final ArrayList<Vertex> graphicalVertices = new ArrayList<>(); //Массив вершин
    private final ArrayList<Edge> graphicalEdge = new ArrayList<>();

    private Vertex[] linesBuffer = new Vertex[2]; //Хранит информацию о точках линии во время её создания

    private AdjMatrixGraph graph = new AdjMatrixGraph(); //Матрица смежности вершин

    private ObjectModes objectMode = ObjectModes.VERTEX; //Текущий режим (вершины или линии)
    private ManipulateModes manipulateMode = ManipulateModes.ADD;

    private int currId = 0;
    private int weight = 0; //Текущий вес ребра, задаётся извне


    public Canvas() {
        createMouseListener();
    }

    public void changeObjectMode(ObjectModes newMode) {
        objectMode = newMode;
    }

    public void changeManipulateMode(ManipulateModes newMode) {
        manipulateMode = newMode;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }

    public AdjMatrixGraph getGraph() {
        return graph;
    }

    private void createMouseListener() {
        setDoubleBuffered(false);

        MouseListener listener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point clickPoint = new Point(e.getX(), e.getY());
                handleClick(clickPoint);
            }
        };

        addMouseListener(listener);
    }

    private Vertex checkVertexCollision(Point clicked) {
        int offsetRectSize = vertexSize * 2;

        for (Vertex vertex : graphicalVertices) {
            Ellipse2D r = new Ellipse2D.Float(vertex.getPos().x - vertexSize, vertex.getPos().y - vertexSize,
                    offsetRectSize, offsetRectSize);

            if (r.contains(clicked))
                return vertex;
        }
        return null;
    }

    private Edge checkResistorCollision(Point clicked) {
        for (Edge resistor : graphicalEdge) {
            Line2D.Float line = new Line2D.Float(resistor.getStart().getPos(), resistor.getEnd().getPos());
            if (line.getBounds().contains(clicked))
                return resistor;
        }
        return null;
    }

    private void handleClick(Point clickPoint) {
        clearColors(); //Для удаления фокуса

        if (manipulateMode == ManipulateModes.ADD)
            handleAddModeClick(clickPoint);
        else
            handleDelModeClick(clickPoint);
    }

    private void handleAddModeClick(Point clickPoint) {
        Vertex v = checkVertexCollision(clickPoint);

        if (objectMode == ObjectModes.VERTEX) { //Режим вершин
            if (v != null) //Запрещаем рисовать вершины на существующих вершинах.
                return;

            createVertex(clickPoint);
        } else { //Режим линий
            if (v == null) //Запрещаем создавать линии там, где нет вершин
                return;

            if (linesBuffer[0] == null || linesBuffer[0].equals(v)) {
                linesBuffer[0] = v; //Записывает первую точку линии в буфер
                v.setColor(Color.GRAY); // фокус
            } else {
                linesBuffer[1] = v;

                createEdge(linesBuffer[0], linesBuffer[1]);

                linesBuffer = new Vertex[2];
            }

            redraw();
        }
    }

    private void handleDelModeClick(Point clickPoint) {
        if (objectMode == ObjectModes.VERTEX) { //Режим вершин
            Vertex vertexToRemove = checkVertexCollision(clickPoint);

            if (vertexToRemove == null) //Запрещаем пытаться удалять несуществующие вершины.
                return;

            removeVertex(vertexToRemove);
        } else {
            Edge resistorToRemove = checkResistorCollision(clickPoint);

            if (resistorToRemove == null) //Запрещаем пытаться удалять несуществующие резисторы
                return;

            removeEdge(resistorToRemove);
        }
    }

    //Записывает вершину в список и рисует её
    private void createVertex(Point p) {
        Vertex vertex = new Vertex(p, currId++);
        graphicalVertices.add(vertex);
        redraw();
    }

    private void removeVertex(Vertex vertexToRemove) {
        Iterable<Integer> adj = graph.adjacency(vertexToRemove.getId());
        for (Integer vertex : adj) {
            graph.removeEdge(vertex, vertexToRemove.getId());
        }

        graphicalVertices.remove(vertexToRemove);

        for (int i = 0; i < graphicalEdge.size(); i++) {
            Edge resistor = graphicalEdge.get(i);

            if (resistor.getStart() == vertexToRemove || resistor.getEnd() == vertexToRemove) {
                removeEdge(resistor);
                i--;
            }
        }

        redraw();
    }

    //Рисует вершину(без записи в список)
    private void drawVertex(Vertex v) {
        int halfSize = vertexSize / 2;
        int drawX = v.getPos().x - halfSize;
        int drawY = v.getPos().y - halfSize;

        g.setStroke(new BasicStroke(4));

        g.setColor(Color.WHITE);
        g.fillOval(drawX, drawY, vertexSize, vertexSize);
        g.setColor(v.getColor());
        g.drawOval(drawX, drawY, vertexSize, vertexSize);

        String strId = String.valueOf(v.getId());
        Rectangle2D stringBounds = g.getFontMetrics(g.getFont()).getStringBounds(strId, g);

        g.setColor(Color.BLACK);
        g.drawString(strId, v.getPos().x - (int) stringBounds.getCenterX(), v.getPos().y - (int) stringBounds.getCenterY());
    }

    //Рисует резистор между двумя точками
    private void createEdge(Vertex start, Vertex end) {
        Edge edge = new Edge(start, end);

        if (graphicalEdge.contains(edge))
            return;

        graphicalEdge.add(edge);
        graph.addEdge(start.getId(), end.getId());

        redraw();
    }

    private void removeEdge(Edge EdgeToRemove) {
        graph.removeEdge(EdgeToRemove.getStart().getId(), EdgeToRemove.getEnd().getId());
        graphicalEdge.remove(EdgeToRemove);

        redraw();
    }

    private void drawEdge(Edge edge) {
        Line2D.Float line = new Line2D.Float(edge.getStart().getPos(), edge.getEnd().getPos());

        //Рисуем линию
        g.setColor(edge.getColor());
        g.draw(line);

        Rectangle bounds = line.getBounds();
        int centerX = (int) bounds.getCenterX();
        int centerY = (int) bounds.getCenterY();

        //Вычисляем поворот резистора
        double theta = Math.atan2(edge.getEnd().getPos().y - edge.getStart().getPos().y,
                edge.getEnd().getPos().x - edge.getStart().getPos().x);
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(theta, centerX, centerY);

    }

    private void redraw() {
        clear();
        redrawEdges();
        redrawVertices();

        if (g != null)
            repaint();
    }

    private void clearColors() {
        graphicalVertices.forEach(o -> o.setColor(Color.black));
        graphicalEdge.forEach(o -> o.setColor(Color.black));
    }

    private void redrawVertices() {
        for (Vertex v : graphicalVertices)
            drawVertex(v);
    }

    private void redrawEdges() {
        for (Edge resistor : graphicalEdge)
            drawEdge(resistor);
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g);

        if (img == null) {
            img = createImage(getSize().width, getSize().height);

            g = (Graphics2D) img.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Font font = g.getFont().deriveFont(Font.PLAIN, 24);
            g.setFont(font);

            clear();
        }

        g1.drawImage(img, 0, 0, null);
    }

    public void clear() {
        g.setPaint(Color.white);
        g.fillRect(0, 0, getSize().width, getSize().height);
        g.setPaint(Color.black);
        repaint();
    }

    //Удаляет
    public void clearAll() {
        currId = 0;
        graph = new AdjMatrixGraph();
        graphicalVertices.clear();
        graphicalEdge.clear();
        clear();
    }
}
