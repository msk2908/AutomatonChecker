import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nea {
    List<State> states;

    public Nea(List<State> states) {
        this.states = states;
    }

    public static Dea convertNeaToDea()  {
        //TODO
        /* converts Nea to Dea using Hopcroft or Moore*/
        // 1. Initialize partitions into accepting and non-accepting states.
        // 2. Use a worklist of splitter sets to refine partitions.
        // 3. Efficiently split classes based on input transitions.
        // 4. Continue until no more splits are possible.
        // 5. Construct minimized DFA from the final partitions.
        return new Dea(new ArrayList<>(), false);
    }



}




