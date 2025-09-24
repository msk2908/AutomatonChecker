import org.junit.Test;

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
        List<HashMap<Input, List<State>>> listOfDifferentiatedStates = new ArrayList<>();
        List<HashMap<Input, List<State>>> compare = new ArrayList<>();
        State startingState;
        try {
            startingState = getStartingState();
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }

        //List<State> reachableStates = deleteUnreachableStatesFromList(this.states);     //get rid of unreachable states -> voll unnötig, fliegen sowieso
        List<State> startList = new ArrayList<>();
        startList.add(startingState);
        HashMap<Input, List<State>> startMap = new HashMap<>();
        startMap.put(null, startList);
        listOfDifferentiatedStates.add(startMap);  // first state has to be the starting state

        split(listOfDifferentiatedStates, startMap);


        //TODO put collected states together and put transitions


        return new Dea(new ArrayList<>(), false, alphabet);
    }

    private List<HashMap<Input, List<State>>> split(List<HashMap<Input, List<State>>> listOfDifferentiatedStates, HashMap<Input, List<State>> startMap) {
        List<HashMap<Input, List<State>>> compare = new ArrayList<>();
        while (compare.size() != listOfDifferentiatedStates.size()) {
            listOfDifferentiatedStates.clear();
            listOfDifferentiatedStates.addAll(compare);
            compare.clear();

            compare.add(startMap);
            for (HashMap<Input, List<State>> stateMap : listOfDifferentiatedStates) {
                for (Input input : stateMap.keySet()) {
                    HashMap<Input, List<State>> following = getFollowingStates(stateMap.get(input));
                    if (!compare.contains(following) && !following.isEmpty()) {
                        compare.add(following);
                    }
                }
            }

        }
        return listOfDifferentiatedStates;
    }


    /*private List<State> deleteUnreachableStatesFromList(List<State> allStates) {
        List<List<State>> following = getFollowingStates(allStates);
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
    }*/

    /**
     * gets all the states that are reachable starting from the given list
     */
    private HashMap<Input, List<State>> getFollowingStates(List<State> statesToCheck) {

        HashMap<Input, List<State>> mapOfListOfStatesToGoTo = new HashMap<>();
        for (Input input : alphabet.possibleInputs) {
            List<State> statesToGoTo = new ArrayList<>();
            for (State state : statesToCheck) {
                if (state.getTransitions().containsKey(input)) {
                    for (State toAdd : state.transitions.get(input)) {
                        if (!statesToGoTo.contains(toAdd)) {
                            statesToGoTo.add(toAdd);
                        }
                    }
                }
            }
            if (!statesToGoTo.isEmpty()) {
                mapOfListOfStatesToGoTo.put(input, statesToGoTo);
            }

        }
        return mapOfListOfStatesToGoTo;
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




