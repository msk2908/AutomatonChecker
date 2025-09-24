import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nea {
    List<State> states;
    Alphabet alphabet;

    public Nea(List<State> states, Alphabet alphabet) {
        this.states = states;
        this.alphabet = alphabet;
    }

    public String neaToString(Alphabet alphabet) {
        String res = "";
        for (State state : states) {
            res += "state " + state.name + " has the transitions: \n" + state.transitionsToString(alphabet, states.size()) + "\n";

        }
        return res;
    }


    /**
     * -------------------------------------------------------------------------------------------------------------
     **/

    public Dea convertNeaToDea() {
        List<List<State>> listOfDifferentiatedStates = new ArrayList<>();
        List<List<State>> compare = new ArrayList<>();
        State startingState;
        try {
            startingState = getStartingState();
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }

        //List<State> reachableStates = deleteUnreachableStatesFromList(this.states);     //get rid of unreachable states -> voll unnötig, fliegen sowieso
        List<State> startList = new ArrayList<>();
        startList.add(startingState);
        listOfDifferentiatedStates.add(startList);  // first state has to be the starting state
        boolean flag = true;

        while (compare != listOfDifferentiatedStates) {
            if (!flag) {
                listOfDifferentiatedStates.clear();
                listOfDifferentiatedStates.addAll(compare);
                compare.clear();
            } else {
                flag = false;
                compare.addAll(listOfDifferentiatedStates);
            }
            for (List<State> stateList : listOfDifferentiatedStates) {
                compare.add(getFollowingStates(stateList));
            }
        }


        return new Dea(new ArrayList<>(), false, alphabet);
    }


    private List<State> deleteUnreachableStatesFromList(List<State> allStates) {
        List<State> following = getFollowingStates(allStates);
        List<State> reachable = new ArrayList<>();
        try {
            reachable.add(getStartingState());
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }

        for (State state : allStates) {
            if (following.contains(state)) {
                reachable.add(state);
            }
        }
        return reachable;
    }

    /**
     * gets all the states that are reachable starting from the given list
     */

    private List<State> getFollowingStates(List<State> statesToCheck) {
        List<State> statesToGoTo = new ArrayList<>();
        for (Input input : alphabet.possibleInputs) {
            for (State state : statesToCheck) {
                if (state.getTransitions().containsKey(input)) {
                    statesToGoTo.addAll(state.transitions.get(input));
                }
            }
        }
        return statesToGoTo;
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




