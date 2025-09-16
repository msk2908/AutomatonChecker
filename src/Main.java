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
        Input a = new Input("a", TransitionType.LITERAL);
        Input b = new Input("b", TransitionType.LITERAL);
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
                Input input = new Input(in, checkTransitionType(in));
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


    // RegEx functions
    // TODO check for correct syntax when interpreting switch e for epsilon etc ε
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
            }
            if (letter == alphabetStr.toCharArray()[alphabetStr.length() - 1]) {
                input += letter;
                buffer.add(input);
            } else {
                input += letter;
            }

        }

        Alphabet alphabet = new Alphabet(buffer);


        System.out.println("Please input RegEx, use e for epsilon: ");
        String regExStr = br.readLine();

        RegEx regexABD = convertToSyntaxTree(regExStr.toCharArray(), "", "");
        System.out.println(regexABD.rToString());

        Nea nea = convertToNea(null, regexABD, new ArrayList<>());

        return nea;

    }

    public static RegEx convertToSyntaxTree(char[] regExChar, String evaluateLeft, String justRead) {
        char[] rest = regExChar;
        if (regExChar.length == 0 && evaluateLeft.isEmpty()) {
            return new RegEx(justRead.toCharArray()[0]);
        }
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
                    if (evaluateLeft.isEmpty() && rest.length > 0) {
                        return new Concat(new Loop(new RegEx(justRead.toCharArray()[0])), convertToSyntaxTree(rest, "", ""));
                    }
                    if (evaluateLeft.isEmpty()) {
                        return new Loop(new RegEx(justRead.toCharArray()[0]));
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
        if (justRead.equals(")")) {
            return concat(evaluateLeft.toCharArray());
        }
        return concat(evaluateLeft.concat(justRead).toCharArray());
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

    public static Nea convertToNea(State actualState, RegEx regEx, List<State> states) {
        if (actualState == null) {
            //create starting state
            actualState = new State(regEx.rToString(), new HashMap<>(), false, true);
        }
        states.add(actualState);

        switch (regEx.type) {
            case RegExType.OR : {
                State left;
                State right;
                // create following states
                if (regEx.getLeft().equals(new RegEx(RegExType.NONE)) || regEx.getRight().equals(new RegEx(RegExType.NONE))) {
                    // mark state as final when there is no left or right
                    left = new State(regEx.getLeft().rToString(), new HashMap(), false, false);
                    right = new State(regEx.getRight().rToString(), new HashMap(), false, false);
                } else {
                    left = new State(regEx.getLeft().rToString(), new HashMap(), false, false);
                    right = new State(regEx.getRight().rToString(), new HashMap(), false, false);
                }
                actualState.setTransitions(new Input("Epsilon", TransitionType.EPSILON), left.name);
                actualState.setTransitions(new Input("Epsilon", TransitionType.EPSILON), right.name);


                System.out.println(regEx.getLeft().rToString());
                System.out.println(regEx.getRight().rToString());
                convertToNea(left, regEx.getLeft(), states);
                convertToNea(right, regEx.getRight(), states);
                break;

            }
            case RegExType.CONCAT:
                State left;
                State right;
                // create following states
                if (regEx.getLeft().equals(new RegEx(RegExType.NONE)) || regEx.getRight().equals(new RegEx(RegExType.NONE))) {
                    // mark state as final when there is no left or right
                    //TODO something is wrong here
                    left = new State(regEx.getLeft().rToString(), new HashMap(), false, false);
                    right = new State(regEx.getRight().rToString(), new HashMap(), false, false);
                } else {
                    left = new State(regEx.getLeft().rToString(), new HashMap(), false, false);
                    right = new State(regEx.getRight().rToString(), new HashMap(), false, false);
                }
                actualState.setTransitions(new Input("Epsilon", TransitionType.EPSILON), left.name);
                actualState.setTransitions(new Input("Epsilon", TransitionType.EPSILON), right.name);

                states.add(left);
                states.add(right);
                System.out.println(regEx.getLeft().rToString());
                System.out.println(regEx.getRight().rToString());
                convertToNea(left, regEx.getLeft(), states);
                convertToNea(right, regEx.getRight(), states);
                break;

            case RegExType.LITERAL: {
                //TODO might also be starting, check if actualState already has transitions
                State last = new State(regEx.rToString() + " final destination", new HashMap<>(), true, false);
                actualState.setTransitions(new Input (regEx.rToString(), TransitionType.LITERAL), last.name);
                states.add(last);
                break;
            }

            default: {
                /*State last = new State(regEx.rToString(), new HashMap<>(), true, false);
                actualState.setTransitions(new Input (regEx.rToString(), TransitionType.LITERAL), last.name +" sfinal destination");
                states.add(last);*/
                break;

            }
        }

        return new Nea(states);
    }

    public static TransitionType checkTransitionType(String in) {
        if (in.equals("e")) {
            return TransitionType.EPSILON;
        } else {
            return TransitionType.LITERAL;
        }
    }
}




