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
            res += "state " + state.name + " has the transitions: \n" + state.transitionsToString(alphabet) + "\n";
        }
        return res;
    }


    /**
     * -------------------------------------------------------------------------------------------------------------
     **/

    public Dea convertNeaToDea() {
        getRidOfEpsilons();
        List<HashMap<Input, List<State>>> listOfDifferentiatedStates = new ArrayList<>();
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
        startMap.put(alphabet.get("Epsilon"), startList);
        listOfDifferentiatedStates.add(startMap);  // first state has to be the starting state

        split(listOfDifferentiatedStates, startMap);

        List<State> removeDoubles = new ArrayList<>();
        for (int i = 0; i < this.states.size(); i++) {
            for (int j = 0; j < this.states.size(); j++) {
                if (i != j && this.states.get(i).equals(this.states.get(j))) {
                    this.states.get(i).addTransitions(this.states.get(j).transitions);
                    removeDoubles.add(this.states.get(j));
                }
            }
        }
        this.states.removeAll(removeDoubles);        // necessary bc starting state does things

        return new Dea(states, false, alphabet);
    }

    private void split(List<HashMap<Input, List<State>>> listOfDifferentiatedStates, HashMap<Input, List<State>> startMap) {
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
    }

    /**
     * If the only connection between two states is en epsilon-transition, delete the one it points to and give the first one its transitions
     */

    private void getRidOfEpsilons() {
        boolean flag = true;
        // get rid of all states connected by Epsilon
        while (flag) {
            for (State state : this.states) {
                List<State> statesConnectedByE = state.transitions.get(alphabet.get("Epsilon"));
                if (statesConnectedByE != null) {
                    List<State> toRemove = new ArrayList<>();
                    for (State state1: statesConnectedByE) {
                        if (state1.starting) {
                            state.setStarting();
                        }
                        toRemove.add(state1);
                        if (statesConnectedByE.isEmpty()) {
                            break;
                        }
                    }
                    for (int i = 0; i< toRemove.size(); i++) {
                        State state1 = toRemove.getFirst();
                        state.addTransitions(state1.transitions);
                        state.removeTransition(alphabet.get("Epsilon"), state1);
                        this.states.remove(state1);
                    }
                    break;
                }
            }
            flag = false;
        }

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
        sortById(statesToCheck);
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




