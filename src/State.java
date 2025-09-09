import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State {
    final String name;
    HashMap<Input, List<String>> transitions = new HashMap<>();
    final boolean terminal;
    final boolean starting;

    public State(String name, HashMap<Input, List<String>> transitions, boolean terminal, boolean starting) {
        this.name = name;
        this.transitions = transitions;
        this.starting = starting;
        this.terminal = terminal;
    }

    public void setTransitions(Input input, String state) {
        // creates a transition for the automaton with the given input from alphabet to the given state
        //TODO evtl. bool to check if input is possible?
        if (transitions.containsKey(input)) {
            transitions.get(input).add(state);
        } else {
            List<String> states = new ArrayList<>();
            states.add(state);
            transitions.put(input, states);
        }
    }

    public HashMap<Input, List<String>> getTransitions() {
        return transitions;
    }

}
