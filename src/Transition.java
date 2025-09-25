import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Transition {

    private final int x1, x2, y1, y2;
    private final String name;

    public Transition(Coordinate starting, Coordinate ending, String name) {
        this.x1 = starting.x+100;
        this.x2 = ending.x;
        this.y1 = starting.y+50;
        this.y2 = ending.y+50;
        this.name = name;
    }

    public void drawArrow(Graphics g) {
        // Hauptlinie
        g.drawLine(x1, y1, x2, y2);

        // Richtung berechnen
        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.sqrt(dx * dx + dy * dy);

        // Normierter Vektor (Richtung)
        double ux = dx / len;
        double uy = dy / len;

        // Pfeilspitzen-Länge
        int spitzenLaenge = 15;
        int spitzenWinkel = 30; // Grad

        // Winkel in Radiant
        double angle = Math.toRadians(spitzenWinkel);

        // Links von der Linie
        double leftX = x2 - spitzenLaenge * (ux * Math.cos(angle) + uy * Math.sin(angle));
        double leftY = y2 - spitzenLaenge * (uy * Math.cos(angle) - ux * Math.sin(angle));

        // Rechts von der Linie
        double rightX = x2 - spitzenLaenge * (ux * Math.cos(angle) - uy * Math.sin(angle));
        double rightY = y2 - spitzenLaenge * (uy * Math.cos(angle) + ux * Math.sin(angle));

        // Pfeilspitze zeichnen
        g.drawLine(x2, y2, (int) leftX, (int) leftY);
        g.drawLine(x2, y2, (int) rightX, (int) rightY);

        g.drawString(name, (x1+x2)/2, (y1+y2)/2);
    }
}
