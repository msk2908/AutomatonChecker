import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Input a = new Input("a");
        Input b = new Input("b");
        HashMap aTransitions = new HashMap();
        List<String> transA = new ArrayList<>();
        transA.add("A");
        transA.add("A");
        transA.add("B");
        aTransitions.put(a, transA);
        transA.remove("A");
        transA.remove("A");
        aTransitions.put(b, transA);

        HashMap bTransitions = new HashMap();
        List<String> transB = new ArrayList<>();
        transB.add("A");
        transB.add("A");
        transB.add("B");
        bTransitions.put(a, transB);
        transB.remove("A");
        transB.remove("A");
        bTransitions.put(b, transB);

        State A = new State("A", aTransitions, false, true);
        State B = new State("B", bTransitions, true, false);
        List<State> s1 = new ArrayList<>();
        s1.add(A);
        s1.add(B);
        Nea default1 = new Nea(s1);

        boolean starting = false;
        String flag = "y";
        List<State> states = new ArrayList<>();

        while (flag.equals("y")) {

            //get states one by one
            List<String> StateNamelist = new ArrayList<String>();
            System.out.print("Please input one state: ");
            String state;
            state = br.readLine();

            StateNamelist.add(state);
            System.out.println(StateNamelist.getFirst());

            System.out.println("Is the state " + state + " terminal? y/n ");
            boolean terminal = br.readLine().equals("y");

            if (!starting) {
                //get only one starting state
                System.out.println("Is the state " + state + " a starting state? y/n ");
                starting = br.readLine().equals("y");
            }

            State currentState = new State(state, new HashMap<>(), terminal, starting);
            states.add(currentState);

            System.out.println("Does your automaton contain more states? y/n: ");
            flag = br.readLine();
        }


        // insert all transitions

        for (State state : states) {
            // get transitions for every state, put into Hashmap
            // Creates a reader instance which takes
            // input from standard input - keyboard

            Scanner reader = new Scanner(System.in);

            // nextInt() reads the next integer from the keyboard
            int number = -1;
            while (number < 0) {
                number = -1;
                System.out.println("How many transitions does " + state.name + " have? ");
                try {
                    String num = reader.nextLine();
                    number = Integer.parseInt(num);
                    break;
                } catch (Exception e) {
                    System.out.println("not a valid number!");
                }
            }


            for (int j = 0; j < number; j++) {
                System.out.println("Please give one possible transition for state " + state.name);
                String in = br.readLine();
                Input input = new Input(in);
                System.out.println("Where can state " + state.name + "go with transition " + in);
                String next = br.readLine();

                if (checkState(next, states)) {
                    state.setTransitions(input, next);
                } else {
                    System.out.println("State not found");
                    j--;
                }
            }

        }
        System.out.println("debugprint");
    }

    public static boolean checkState(String name, List<State> states) {
        for (State state : states) {
            if (state.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}


