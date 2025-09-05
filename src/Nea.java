import java.util.HashMap;
import java.util.List;

public class Nea {
    List<State> states;

    public Nea(List<State> states) {
        this.states = states;
    }


    public void convert() {
        /* converts Nea to Dea using Hopcroft or Moore*/
        // 1. Initialize partitions into accepting and non-accepting states.
        // 2. Use a worklist of splitter sets to refine partitions.
        // 3. Efficiently split classes based on input transitions.
        // 4. Continue until no more splits are possible.
        // 5. Construct minimized DFA from the final partitions.

    }


    // useless?
    /*public void getRidOfDoubleTransitions() {
        /*for every triple (X,a,Y) with X, Y being states and a being the transition in between,
         check if there exists a copy and if yes delete it

        for (State state : states) {
            HashMap<Input, List<String>> transitions = state.getTransitions();
            for (Input input : transitions.keySet()) {

            }
        }

    }*/


}




