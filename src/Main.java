import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //TODO alphabet is wrong
        Nea default1 = new Nea(null, new Alphabet(new ArrayList<>()));
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


    public static State getStateByName(String name, List<State> states) {
        for (State state : states) {
            if (state.name.equals(name)) {
                return state;
            }
        }
        return null;
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
        //TODO insert useful alphabet, get from loop
        return new Nea(stateList, new Alphabet(new ArrayList<>()));
    }



    // RegEx functions
    // TODO check for correct syntax when interpreting switch e for ε epsilon etc
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


        System.out.println("Please input RegEx: ");
        String regExStr = br.readLine();

        RegEx regexABD = convertToSyntaxTree(regExStr.toCharArray(), "", "");
        System.out.println(regexABD.rToString());

        Nea nea = convertToNea(null, regexABD, new ArrayList<>(), alphabet);

        return nea;

    }

    /**
     * converts a given RegEx into a syntax tree
     **/
    public static RegEx convertToSyntaxTree(char[] regExChar, String evaluateLeft, String justRead) {
        char[] rest = regExChar;
        if (regExChar.length == 0 && evaluateLeft.isEmpty()) {
            try {
                return new RegEx(justRead.toCharArray()[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                // catches case "a+" (missing argument on right side of Or)
                System.out.println("not enough arguments");
            }

        }
        if (regExChar.length == 1) {
            return new RegEx(regExChar[0]);
        }
        for (Character character : regExChar) {
            // do I really need this for-loop if only ever considering the first entry?
            StringBuilder buf = new StringBuilder();
            for (int i = 1; i < rest.length; i++) {
                buf.append(rest[i]);
            }
            rest = buf.toString().toCharArray();

            switch (character) {
                case '(': {
                    /* if parenthesis open: loop until the same parenthesis does close again,
                     evaluate the expression in it (parenthesisEvaluated), then check if there is more expression (checkHowToGoOn)*/
                    StringBuilder par = new StringBuilder();
                    int i = 0;
                    int counter = 0; //counts the amount of opened parenthesis to not close the first one too early
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
                        par.append(rest[i]);
                        i++;
                    }

                    RegEx parenthesisEvaluated = convertToSyntaxTree(par.toString().toCharArray(), "", "");
                    buf = new StringBuilder();
                    i += 1;
                    while (i < rest.length) {
                        buf.append(rest[i]);
                        i++;
                    }
                    rest = buf.toString().toCharArray();
                    if (!evaluateLeft.isEmpty() || !justRead.isEmpty()) {
                        //RegEx evaluateEverythingInParenthesis = convertToSyntaxTree(par.toCharArray(), "", "");
                        parenthesisEvaluated = new Concat(concat(evaluateLeft.concat(justRead).toCharArray()), checkHowToGoOn(parenthesisEvaluated, rest));
                    } else {
                        parenthesisEvaluated = checkHowToGoOn(parenthesisEvaluated, rest);
                    }


                    return parenthesisEvaluated;

                }
                case '+': {
                    evaluateLeft += justRead;
                    try {
                        RegEx rightSide = convertToSyntaxTree(rest, "", "");
                        return new Or(concat(evaluateLeft.toCharArray()), rightSide);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Or does not have enough arguments");
                    }

                }
                case '*': {
                    if (evaluateLeft.isEmpty() && rest.length > 0) {
                        RegEx loop = new Loop(new RegEx(justRead.toCharArray()[0]));
                        return checkHowToGoOn(loop, rest);
                        //return new Concat(new , convertToSyntaxTree(rest, "", ""));
                    }
                    if (evaluateLeft.isEmpty()) {
                        return new Loop(new RegEx(justRead.toCharArray()[0]));
                    }
                    RegEx loop = new Concat(concat(evaluateLeft.toCharArray()), new Loop(convertToSyntaxTree(justRead.toCharArray(), "", justRead)));
                    return checkHowToGoOn(loop, rest);
                }

                default: {
                    evaluateLeft += justRead;
                    justRead = character.toString();
                    convertToSyntaxTree(rest, evaluateLeft, justRead);
                    break;
                }
            }
        }
        if (justRead.equals(")")) {
            return concat(evaluateLeft.toCharArray());
        }
        return concat(evaluateLeft.concat(justRead).toCharArray());
    }


    /**
     * helper function for convertToSyntaxTree, puts two parts of a RegEx together
     **/
    public static RegEx concat(char[] regex) throws IllegalArgumentException {
        char[] rest = regex;
        StringBuilder buf = new StringBuilder();
        try {
            for (int i = 1; i < rest.length; i++) {
                buf.append(rest[i]);
            }
            rest = buf.toString().toCharArray();

            if (rest.length > 0) {
                return new Concat(new RegEx(regex[0]), concat(rest));
            }
            return new RegEx(regex[0]);
        } catch (Exception e) {
            // catches case "+b" (missing argument on the left side of Or)
            throw new IllegalArgumentException("not enough arguments");
        }

    }

    /**
     * helper function, checks if there is something to do after evaluating a parenthesis or the inside of a Loop
     **/
    public static RegEx checkHowToGoOn(RegEx parenthesisEvaluated, char[] rest) {
        // checks what comes after the parenthesis to evaluate the whole expression
        if (rest.length == 0) {
            return parenthesisEvaluated;
        } else {
            char check = rest[0];
            StringBuilder buf = new StringBuilder();
            for (int i = 1; i < rest.length; i++) {
                buf.append(rest[i]);
            }
            rest = buf.toString().toCharArray();
            switch (check) {
                case '*': {
                    //loops the whole parenthesis
                    return checkHowToGoOn(new Loop(parenthesisEvaluated), rest);
                }
                case '+': {
                    return new Or(parenthesisEvaluated, convertToSyntaxTree(rest, "", ""));
                }
                default: {
                    String all = check + buf.toString();
                    return new Concat(parenthesisEvaluated, convertToSyntaxTree(all.toCharArray(), "", ""));
                }
            }
        }
    }


    /**
     * converts a given RegEx to a Nea
     **/
    public static Nea convertToNea(State actualState, RegEx regEx, List<State> states, Alphabet alphabet) {
        if (actualState == null) {
            //create starting state
            actualState = new State(setId(states), regEx.rToString(), new HashMap<>(), false, true);
        }
        if (!states.contains(actualState)) {
            states.add(actualState);
        }


        switch (regEx.type) {
            case RegExType.OR: {
                State left;
                State right;
                // create two follow-up states with an Epsilon-Transition
                RegExType type = regEx.getRight().type;

                left = new State(setId(states), regEx.getLeft().rToString(), new HashMap<>(), false, false);

                states.add(left);

                right = new State(setId(states), regEx.getRight().rToString(), new HashMap<>(), false, false);

                states.add(right);


                actualState.setTransitions(alphabet.get("Epsilon"), left);
                actualState.setTransitions(alphabet.get("Epsilon"), right);


                System.out.println(regEx.getLeft().rToString());
                System.out.println(regEx.getRight().rToString());
                convertToNea(left, regEx.getLeft(), states, alphabet);
                convertToNea(right, regEx.getRight(), states, alphabet);
                break;

            }

            case RegExType.CONCAT: {
                RegEx left = regEx.getLeft();
                RegEx right = regEx.getRight(); // is never empty or broken (will be checked by concat() when inputting RegEx)
                Nea evaluateLeft;
                if (Objects.requireNonNull(left.type) == RegExType.LITERAL) {// create one new state with transition to right
                    State next = new State(setId(states), right.rToString(), new HashMap<>(), false, false);
                    actualState.setTransitions(alphabet.get(left.a.toString()), next);
                    convertToNea(next, right, states, alphabet);
                } else {
                    evaluateLeft = convertToNea(actualState, left, states, alphabet);
                    //get last states of the left side of concatenation to go on here
                    List<State> followUpStates = new ArrayList<>();
                    for (State state : evaluateLeft.states) {
                        if (state.terminal) {
                            state.setTerminal(false);
                            followUpStates.add(state);
                        }
                    }


                    for (State state : followUpStates) {
                        convertToNea(state, right, states, alphabet);
                    }
                }
                break;
            }
            case RegExType.LITERAL: {
                //TODO might also be starting or non-final, check if actualState already has transitions or there is a follow-up (eg (a+b)*c))
                State last = new State(setId(states), regEx.rToString() + " final", new HashMap<>(), true, false);
                actualState.setTransitions(alphabet.get(regEx.rToString()), last);
                states.add(last);
                break;
            }
            case RegExType.LOOP: {
                // evaluate everything and put an Epsilon-Transition to starting state
                RegEx inside = regEx.getRegEx();
                List<State> saveStates = new ArrayList<>();
                saveStates.addAll(states);
                Nea evaluateLoop = convertToNea(actualState, inside, states, alphabet);

                        //get last states of the left side of concatenation to go on here
                List<State> followUpStates = new ArrayList<>();
                for (State state : evaluateLoop.states) {
                    if (state.terminal && !saveStates.contains(state)) {
                        //state.setTerminal(false);
                        followUpStates.add(state);
                    }
                }
                for (State state : followUpStates) {
                    state.setTransitions(alphabet.get("Epsilon"), actualState);
                }
            }
        }

        return new Nea(states, alphabet);
    }

    /**
     * sets the id of states based on the existing number of states (kinda useless, but fine)
     */
    public static int setId(List<State> states) {
        return states.size();
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




