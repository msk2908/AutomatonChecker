import java.util.ArrayList;
import java.util.List;

public class Nea {
    List<State> states;
    Alphabet alphabet;

    public Nea(List<State> states, Alphabet alphabet) {
        this.states = states;
        this.alphabet = alphabet;
    }

    public Dea convertNeaToDea() {
        State startingState;
        try {
            startingState = getStartingState();
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }
        List<State> check = new ArrayList<>();
        check.add(startingState);
        List<State> following = getFollowingStates(check);

        return new Dea(new ArrayList<>(), false, alphabet);
    }


    /**
     * gets all the states that are reachable starting from the given list
     * @param statesToCheck
     * @return
     */

    private List<State> getFollowingStates(List<State> statesToCheck) {
        List<State> statesToGoTo = new ArrayList<>();
        for (Input input : alphabet.possibleInputs) {
            for (State state : statesToCheck) {
                if (state.getTransitions().containsKey(input)) {
                    statesToGoTo.add(state.transitions.get(input));
                }
            }
        }
        return statesToGoTo;
    }


    public String neaToString(Alphabet alphabet) {
        String res = "";
        for (State state : states) {
            res += "state " + state.name + " has the transitions: \n" + state.transitionsToString(alphabet, states.size()) + "\n";

        }
        return res;
    }

    private State getStartingState() throws Exception {

        for (State state : states) {
            if (state.starting) {
                return state;
            }
        }

        throw new Exception("automaton has no starting state");
    }
}




