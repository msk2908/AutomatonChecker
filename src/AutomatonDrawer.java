import javax.swing.JFrame;
import java.util.List;
import Drawing.Coordinate;


public class AutomatonDrawer {
    private final List<State> states;
    private final List<Coordinate> coordinates;
    public AutomatonDrawer(List<State> states, List<Coordinate> coordinates) {
        this.states = states;
        this.coordinates = coordinates;
    }

    public JFrame paintFrame() {
        JFrame drawingPanel = new JFrame("Automaton");
        drawingPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawingPanel.setSize(400, 400);

        drawingPanel.setVisible(true);
        return drawingPanel;
    }

    public void paint(JFrame drawingPanel) {
        // Panel mit Drawing.Kreis hinzufügen
        StatePanel panel = new StatePanel(states, coordinates);
        drawingPanel.add(panel);

    }
}
