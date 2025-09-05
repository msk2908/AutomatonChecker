import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Nea default1 = new Nea(null);
        System.out.println("Use default automaton? y/n");
        String def = br.readLine();

        if (def.equals("n")) {
            System.out.println("Enter Automaton or RegEx? a/r");
            def = br.readLine();
            if (def.equals("a")) {
                default1 = enterAutomaton(br);
            } else {
                default1 = enterRegEx(br);
            }

        } else {
            default1 = setUpDefault();
        }
    }


    public static boolean checkState(String name, List<State> states) {
        for (State state : states) {
            if (state.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Nea setUpDefault() {
        Input a = new Input("a");
        Input b = new Input("b");
        HashMap aTransitions = new HashMap();
        HashMap bTransitions = new HashMap();

        State A = new State("A", aTransitions, false, true);
        State B = new State("B", bTransitions, true, false);
        A.setTransitions(a, "A");
        A.setTransitions(a, "A");
        A.setTransitions(a, "B");
        B.setTransitions(a, "A");
        B.setTransitions(b, "A");

        List<State> s1 = new ArrayList<>();
        s1.add(A);
        s1.add(B);
        return new Nea(s1);
    }

    public static Nea enterAutomaton(BufferedReader br) throws IOException {

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
                //make sure to have only one starting state
                System.out.println("Is the state " + state + " a starting state? y/n ");
                starting = br.readLine().equals("y");
            }

            State currentState = new State(state, new HashMap<>(), terminal, starting);
            states.add(currentState);

            System.out.println("Does your automaton contain more states? y/n: ");
            flag = br.readLine();
        }


        // insert all transitions
        List<State> stateList = new ArrayList<>();
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
            stateList.add(state);
        }
        return new Nea(stateList);
    }

    public static Nea enterRegEx(BufferedReader br) throws IOException {
        List<State> states = new ArrayList<>();

        System.out.println("Please input used alphabet, each input separated by ',': ");
        String alphabetStr = br.readLine();


        // convert input string into alphabet
        String input = "";
        List<String> buffer = new ArrayList<>();
        System.out.println(alphabetStr);
        for (Character letter : alphabetStr.toCharArray()) {
            if (letter == ',') {
                buffer.add(input);
                input = "";
            } else {
                if (letter == alphabetStr.toCharArray()[alphabetStr.length() - 1]) {
                    input += letter;
                    buffer.add(input);
                } else {
                    input += letter;
                }
            }
        }

        Alphabet alphabet = new Alphabet(buffer);


        //TODO convert input string into regex

        System.out.println("Please input RegEx, use e for epsilon: ");
        String regExStr = br.readLine();
        Pattern pattern = Pattern.compile(regExStr);
        Matcher matcher = pattern.matcher("ab");
        System.out.printf("Input: %-6s => Match: %s\n", regExStr, matcher.matches());

        RegEx regexABD = convertToSyntaxTree(regExStr.toCharArray(), "", "");
        System.out.println(regexABD);

        return new Nea(states);
    }

    public static RegEx convertToSyntaxTree(char[] regExChar, String evaluateLeft, String justRead) {
        char[] rest = regExChar;
        if (regExChar.length == 1) {
            return new RegEx(regExChar[0]);
        }
        for (Character character : regExChar) {
            String buf = "";
            for (int i = 1; i < rest.length; i++) {
                buf += rest[i];
            }
            rest = buf.toCharArray();
            System.out.println("rest " + buf);
            switch (character) {
                case '+': {
                    evaluateLeft += justRead;
                    return new Or(concat(evaluateLeft.toCharArray()), convertToSyntaxTree(rest, "", ""));
                }
                case '*': {
                    if (evaluateLeft.isEmpty()) {
                        return new Loop(convertToSyntaxTree(justRead.toCharArray(), "", justRead));
                    }
                    return new Concat(concat(evaluateLeft.toCharArray()), new Loop(convertToSyntaxTree(justRead.toCharArray(), "", justRead)));
                }

                default: {
                    evaluateLeft += justRead;
                    justRead = character.toString();
                    convertToSyntaxTree(rest, evaluateLeft, justRead);
                }
            }
        }
        return concat(evaluateLeft.toCharArray());
    }

    public static RegEx concat(char[] regex) {
        char[] rest = regex;
        String buf = "";
        for (int i = 1; i < rest.length; i++) {
            buf += rest[i];
        }
        rest = buf.toCharArray();

        if (rest.length > 0) {
            return new Concat(new RegEx(regex[0]), concat(rest));
        }
        return new RegEx(regex[0]);
    }
}


