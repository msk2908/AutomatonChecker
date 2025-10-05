import javax.swing.JFrame;
import java.util.List;


public class AutomatonDrawer {
    private final List<State> states;
    private final List<Coordinate> coordinates;
    public AutomatonDrawer(List<State> states, List<Coordinate> coordinates) {
        this.states = states;
        this.coordinates = coordinates;
    }

    public JFrame paintFrame() {
        JFrame fenster = new JFrame("Automaton");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(400, 400);

        fenster.setVisible(true);
        return fenster;
    }

    public void paint(JFrame fenster) {
        // Panel mit Kreis hinzufügen
        StatePanel panel = new StatePanel(states, coordinates);
        fenster.add(panel);

    }
}
