import java.util.ArrayList;
import java.util.List;

public class Dea {
    List<State> states;
    boolean minimized;
    Alphabet alphabet;

    public Dea(List<State> states, boolean minimized, Alphabet alphabet) {
        this.states = states;
        this.minimized = minimized;
        this.alphabet = alphabet;
    }

    public String deaToString(Alphabet alphabet) {
        String res = "";
        for (State state : states) {
            res += "state " + state.name + " has the transitions: \n" + state.transitionsToString(alphabet) + "\n";
        }
        return res;
    }

    public void minimization() {
       /* Minimizes the DFA using state equivalence:
            1. Partition states into accepting and non-accepting.
            2. Refine partitions until no further splits occur.
            3. Build a new DFA from the resulting state classes.*/
    }



    public Dea minimize() {
        //TODO
        /**
        //1. write down a table of all pairs {p,q} initially
        //2. mark {p,q} if i $\in$ F and q $\notin$ F or vice versa
        //3. repeat the following until no more changes occur: if there exists an unmarked pair {p,q} st {$\delta$(p), $\delta$(q)} is unmarked for some a ?in $\Sigma$, then mark {p,q}
        //4. when done, q $\approx$ q iff {p,q} is**/
        return new Dea(new ArrayList<>(), true, this.alphabet);
    }

}
