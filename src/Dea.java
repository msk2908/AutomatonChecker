import java.util.HashMap;
import java.util.List;

public class Dea {
    List<State> states;
    HashMap<Input, State> transitions;

    public Dea(List<State> states, HashMap<Input, State> transitions) {
        states = states;
        transitions = transitions;
    }

    public void minimization() {
       /* Minimizes the DFA using state equivalence:
            1. Partition states into accepting and non-accepting.
            2. Refine partitions until no further splits occur.
            3. Build a new DFA from the resulting state classes.*/
    }

}
