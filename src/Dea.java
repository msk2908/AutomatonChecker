import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dea {
    List<State> states;
    boolean minimized;

    public Dea(List<State> states, boolean minimized) {
        states = states;
        this.minimized = minimized;
    }

    public void minimization() {
       /* Minimizes the DFA using state equivalence:
            1. Partition states into accepting and non-accepting.
            2. Refine partitions until no further splits occur.
            3. Build a new DFA from the resulting state classes.*/
    }



    public static Dea minimize() {
        //TODO
        return new Dea(new ArrayList<>(), true);
    }

}
