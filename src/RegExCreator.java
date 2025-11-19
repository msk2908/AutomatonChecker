import java.util.*;

import RegExClasses.*;


public class RegExCreator {
    // TODO check difficulty somehow

    /**
     * creates a random regular expression of the given length
     * @param depth the length of the expression
     * @return
     */
    public RegEx create(int depth) {
        Random rand = new Random();
        RegEx regEx = new RegEx('a');

        while (depth > 0) {
            int r = rand.nextInt(3);
            switch (r) {
                case 0 :  {
                    // add Concatenation
                    regEx = new Concat(regEx, new RegEx(getCharacter()));
                    break;
                }
                case 1 : {
                    // add Or
                    regEx = new Or(regEx, new RegEx(getCharacter()));
                    break;
                }
                case 2: {
                    // add Loop
                    regEx = new Loop(regEx);
                    break;
                }
            }
            depth--;
            r = rand.nextInt(3);
        }
        return regEx;
    }

    /**
     *
     * @return random character for new RegEx name
     */
    public char getCharacter() {
        Random r = new Random();
        return (char)(r.nextInt(26) + 'a');
    }

    /**
     * converts a given RegEx to a Nea
     **/
    public Nea convertToNea(State actualState, RegEx regEx, List<State> states, Alphabet alphabet) {
        if (actualState == null) {
            //create starting state
            actualState = new State(setId(states), regEx.rToString(), new HashMap<>(), false, true);
        }
        if (!states.contains(actualState)) {
            states.add(actualState);
        }


        switch (regEx.getType()) {
            case RegExType.OR: {
                State left;
                State right;
                // create two follow-up states with an Epsilon-Drawing.Transition
                RegExType type = regEx.getRight().getType();

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
                if (Objects.requireNonNull(left.getType()) == RegExType.LITERAL) {// create one new state with transition to right
                    State next = new State(setId(states), right.rToString(), new HashMap<>(), false, false);
                    actualState.setTransitions(alphabet.get(left.getA().toString()), next);
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
                // evaluate everything and put an Epsilon-Drawing.Transition to starting state
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
    public int setId(List<State> states) {
        return states.size();
    }



    // parse RegEx

    /**
     * converts a given RegEx into a syntax tree
     **/
    public RegEx convertToSyntaxTree(char[] regExChar, String evaluateLeft, String justRead) {
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
    public RegEx concat(char[] regex) throws IllegalArgumentException {
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
    public RegEx checkHowToGoOn(RegEx parenthesisEvaluated, char[] rest) {
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


    public Alphabet getAlphabet(RegEx regEx) {
        Alphabet alphabet = new Alphabet(new ArrayList<>());
        switch (regEx.getType()) {
            case LITERAL : {
                alphabet.add(regEx.getA().toString());
                break;
            }
            case LOOP: {
                alphabet.add(getAlphabet(regEx.getRegEx()));
                break;
            }
            default: {
                alphabet.add(getAlphabet(regEx.getLeft()));
                alphabet.add(getAlphabet(regEx.getRight()));
                break;
            }
        }
        return alphabet;
    }



}
