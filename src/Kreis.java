import java.awt.Graphics;
import java.awt.Color;

public class Kreis {

    private final int x, y;
    private final Color color;
    private final String name;
    private boolean terminal = false;

    public Kreis(int x, int y, Color color, String name) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
    }

    public Kreis(int x, int y, Color color, String name, boolean terminal) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
        this.terminal = terminal;
    }


    public void zeichne(Graphics g) {
        g.setColor(color);
        if (terminal) {
            g.drawOval(x-3, y-3, 106, 106);
        }
        g.drawOval(x, y, 100, 100);
        g.drawString(this.name, x+50, y+50);
        // Wenn du einen gefüllten Kreis willst:
        // g.fillOval(x, y, breite, hoehe);
    }
}
