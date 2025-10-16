
import evtlSpaeterNutzbar.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolutionChecker {

    public SolutionChecker() {

    }

    public static java.util.List<State> convertInputToStates(InputAutomaton automaton) {
        List<State> states = new ArrayList<>();
        int id = 0;
        for (StateDraw stateDraw: automaton.stateDrawList) {
            String name = stateDraw.getName();
            boolean terminal = stateDraw.isFinal();
            boolean starting = stateDraw.isStart();
            State newState = new State(id, name, new HashMap<>(), terminal, starting);

            for (Transition transition : automaton.transitionList) {
                if (transition.getFrom().equals(stateDraw)) {
                    //TODO get transitions
                }
            }
            id++;
        }


        return states;
    }



}
