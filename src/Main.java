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

            System.out.println("Is the state " + state + " a starting state? y/n ");
            boolean starting = br.readLine().equals("y");

            State currentState = new State(state, new HashMap<>(), terminal, starting);
            states.add(currentState);

            System.out.println("Does your automaton contain more states? y/n: ");
            flag = br.readLine();
        }


        // insert all transitions

        for (State state : states) {
            // get transitions for every state, put into Hashmap
            System.out.println("How many transitions does " + state.name + " have? ");
            // Creates a reader instance which takes
            // input from standard input - keyboard
            Scanner reader = new Scanner(System.in);

            // nextInt() reads the next integer from the keyboard
            int number = reader.nextInt();

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
        System.out.println("lol");

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


