package evtlSpaeterNutzbar;

import java.util.List;

public class InputAutomaton {
    public List<StateDraw> stateDrawList;
    public List<Transition> transitionList;
    public boolean complete = false;

    public InputAutomaton(List<StateDraw> stateDrawList, List<Transition> transitionList, boolean done) {
        this.stateDrawList = stateDrawList;
        this.transitionList = transitionList;
        this.complete = done;
    }

    public void setComplete() {
        complete = true;
    }
}
