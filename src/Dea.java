import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Dea {
    List<State> states;
    boolean minimized;
    Alphabet alphabet;

    public Dea(List<State> states, boolean minimized, Alphabet alphabet) {
        this.states = states;
        this.minimized = minimized;
        this.alphabet = alphabet;
    }

    public String deaToString(Alphabet alphabet) {
        String res = "";
        for (State state : states) {
            res += "state " + state.name + " has the transitions: \n" + state.transitionsToString(alphabet) + "\n";
        }
        return res;
    }

    public void drawDea() {
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
        System.exit(0);
    }

    public void minimization() {
       /* Minimizes the DFA using state equivalence:
            1. Partition states into accepting and non-accepting.
            2. Refine partitions until no further splits occur.
            3. Build a new DFA from the resulting state classes.*/
    }


    public Dea minimize() {
        List<HashMap<Input, List<State>>> listOfDifferentiatedStates = new ArrayList<>(); // to save new states for Dea
        State startingState;
        try {
            startingState = getStartingState();
        } catch (Exception e) {
            throw new IllegalArgumentException("Your automaton did not contain a starting state, how did you manage to do this");
        }

        List<State> startList = new ArrayList<>();


        startList.add(startingState);

        HashMap<Input, List<State>> startMap = new HashMap<>();

        startMap.put(alphabet.get("Epsilon"), startList);

        listOfDifferentiatedStates.add(startMap);


        //TODO get rid of equal states with no epsilon-transition

        split(listOfDifferentiatedStates, startMap);


        getRidOfEquals(listOfDifferentiatedStates);


        checkForWeirdLoop(this.states);
        /**
         //1. write down a table of all pairs {p,q} initially
         //2. mark {p,q} if i $\in$ F and q $\notin$ F or vice versa
         //3. repeat the following until no more changes occur: if there exists an unmarked pair {p,q} st {$\delta$(p), $\delta$(q)} is unmarked for some a ?in $\Sigma$, then mark {p,q}
         //4. when done, q $\approx$ q iff {p,q} is**/
        return new Dea(this.states, true, this.alphabet);
    }

    /**
     * removes any unnecessary loops
     *
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
                for (State state1 : toCheck) {
                    if (checkIfEqual(state, state1)) {
                        HashMap<Input, List<State>> followUp = state.transitions;
                        HashMap<Input, List<State>> followUp1 = state1.transitions;

                        for (Input input : followUp.keySet()) {
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
                for (Input input : transitions.keySet()) {
                    toRemoveFrom.removeTransition(input, toRemove);
                    toRemoveFrom.setTransitions(input, toRemoveFrom);
                }
                for (State state : this.states) {
                    for (Input input : state.transitions.keySet()) {
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

    private void getRidOfEquals(List<HashMap<Input, List<State>>> listOfDifferentiatedStates) {
        for (HashMap<Input, List<State>> state1 : listOfDifferentiatedStates) {
            for (Input input : state1.keySet()) {
                //check if state1 goes to states that are equal
                checkForEqualStates(state1.get(input));
            }
        }
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


    private boolean checkIfEqual(State a, State b) {
        return (a.transitions.equals(b.transitions) && !(a.id == b.id));
    }

}
