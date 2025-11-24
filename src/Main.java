import RegExClasses.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner reader = new Scanner(System.in);
        System.out.println("create new exercise or check solution? e/s");
        String def = br.readLine();


        if (def.equals("e")) {
            // create a new RegEx to find a dea for
            System.out.println("select a difficulty: ");
            int depth = reader.nextInt();
            if (depth <= 0) {
                System.out.println("looks a little too easy");
            } else {
                //createNewExercise(depth, br);
                Challenge challenge = new Challenge();
                challenge.startGame(depth);
            }

        } else {
            // check solution to given exercise
            System.out.println("Please enter regex to check automaton for: ");
            String regExS = reader.nextLine();
            RegEx regEx = regExCreator.convertToSyntaxTree(regExS.toCharArray(), "", "");
            Alphabet alphabet = new Alphabet(regEx.getAlphabet());
            Nea automatonFromRegEx = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
            Dea deaFromRegEx = automatonFromRegEx.convertNeaToDea();
            SolutionChecker solutionChecker = new SolutionChecker();
            solutionChecker.checkSolution(regEx, alphabet, deaFromRegEx, br);


        }

    }




















    public static Nea setUpDefault() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        Input a = new Input("a", TransitionType.LITERAL);
        Input b = new Input("b", TransitionType.LITERAL);
        HashMap<Input, List<State>> aTransitions = new HashMap<>();
        HashMap<Input, List<State>> bTransitions = new HashMap<>();

        State A = new State(0, "A", aTransitions, false, true);
        State B = new State(1, "B", bTransitions, true, false);
        A.setTransitions(a, A);
        A.setTransitions(a, A);
        A.setTransitions(a, B);
        B.setTransitions(a, A);
        B.setTransitions(b, A);

        List<State> s1 = new ArrayList<>();
        s1.add(A);
        s1.add(B);
        return new Nea(s1, alphabet);
    }

    // TODO test this
    public static Nea enterAutomaton(BufferedReader br) throws IOException {

        boolean starting = false;
        String flag = "y";
        List<State> states = new ArrayList<>();
        int stateId = 0;

        while (flag.equals("y")) {

            //get states one by one
            List<String> StateNamelist = new ArrayList<>();
            System.out.print("Please input one state: ");
            String state;
            state = br.readLine();

            StateNamelist.add(state);
            System.out.println(StateNamelist.getFirst());

            System.out.println("Is the state " + state + " terminal? y/n ");
            boolean terminal = br.readLine().equals("y");

            if (!starting) {
                //make sure to have only one starting state
                System.out.println("Is the state " + state + " a starting state? y/n ");
                starting = br.readLine().equals("y");
            }

            State currentState = new State(stateId, state, new HashMap<>(), terminal, starting);
            stateId++;
            states.add(currentState);

            System.out.println("Does your automaton contain more states? y/n: ");
            flag = br.readLine();
        }


        // insert all transitions
        List<State> stateList = new ArrayList<>();
        List<String> alphabetList = new ArrayList<>();
        for (State state : states) {
            // get transitions for every state, put into Hashmap
            // Creates a reader instance which takes
            // input from standard input - keyboard

            Scanner reader = new Scanner(System.in);

            // nextInt() reads the next integer from the keyboard
            int number = -1;
            while (true) {
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
                if (!alphabetList.contains(in)) {
                    alphabetList.add(in);
                }
                Input input = new Input(in, checkTransitionType(in));
                System.out.println("Where can state " + state.name + "go with transition " + in);
                String next = br.readLine();

                State nextState = getStateByName(next, states);
                if (nextState != null) {
                    state.setTransitions(input, nextState);
                } else {
                    System.out.println("State not found");
                    j--;
                }
            }
            stateList.add(state);
        }

        return new Nea(stateList, new Alphabet(alphabetList));
    }

    public static State getStateByName(String name, List<State> states) {
        for (State state : states) {
            if (state.name.equals(name)) {
                return state;
            }
        }
        return null;
    }



    // RegEx functions
    // RegEx functions
    public static Nea enterRegEx(BufferedReader br) throws IOException {
        RegExCreator regExCreator = new RegExCreator();
        List<State> states = new ArrayList<>();

        System.out.println("Please input used alphabet, each input separated by ',' (Epsilon exists per default): ");
        String alphabetStr = br.readLine();


        // convert input string into alphabet
        String input = "";
        List<String> buffer = new ArrayList<>();
        System.out.println(alphabetStr);
        for (Character letter : alphabetStr.toCharArray()) {
            if (letter == ',') {
                buffer.add(input);
                input = "";
            }
            if (letter == alphabetStr.toCharArray()[alphabetStr.length() - 1]) {
                input += letter;
                buffer.add(input);
            } else {
                input += letter;
            }

        }

        Alphabet alphabet = new Alphabet(buffer);


        System.out.println("Please input RegEx: ");
        String regExStr = br.readLine();

        RegEx regexABD = regExCreator.convertToSyntaxTree(regExStr.toCharArray(), "", "");
        System.out.println(regexABD.rToString());
        Nea nea = regExCreator.convertToNea(null, regexABD, new ArrayList<>(), alphabet);

        return nea;

    }

    /**
     * returns the type of the given transition if e would be used as Epsilon
     */
    public static TransitionType checkTransitionType(String in) {
        if (in.equals("e") || in.equals("Epsilon")) {
            return TransitionType.EPSILON;
        } else {
            return TransitionType.LITERAL;
        }
    }
}




