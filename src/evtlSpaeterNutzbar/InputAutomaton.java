package evtlSpaeterNutzbar;

import java.util.List;

public class InputAutomaton {
    public List<StateDraw> stateDrawList;
    public List<Transition> transitionList;

    public InputAutomaton(List<StateDraw> stateDrawList, List<Transition> transitionList) {
        this.stateDrawList = stateDrawList;
        this.transitionList = transitionList;
    }
}
