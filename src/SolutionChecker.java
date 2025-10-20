
import evtlSpaeterNutzbar.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolutionChecker {

    public SolutionChecker() {

    }

    public static Dea convertInputToStates(InputAutomaton automaton) {
        List<State> states = new ArrayList<>();
        HashMap<StateDraw, Integer> idMap = new HashMap<StateDraw, Integer>();
        Alphabet alphabet = new Alphabet(new ArrayList<>());

        int id = 0;
        for (StateDraw stateDraw: automaton.stateDrawList) {
            String name = stateDraw.getName();
            boolean terminal = stateDraw.isFinal();
            boolean starting = stateDraw.isStart();
            State newState = new State(id, name, new HashMap<>(), terminal, starting);
            states.add(newState);
            idMap.put(stateDraw, id);
            id++;
        }

        for (Transition transition : automaton.transitionList) {
            int idFrom = idMap.get(transition.getFrom());
            int idTo = idMap.get(transition.getTo());
            List<State> transitionsList = new ArrayList<>();
            states.get(idFrom).setTransitions(alphabet.add(transition.getLabel()), states.get(idTo));
        }


        return new Dea(states, false, alphabet);
    }




}
