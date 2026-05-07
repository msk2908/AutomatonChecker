package Gui;

import java.util.ArrayList;
import java.util.List;

public class InputAutomaton {
    public List<StateDraw> stateDrawList;
    public List<Transition> transitionList;
    public boolean complete = false;
    public boolean newExercise = false;

    public InputAutomaton(List<StateDraw> stateDrawList, List<Transition> transitionList, boolean done) {
        this.stateDrawList = stateDrawList;
        this.transitionList = transitionList;
        this.complete = done;
    }

    public void setComplete() {
        complete = true;
    }

    public void setNewExercise(boolean flag) {
        this.newExercise = flag;
        stateDrawList = new ArrayList<>();
        transitionList = new ArrayList<>();
    }
}
