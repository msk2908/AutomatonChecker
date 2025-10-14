package evtlSpaeterNutzbar;

import java.util.List;

public class DrawnStuff {
    List<StateDraw> states;
    List<Transition> transitions;

    public DrawnStuff(List<StateDraw> states, List<Transition> transitions) {
        this.states = states;
        this.transitions = transitions;
    }

    public List<StateDraw> getStates() {
        return this.states;
    }

    public List<Transition> getTransitions(){
        return this.transitions;
    }

}
