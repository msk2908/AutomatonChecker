
import RegExClasses.RegEx;
import Gui.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolutionChecker {

    public SolutionChecker() {

    }

    // TODO test this
    public static Dea convertInputToDea(InputAutomaton automaton) {
        List<State> states = new ArrayList<>();
        HashMap<StateDraw, Integer> idMap = new HashMap<StateDraw, Integer>();
        Alphabet alphabet = new Alphabet(new ArrayList<>());

        int id = 0;
        // catch all states
        for (StateDraw stateDraw : automaton.stateDrawList) {
            String name = stateDraw.getName();
            boolean terminal = stateDraw.isFinal();
            boolean starting = stateDraw.isStart();
            State newState = new State(id, name, new HashMap<>(), terminal, starting);
            states.add(newState);
            idMap.put(stateDraw, id);
            id++;
        }

        // catch all transitions
        for (Transition transition : automaton.transitionList) {
            int idFrom = idMap.get(transition.getFrom());
            int idTo = idMap.get(transition.getTo());
            states.get(idFrom).setTransitions(alphabet.add(transition.getLabel()), states.get(idTo));
        }

        // output: Dea that was input by user
        return new Dea(states, false, alphabet);
    }

    public boolean compareDea(Dea dea1, Dea dea2) throws Exception {
        // for every state of dea1
        // if dea2 contains a state with all the given transitions put in matchingStates-map
        if (!dea1.minimized) {
            dea1.minimize();
        }
        if (!dea2.minimized) {
            dea2.minimize();
        }

        Alphabet a1 = dea1.alphabet;
        Alphabet a2 = dea2.alphabet;

        List<String> a1String = a1.aToString();
        List<String> a2String = a2.aToString();


        // check if alphabets are same-sized
        if (a1String.size() != a2String.size()) {
            return false;
        }

        // if one dea has more states they are not equal
        if (dea1.states.size() != dea2.states.size()) {
            return false;
        }


        HashMap<State, List<State>> matches = new HashMap<>();
        List<State> states1 = new ArrayList<>();
        HashMap<State, List<State>> matchingStates = new HashMap<>();
        states1 = dea1.states;

        // TODO check if transitions match

        State startingState1 = dea1.getStartingState();
        State startingState2 = dea2.getStartingState();

        int distanceFromStartingState = 0;

        List<State> statesToCheck = new ArrayList<>();

        HashMap<Input, List<State>> possibleFollowUps1 = dea1.getFollowingStates(startingState1);
        HashMap<Input, List<State>> possibleFollowUps2 = dea2.getFollowingStates(startingState2);

        return true;
    }

    private boolean statesMatch(Dea dea1, Dea dea2, State state1, State state2) {
        /* two states are the same iff:
        1) they have the same number of outgoing transitions
        2) the states before were equal
        3) they have equal following states
        */
        if (state1.transitions.size() != state2.transitions.size() || getDistanceFromTerminal(dea1, state1) != getDistanceFromTerminal(dea2, state2)) {
            return false;
        }

        return true;
    }

    public int getDistanceFromTerminal(Dea dea, State state) {
        int n = 0;
        if (state.terminal) {
            return n;
        }
        HashMap<Input, List<State>> next = dea.getFollowingStates(state);
        while (!next.isEmpty()) {
            List<State> actualStates = new ArrayList<>();
            for (Input input : next.keySet()) {
                for (State state1 : next.get(input)) {
                    actualStates.add(state1);
                    if (state1.terminal) {
                        return n;
                    }
                }
            }
            n++;
            next = dea.getFollowingStates(actualStates);
        }
        return -1;
    }




    public boolean DeaMatchesRegEx(RegEx regEx, Dea dea) throws Exception {
        Alphabet alphabet = new Alphabet(regEx.getAlphabet());
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        Dea givenDea = nea.convertNeaToDea();
        return compareDea(givenDea, dea);
    }



    public boolean checkSolution(RegEx regEx, Alphabet alphabet, Dea deaCompare, BufferedReader reader) throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        InputAutomaton automaton = NEAGui.inputMain();
        boolean complete = false;
        while(!automaton.complete) {
            Thread.sleep(500);
            // TODO keep terminal busy
            //System.out.println("done?");
            //String input = reader.readLine();
            //System.in.wait();
        }
        Dea dea = convertInputToDea(automaton);
        boolean correct = compareDea(dea, deaCompare);
        System.out.println(correct);
        if (!correct) {
            deaCompare.drawDea();
        }
        return correct;
    }

}
