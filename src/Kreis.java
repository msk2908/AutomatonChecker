import java.awt.Graphics;
import java.awt.Color;

public class Kreis {

    private int x, y;
    private Color farbe;
    private String name;

    public Kreis(int x, int y, Color farbe, String name) {
        this.x = x;
        this.y = y;
        this.farbe = farbe;
        this.name = name;
    }

    public void zeichne(Graphics g) {
        g.setColor(farbe);
        g.drawOval(x, y, 100, 100);
        g.drawString(this.name, x+50, y+50);
        // Wenn du einen gefüllten Kreis willst:
        // g.fillOval(x, y, breite, hoehe);
    }
}
