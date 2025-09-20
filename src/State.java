import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State {
    final String name;
    HashMap<Input, List<State>> transitions = new HashMap<>();
    boolean terminal;
    final boolean starting;

    public State(String name, HashMap<Input, List<State>> transitions, boolean terminal, boolean starting) {
        this.name = name;
        this.transitions = transitions;
        this.starting = starting;
        this.terminal = terminal;
    }

    public void setTransitions(Input input, State state) {
        // creates a transition for the automaton with the given input from alphabet to the given state
        //TODO evtl. bool to check if input is possible? -> not really needed?
        if (transitions.containsKey(input)) {
            transitions.get(input).add(state);
        } else {
            List<State> states = new ArrayList<>();
            states.add(state);
            transitions.put(input, states);
        }
    }

    public void setTerminal(boolean b) {
        this.terminal = b;
    }

    public HashMap<Input, List<State>> getTransitions() {
        return transitions;
    }

    public List<State> getNextStatesForInput(Input input) {
        return transitions.get(input);
    }

    public String transitionsToString() {
        String res = "";
        for (Input input : transitions.keySet()) {
            List<State> nextStates = transitions.get(input);
            List<String> stringStates = new ArrayList<>();
            for (State state: nextStates) {
                stringStates.add(state.name);
            }
            res += input.iToString() + ":" + stringStates + "\n";
        }
        return res;
    }

}
