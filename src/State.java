import java.util.*;

public class State {
    final int id;
    final String name;
    Map<Input, List<State>> transitions = new LinkedHashMap<>();
    boolean terminal;
    final boolean starting;

    public State(int id, String name, Map<Input, List<State>> transitions, boolean terminal, boolean starting) {
        this.id = id;
        this.name = name;
        this.transitions = transitions;
        this.starting = starting;
        this.terminal = terminal;
    }

    public void setTransitions(Input input, State state) {
        // creates a transition for the automaton with the given input from alphabet to the given state
        //TODO evtl. bool to check if input is possible? -> not really needed?
        List<State> newList = new ArrayList<>();
        newList.add(state);
        if (transitions.containsKey(input)) {
            transitions.get(input).add(state);
        } else {
            transitions.put(input, newList);
        }
    }

    public void setTerminal(boolean b) {
        this.terminal = b;
    }

    public Map<Input, List<State>> getTransitions() {
        return transitions;
    }

    public List<State> getNextStatesForInput(Input input) {
        //TODO fix this to hand out ALL follow-up states
        return transitions.get(input);
    }

    public String transitionsToString(Alphabet alphabet, int numberOfAllStates) {
        String res = "";
        /*for (Input input : transitions.keySet()) {
            List<State> nextStates = transitions.get(input);
            List<String> stringStates = new ArrayList<>();
            for (State state: nextStates) {
                stringStates.add(state.name);
            }
            res += input.iToString() + ":" + stringStates + "\n";
        }*/
        //this did not change anything, fix the order in some way dude, pls
        for (Input input : alphabet.possibleInputs) {
            List<String> stringStates = new ArrayList<>();
            List<State> nextStates = new ArrayList<>();
            for (Input tInput : transitions.keySet()) {
                if (input.iToString().equals(tInput.iToString())) {
                    nextStates.addAll(transitions.get(tInput));

                }
            }
            if (nextStates != null) {
                nextStates = sortById(nextStates, numberOfAllStates);
                for (State state : nextStates) {
                    stringStates.add(state.name);
                }
            }
            if (!stringStates.isEmpty()) {
                res += input.iToString() + ":" + stringStates + "\n";
            }


        }

        return res;
    }

    private List<State> sortById(List<State> states, int allStates) {
        List<State> result = new ArrayList<>();
        for (int i = 0; i < allStates; i++) {
            for (State state : states) {
                if (state.id == i) {
                    result.add(state);
                    break;
                }
            }
        }
        return result;

    }


}
