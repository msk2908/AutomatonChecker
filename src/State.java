import java.util.*;

public class State {
    final int id;
    final String name;
    HashMap<Input, List<State>> transitions = new LinkedHashMap<>();
    boolean terminal;
    boolean starting;

    public State(int id, String name, HashMap<Input, List<State>> transitions, boolean terminal, boolean starting) {
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
        if (transitions.containsKey(input) && !transitions.get(input).contains(state)) {
            transitions.get(input).add(state);
        } else {
            transitions.put(input, newList);
        }
    }

    public void addTransitions(HashMap<Input, List<State>> map) {
        for (Input input: map.keySet()) {
            for (State state: map.get(input)) {
                setTransitions(input, state);
            }
        }
    }

    public void removeTransition(Input input, State state) {
        transitions.get(input).remove(state);
        if (transitions.get(input).isEmpty()) {
            transitions.remove(input);
        }
    }

    public void removeTransition(State state) {
        for (Input input: transitions.keySet()) {
            if (transitions.get(input).contains(state)) {
                removeTransition(input, state);
            }
        }
    }

    public void removeTransition(List<State> states) {
        for (State state : states) {
            removeTransition(state);
        }
    }

    public void setStarting() {
        starting = true;
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

    public String transitionsToString(Alphabet alphabet) {
        String res = "";

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
                nextStates = sortById(nextStates);
                for (State state : nextStates) {
                    if (!stringStates.contains(state.name)) {
                        stringStates.add(state.name);
                    }
                }
            }
            if (!stringStates.isEmpty()) {
                res += input.iToString() + ":" + stringStates + "\n";
            }
        }

        return res;
    }

    private List<State> sortById(List<State> states) {
        List<State> result = new ArrayList<>(states);
        List<Integer> idsSorted= getAllIdsInOrder(states);
        for (int i : idsSorted) {
            for (State state : states) {
                if (state.id == i && !result.contains(state)) {
                    result.add(state);
                    break;
                }
            }
        }
        return result;
    }

    private List<Integer> getAllIdsInOrder(List<State> states) {
        List<Integer> idList = new ArrayList<>();
        for (State state: states) {
            idList.add(state.id);
        }
        return idList.stream().sorted().toList();

    }


}
