
import RegExClasses.RegEx;
import Gui.*;

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


        return new Dea(states, false, alphabet);
    }

    public boolean compareDea(Dea dea1, Dea dea2) {
        // for every state of dea1
        // if dea2 contains a state with all the given transitions
        if (!dea1.minimized) {
            dea1.minimize();
        }
        if (!dea2.minimized) {
            dea2.minimize();
        }

        HashMap<State, List<State>> matches = new HashMap<>();
        List<State> states1 = new ArrayList<>();
        HashMap<State, List<State>> matchingStates = new HashMap<>();
        states1 = dea1.states;

        if (dea1.states.size() != dea2.states.size()) {
            return false;
        }

        // match up states that have the same transitions
        for (State state : dea1.states) {
            boolean stateMatched = false;
            matchingStates.put(state, new ArrayList<>());
            for (State state1 : dea2.states) {
                if (dea1.haveEqualKeysets(state, state1)) {
                    matchingStates.get(state).add(state1);
                    stateMatched = true;
                }
            }
            // if there is no matching state for one of the states, the deas are not equal
            if (!stateMatched) {
                return false;
            }


            // TODO check if transitions match

        }
        return true;
    }


    public boolean DeaMatchesRegEx(RegEx regEx, Dea dea) {
        Alphabet alphabet = new Alphabet(regEx.getAlphabet());
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        Dea givenDea = nea.convertNeaToDea();
        return compareDea(givenDea, dea);
    }

}
