import java.util.HashMap;
import java.util.List;

public class Nea {
    List<State> states;
    HashMap<Input, List<State>> transitions;
    State startingState;
    List<State> finalStates;
    Alphabet inputAlphabet;

    public Nea(List<State> states, HashMap<Input, List<State>> transitions, State startingState, List<State> finalStates, Alphabet inputAlphabet) {
        this.finalStates = finalStates;
        this.inputAlphabet = inputAlphabet;
        this.startingState = startingState;
        this.states = states;
        this.transitions = transitions;
    }
}
