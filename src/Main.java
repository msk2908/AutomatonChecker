import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Nea givenAutomaton = new Nea(null);
        System.out.println("Use default automaton? y/n");
        String def = br.readLine();

        if (def.equals("n")) {
            System.out.println("Enter Automaton or RegEx? a/r");
            def = br.readLine();
            if (def.equals("a")) {
                givenAutomaton = enterAutomaton(br);
            } else {
                givenAutomaton = enterRegEx(br);
            }

        } else {
            givenAutomaton = setUpDefault();
        }
        System.out.println("debugprint");
    }







    // helper functions

    public static boolean checkIfStateExists(String name, List<State> states) {
        for (State state : states) {
            if (state.name.equals(name)) {
                return true;
            }
        }
        System.out.println("state "+ name + " does not exist");
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
        Scanner scanner = new Scanner(System.in);

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

        // get input alphabet of automaton
        // TODO: fix input (e for epsilon, 'e' for e, ' ' for space)
        System.out.println("Please input Alphabet of automaton, inputs separated by space, e for epsilon, 'e' for e, ' ' for space ");
        String alphabetStr = scanner.nextLine();
        List<String> alphabetList = getInputFromList(alphabetStr);

        Alphabet alphabet = new Alphabet(alphabetList);
        System.out.println(alphabetList + "," + alphabet.aToString());

        // create all transitions
        for (State state : states) {
            for (Input input : alphabet.possibleInputs) {
                System.out.println("Where can state " + state.name + " go with transition " +
                        input.input + "? (input all possible next states separated by blank space)");
                List<String> statesToGo = getInputFromList(scanner.nextLine());
                for (String nextState : statesToGo) {
                    if (checkIfStateExists(nextState, states)) {
                        state.setTransitions(input, nextState);
                    }
                }
            }
        }

        return new Nea(states);
    }

    public static List<String> getInputFromList(String input) {
        // returns a list of inputs separated by spaces
        List<String> output = new ArrayList<>();
        String word = "";
        for (char character : input.toCharArray()) {
            switch (character) {
                case ' ' : {
                    output.add(word);
                    word = "";
                }
                case 'e': {
                    output.add("epsilon");
                }

                default: {
                    word += character;
                }
            }
        }
        if (!word.isEmpty()) {
            output.add(word);
        }

        return output;
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


        System.out.println("Please input RegEx, use e for epsilon: ");
        String regExStr = br.readLine();

        RegEx regexABD = convertToSyntaxTree(regExStr.toCharArray(), "", "");
        System.out.println(regexABD.rToString());

        return new Nea(states);
    }

    public static RegEx convertToSyntaxTree(char[] regExChar, String evaluateLeft, String justRead) {
        char[] rest = regExChar;
        if (regExChar.length == 1) {
            return new RegEx(regExChar[0]);
        }
        for (Character character : regExChar) {
            // do I really need this for-loop if only ever considering the first entry?
            String buf = "";
            for (int i = 1; i < rest.length; i++) {
                buf += rest[i];
            }
            rest = buf.toCharArray();

            switch (character) {
                case '(': {
                    /* if parenthesis open: loop until the same parenthesis does close again,
                     evaluate the expression in it (parenthesisEvaluated), then check if there is more expression (checkHowToGoOn)*/
                    String par = "";
                    int i = 0;
                    int counter = 0;
                    while (i < rest.length) {
                        // get everything inside the parenthesis
                        if (rest[i] == '(') {
                            counter += 1;
                        } else {
                            if (rest[i] == ')') {
                                if (counter == 0) {
                                    break;
                                } else {
                                    counter -= 1;
                                }
                            }
                        }
                        par += rest[i];
                        i++;
                    }

                    RegEx parenthesisEvaluated = convertToSyntaxTree(par.toCharArray(), "", "");
                    buf = "";
                    i += 1;
                    while (i < rest.length) {
                        buf += rest[i];
                        i++;
                    }
                    rest = buf.toCharArray();

                    return checkHowToGoOn(parenthesisEvaluated, rest);

                }
                case '+': {
                    evaluateLeft += justRead;
                    return new Or(concat(evaluateLeft.toCharArray()), convertToSyntaxTree(rest, "", ""));
                }
                case '*': {
                    if (evaluateLeft.isEmpty()) {
                        return new Concat(new Loop(new RegEx(justRead.toCharArray()[0])), convertToSyntaxTree(rest, "", ""));
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

    public static RegEx checkHowToGoOn(RegEx parenthesisEvaluated, char[] rest) {
        // checks what comes after the parenthesis to evaluate the whole expression
        if (rest.length == 0) {
            return parenthesisEvaluated;
        } else {
            char check = rest[0];
            String buf = "";
            for (int i = 1; i < rest.length; i++) {
                buf += rest[i];
            }
            rest = buf.toCharArray();
            switch (check) {
                case '*': {
                    //loops the whole parenthesis
                    return checkHowToGoOn(new Loop(parenthesisEvaluated), rest);
                }
                case '+': {
                    return new Or(parenthesisEvaluated, convertToSyntaxTree(rest, "", ""));
                }
                default: {
                    return new Concat(parenthesisEvaluated, convertToSyntaxTree(rest, "", ""));
                }
            }
        }
    }
}


