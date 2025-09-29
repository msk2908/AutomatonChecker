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


        //List<State> reachableStates = deleteUnreachableStatesFromList(this.states);     //get rid of unreachable states -> voll unnötig, fliegen sowieso
        List<State> startList = new ArrayList<>();
        startList.add(startingState);
        HashMap<Input, List<State>> startMap = new HashMap<>();
        startMap.put(alphabet.get("Epsilon"), startList);
        listOfDifferentiatedStates.add(startMap);  // first state has to be the starting state

        //TODO get rid of equal states with no epsilon-transition
        split(listOfDifferentiatedStates, startMap);
        getRidOfEquals(listOfDifferentiatedStates);
        checkForWeirdLoop(this.states);

        // get rid of states that ended up twice in Dea on accident
        // necessary bc starting state does things

        return new Dea(states, false, alphabet);
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

    private HashMap<Input, List<State>> getFollowingStates(State statesToCheck) {
        List<State> list = new ArrayList<>();
        list.add(statesToCheck);
        return getFollowingStates(list);
    }


    private void checkForEqualStates(List<State> states) {
        boolean somethingChanged = true;
        boolean stop = false;
        while (somethingChanged) {
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

    /**
     * removes any unnecessary loops
     * @param toCheck: List of states to check for Loop
     */
    private void checkForWeirdLoop(List<State> toCheck) {
        State toRemove = null;
        State toRemoveFrom = null;
        boolean somethingChanged = true;
        boolean stop = false;

        while (somethingChanged) {
            boolean one = false;
            boolean two = false;
            List<State> compare = new ArrayList<>(this.states);
            for (State state : toCheck) {
                for (State state1: toCheck) {
                    if (checkIfEqual(state, state1)) {
                        HashMap<Input, List<State>> followUp = state.transitions;
                        HashMap<Input, List<State>> followUp1 = state1.transitions;

                        for (Input input: followUp.keySet()) {
                            if (followUp.get(input).contains(state1)) {
                                one = true;
                            }
                            if (followUp1.get(input).contains(state)) {
                                two = true;
                            }
                        }

                        if (one && two) {
                            // found a loophole
                            toRemove = state1;
                            toRemoveFrom = state;
                            stop = true;
                            break;
                        }
                    }
                }
                if (stop) {
                    break;
                }
            }

            if (toRemove != null) {
                HashMap<Input, List<State>> transitions = toRemove.transitions;
                for (Input input: transitions.keySet()) {
                    toRemoveFrom.removeTransition(input, toRemove);
                    toRemoveFrom.setTransitions(input, toRemoveFrom);
                }
                for (State state: this.states) {
                    for (Input input: state.transitions.keySet()) {
                        if (state.transitions.get(input).contains(toRemove)) {
                            state.removeTransition(input, toRemove);
                            state.setTransitions(input, toRemoveFrom);
                        }
                    }
                }
            }
            this.states.remove(toRemove);
            toCheck.remove(toRemove);

            somethingChanged = !compare.equals(this.states);
        }

    }

    private boolean checkIfEqual(State a, State b) {
        return (a.transitions.equals(b.transitions) && !(a.id == b.id));
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




