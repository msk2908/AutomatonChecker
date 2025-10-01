import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toCollection;

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

    public void drawNea() {
        int x = 10;
        int y = 10;
        List<Coordinate> coordinates = new ArrayList<>();
        boolean flag = true;
        boolean quadrupleFlag = true;
        for (State state : states) {
            coordinates.add(new Coordinate(x, y));
            if (flag) {
                if (quadrupleFlag) {
                    x += 150;
                    quadrupleFlag = false;
                } else {
                    y += 150;
                    quadrupleFlag = true;
                    flag = false;
                }
            } else {
                if (quadrupleFlag) {
                    x += 150;
                    quadrupleFlag = false;
                } else {
                    y += 150;
                    flag = true;
                    quadrupleFlag = true;
                }
            }

        }

        AutomatonDrawer stateDrawerList = new AutomatonDrawer(states, coordinates);
        JFrame fenster = stateDrawerList.paintFrame();
        stateDrawerList.paint(fenster);

        // Konsole offen halten
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER zum Beenden...");
        scanner.nextLine();
    }


    /**
     * -------------------------------------------------------------------------------------------------------------
     **/

    public Dea convertNeaToDea() {
        getRidOfEpsilons();
        List<HashMap<Input, List<State>>> listOfDifferentiatedStates = new ArrayList<>(); // to save new states for Dea
        State startingState;
        try {
            startingState = getStartingState();
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }

        //ok wait let me just do this again pls
        onlyLeaveSingleTransitions();




        /*List<State> startList = new ArrayList<>();
        startList.add(startingState);
        HashMap<Input, List<State>> startMap = new HashMap<>();
        startMap.put(alphabet.get("Epsilon"), startList);
        listOfDifferentiatedStates.add(startMap);  // first state has to be the starting state

        //TODO get rid of equal states with no epsilon-transition
        split(listOfDifferentiatedStates, startMap);
        //putTogether(listOfDifferentiatedStates);

        //this seems to be minimization?
        //getRidOfEquals(listOfDifferentiatedStates);
        //getRidOfSubsets(listOfDifferentiatedStates);

        // get rid of states that ended up twice in Dea on accident
        // necessary bc starting state does things*/

        return new Dea(states, false, alphabet);
    }


    private void onlyLeaveSingleTransitions() {
        boolean somethingChanged = true;
        boolean stop = false;
        while (somethingChanged) {
            List<State> compare = new ArrayList<>(this.states);
            for (State state : this.states) {
                for (Input input : state.transitions.keySet()) {
                    if (state.transitions.get(input).size() > 1) {
                        List<State> followingStates = state.transitions.get(input);
                        this.states.removeAll(state.transitions.get(input));
                        HashMap<Input, List<State>> followingTransitions = getFollowingStates(followingStates);
                        state.removeTransition(state.transitions.get(input));
                        State keepOne = followingStates.getFirst();
                        keepOne.addTransitions(moveTransitions(followingTransitions, followingStates, keepOne));
                        state.setTransitions(input, keepOne);

                        this.states.add(keepOne);



                        stop = true;
                    }
                    if (stop) {
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            somethingChanged = !this.states.equals(compare);
        }

    }


    private void getRidOfEquals(List<HashMap<Input, List<State>>> listOfDifferentiatedStates) {
        for (HashMap<Input, List<State>> state1 : listOfDifferentiatedStates) {
            for (Input input : state1.keySet()) {
                //check if state1 goes to states that are equal
                checkForEqualStates(state1.get(input));
            }
        }
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
                    //TODO if following have the same transitions, then create only one state for them
                    if (!compare.contains(following) && !following.isEmpty()) {
                        compare.add(following);
                    }
                }
            }

        }
    }

    /*private void putTogether(List<HashMap<Input, List<State>>> listOfPossibleTransitions) {
        List<State> states = new ArrayList<>();

        int id = 0;
        for (HashMap<Input, List<State>> transition : listOfPossibleTransitions) {
            //TODO add starting and terminal
            State newState = new State(id, "creative Name", new HashMap<>(), false, false);
            for (Input input: transition.keySet()) {
                // remove equal states
                checkForEqualStates(transition.get(input));
                // get all transitions of the leftover states
                HashMap<Input, List<State>> transitions = new HashMap<>();
                for (State state: transition.get(input)) {
                    transitions.putAll(state.transitions);
                }
                newState.addTransitions(transitions);
                states.add(newState);
            }
        }
        Dea dea = new Dea(states, false, alphabet);
        dea.drawDea();
    }*/

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
                    for (State state1 : statesConnectedByE) {
                        if (state1.starting) {
                            state.setStarting();
                        }
                        toRemove.add(state1);
                        if (statesConnectedByE.isEmpty()) {
                            break;
                        }
                    }
                    for (int i = 0; i < toRemove.size(); i++) {
                        State state1 = toRemove.get(i);
                        state.addTransitions(state1.transitions);
                        state.removeTransition(alphabet.get("Epsilon"), state1);
                        this.states.remove(state1);
                    }
                    break;
                }
            }

            int counter = 0;
            //count epsilons
            for (State state : states) {
                try {
                    counter += state.transitions.get(alphabet.get("Epsilon")).size();
                } catch (NullPointerException n) {
                    counter += 0;
                }
            }
            if (counter == 0) {
                flag = false;
            }
        }


    }

    private void getRidOfSubsets(List<HashMap<Input, List<State>>> listOfPossibleTransitions) {
        // for every state that is reachable by the same input
        // if there is a state that contains the same transitions (and more)
        // remove the state with fewer transitions and add old transitions to remaining state, delete doubles
        State subset = null;


        boolean somethingChanged = true;


        while (somethingChanged) {
            State toRemove = null;
            List<State> compare = new ArrayList<>(this.states);
            boolean stop = false;
            for (State state : this.states) {
                for (State state1 : this.states) {
                    if (state.id != state1.id) {
                        subset = isSubset(state, state1);
                    }
                    if (subset != null) {
                        toRemove = subset;
                        state.addTransitions(moveTransitions(subset.transitions, subset, state));
                        state.removeTransition(toRemove);
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }

            this.states.remove(toRemove);
            states.remove(toRemove);
            somethingChanged = !compare.equals(this.states);
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

    private HashMap<Input, List<State>> getFollowingStates(State stateToCheck) {
        List<State> list = new ArrayList<>();
        list.add(stateToCheck);
        return getFollowingStates(list);
    }

    private State isSubset(State a, State b) {
        State subset = null;
        boolean aIsSubset = true;
        boolean bIsSubset = true;
        HashMap<Input, List<State>> transitionsA = a.transitions;
        HashMap<Input, List<State>> transitionsB = b.transitions;

        try {
            for (Input input : transitionsA.keySet()) {
                List<State> next = transitionsA.get(input);
                if (!transitionsB.get(input).containsAll(next)) {
                    aIsSubset = false;
                }
            }
        } catch (NullPointerException n) {
            aIsSubset = false;
        }


        try {
            for (Input input : transitionsB.keySet()) {
                List<State> next = transitionsB.get(input);
                if (!transitionsA.get(input).containsAll(next)) {
                    bIsSubset = false;
                }
            }
        } catch (NullPointerException n) {
            bIsSubset = false;
        }

        if (aIsSubset) {
            subset = a;
        }

        if (bIsSubset) {
            subset = b;
        }

        return subset;
    }


    private void checkForEqualStates(List<State> states) {
        boolean somethingChanged = true;

        while (somethingChanged) {
            boolean stop = false;
            List<State> compare = new ArrayList<>(this.states);
            State toRemove = null;
            for (State state : states) {
                for (State state1 : states) {
                    if (state.transitions.equals(state1.transitions) && !(state1.id == state.id)) {
                        toRemove = state1;
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            //delete all transitions to states to remove
            if (!(toRemove == null)) {
                for (State state : this.states) {
                    for (Input input : state.transitions.keySet()) {
                        if (state.transitions.get(input).contains(toRemove)) {
                            //add all transitions that don't go to the removed State
                            state.addTransitions(moveTransitions(toRemove.transitions, toRemove, state));
                            state.removeTransition(input, toRemove);
                        }

                    }
                }
            }


            this.states.remove(toRemove);
            states.remove(toRemove);
            somethingChanged = !compare.equals(this.states);
        }
    }

    private HashMap<Input, List<State>> moveTransitions(HashMap<Input, List<State>> transitions, State toRemoveFrom, State toMoveTo) {
        HashMap<Input, List<State>> newTransitions = new HashMap<>();
        for (Input input : transitions.keySet()) {
            List<State> following = new ArrayList<>();
            for (State state : transitions.get(input)) {
                if (!state.equals(toRemoveFrom)) {
                    following.add(state);
                } else {
                    following.add(toMoveTo);
                }
            }
            newTransitions.put(input, following);
        }


        return newTransitions;
    }

    private HashMap<Input, List<State>> moveTransitions(HashMap<Input, List<State>> transitions, List<State> toRemoveFrom, State toMoveTo) {
        HashMap<Input, List<State>> newTransitions = new HashMap<>();
        for (State state : toRemoveFrom) {
            if (!state.equals(toMoveTo)) {
                newTransitions.putAll(moveTransitions(transitions, state, toMoveTo));
            }

        }
        return newTransitions;
    }

    private State getStartingState() throws Exception {

        for (State state : states) {
            if (state.starting) {
                return state;
            }
        }

        throw new Exception("automaton has no starting state");
    }

    private void sortById(List<State> states) {
        List<State> result = new ArrayList<>(states);
        List<Integer> idsSorted = getAllIdsInOrder(states);
        for (int i : idsSorted) {
            for (State state : states) {
                if (state.id == i && !result.contains(state)) {
                    result.add(state);
                    break;
                }
            }
        }
    }

    private List<Integer> getAllIdsInOrder(List<State> states) {
        List<Integer> idList = new ArrayList<>();
        for (State state : states) {
            idList.add(state.id);
        }
        return idList.stream().sorted().toList();

    }
}




