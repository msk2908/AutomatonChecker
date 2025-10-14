package evtlSpaeterNutzbar;

import java.awt.*;

class Transition {
    StateDraw from, to;
    String symbol;

    public Transition(StateDraw from, StateDraw to, String symbol) {
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        int x1 = from.x;
        int y1 = from.y;
        int x2 = to.x;
        int y2 = to.y;

        if (from == to) {
            // RegExClasses.Loop
            g.drawArc(x1 - 30, y1 - 50, 60, 40, 0, 360);
            g.drawString(symbol, x1, y1 - 60);
        } else {
            g.drawLine(x1, y1, x2, y2);
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g.drawString(symbol, midX + 5, midY - 5);

            // Arrowhead
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowSize = 8;
            int dx = (int) (arrowSize * Math.cos(angle - Math.PI / 6));
            int dy = (int) (arrowSize * Math.sin(angle - Math.PI / 6));
            g.drawLine(x2, y2, x2 - dx, y2 - dy);
            dx = (int) (arrowSize * Math.cos(angle + Math.PI / 6));
            dy = (int) (arrowSize * Math.sin(angle + Math.PI / 6));
            g.drawLine(x2, y2, x2 - dx, y2 - dy);
        }
    }

    public StateDraw getFrom() {
        return from;
    }

    public StateDraw getTo() {
        return to;
    }

    public String getLabel() {
        return symbol;
    }
}
