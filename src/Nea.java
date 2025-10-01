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
        //ok wait let me just do this again pls
        onlyLeaveSingleTransitions();

        //TODO delete states with no ingoing transitions
        getRidOfLostStates();

        return new Dea(states, false, alphabet);
    }


    private void onlyLeaveSingleTransitions() {
        boolean somethingChanged = true;

        while (somethingChanged) {
            boolean stop = false;
            List<State> compare = new ArrayList<>(this.states);
            for (State state : this.states) {
                for (Input input : state.transitions.keySet()) {
                    if (state.transitions.get(input).size() > 1) {

                        List<State> followingStates = new ArrayList<>(state.transitions.get(input));

                        // collect all transitions of the states just deleted (trust me bro)
                        HashMap<Input, List<State>> followingTransitions = getFollowingStates(followingStates);

                        // delete transitions to states just deleted
                        state.removeTransition(followingStates);

                        // keep one of the deleted states
                        State keepOne = followingStates.getFirst();

                        // add all transitions of the others
                        keepOne.addTransitions(moveTransitions(followingTransitions, followingStates, keepOne));

                        // add new transition to the state that is kept
                        state.setTransitions(input, keepOne);

                        // replace all transitions leading to one of the states to delete
                        replaceTransitions(followingStates, keepOne);

                        //remove all states from list of states
                        this.states.removeAll(followingStates);

                        // add the "new" state to the list of states
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

    private void getRidOfLostStates() {
        List<State> notLost = new ArrayList<>();
        for (State state : this.states) {
            for (Input input : state.transitions.keySet()) {
                notLost.addAll(state.transitions.get(input));
            }
        }
        List<State> toRemove = new ArrayList<>();
        for (State state : this.states) {
            if (!notLost.contains(state) && !state.starting) {
                toRemove.add(state);
            }
        }

        this.states.removeAll(toRemove);
    }

    private void replaceTransitions(List<State> followingStates, State keepOne) {
        for (State state : this.states) {
            for (State state1 : followingStates) {
                Input input = state.removeTransition(state1);
                if (input != null) {
                    state.setTransitions(input, keepOne);
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




