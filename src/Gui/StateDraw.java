package Gui;

import Drawing.Coordinate;

import java.awt.*;

public class StateDraw {
    private static final int RADIUS = 30;
    String name;
    int x, y;
    boolean isStart, isFinal;

    public StateDraw(String name, int x, int y, boolean isStart, boolean isFinal) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isStart = isStart;
        this.isFinal = isFinal;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        g.drawString(name, x - 10, y + 5);

        if (isFinal) {
            g.drawOval(x - RADIUS + 5, y - RADIUS + 5, 2 * (RADIUS - 5), 2 * (RADIUS - 5));
        }

        if (isStart) {
            g.drawLine(x - RADIUS - 20, y, x - RADIUS, y);
            g.drawLine(x - RADIUS - 10, y - 5, x - RADIUS, y);
            g.drawLine(x - RADIUS - 10, y + 5, x - RADIUS, y);
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinal() {
        return isFinal;
    }

    //TODO irgendwie unnötig
    public Coordinate getCoordinate() {
        return new Coordinate(x,y);
    }

    public boolean contains(Point p) {
        return (Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2)) <= Math.pow(RADIUS, 2);
    }
}
