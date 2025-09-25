import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatePanel extends JPanel {

    private List<Kreis> states = new ArrayList<>();
    private HashMap<State, Coordinate> stateCoordinateHashMap = new HashMap<>();
    private List<Color> colorList = new ArrayList<>();
    private List<List<Transition>> transitions = new ArrayList<>();

    public StatePanel(List<State> states, List<Coordinate> coordinates) {
        int counter = 0;
        for (State state : states) {
            int x = coordinates.get(counter).getX();
            int y = coordinates.get(counter).getY();
            stateCoordinateHashMap.put(state, coordinates.get(counter));
            colorList.add(new Color(
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256)));
            this.states.add(new Kreis(x, y, colorList.get(counter), state.name));
            counter++;
        }

        for (State state : states) {
            List<Transition> list = new ArrayList<>();
            Coordinate coordinateStart = stateCoordinateHashMap.get(state);
            HashMap<Input, List<State>> stateTransitions = state.transitions;
            for (Input input : stateTransitions.keySet()) {
                for (State state1 : stateTransitions.get(input)) {
                    Coordinate coordinateZiel = stateCoordinateHashMap.get(state1);
                    list.add(new Transition(coordinateStart, coordinateZiel, input.iToString()));
                }
            }
            transitions.add(list);
        }

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Kreis k : states) {
            k.zeichne(g);
        }
        for (int i = 0; i < states.size(); i++) {
            g.setColor(colorList.get(i));
            for (Transition t : transitions.get(i)) {
                t.drawArrow(g);
            }
        }



    }
}
