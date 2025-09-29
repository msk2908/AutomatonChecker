import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void drawDea() {
        int x = 10;
        int y = 10;
        List<Coordinate> coordinates = new ArrayList<>();
        boolean flag = true;
        boolean quadrupleFlag = true;
        for (State state : states) {
            coordinates.add(new Coordinate(x, y));
            if (flag) {
                if (quadrupleFlag) {
                    x += 150;
                    quadrupleFlag = false;
                } else {
                    y += 150;
                    quadrupleFlag = true;
                    flag = false;
                }
            } else {
                if (quadrupleFlag) {
                    x += 150;
                    quadrupleFlag = false;
                } else {
                    y += 150;
                    flag = true;
                    quadrupleFlag = true;
                }
            }

        }

        AutomatonDrawer stateDrawerList = new AutomatonDrawer(states, coordinates);
        JFrame fenster = stateDrawerList.paintFrame();
        stateDrawerList.paint(fenster);

        // Konsole offen halten
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER zum Beenden...");
        scanner.nextLine();
        System.exit(0);
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
