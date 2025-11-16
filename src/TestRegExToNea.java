import RegExClasses.Concat;
import RegExClasses.Loop;
import RegExClasses.Or;
import RegExClasses.RegEx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestRegExToNea {

    // easy (only one property)
    @Test
    public void testLiteralToNea() {
        RegEx regEx = new RegEx('a');
        List<String > alphabetList = new ArrayList<>();
        alphabetList.add("a");
        Alphabet alphabet = new Alphabet(alphabetList);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(),alphabet);
        System.out.println("created: " + nea.neaToString(alphabet));
        List<State> states = new ArrayList<>();
        State state2 = new State(1, "a final", new HashMap<>(), true, false);
        HashMap<Input, List<State>> transitionsInitial = new HashMap<>();
        State initial = new State(0, "a", transitionsInitial, false, true);
        initial.setTransitions(new Input("a", TransitionType.LITERAL), state2);
        states.add(initial);
        states.add(state2);
        Nea compare = new Nea(states, alphabet);
        System.out.println("to compare: " + compare.neaToString(alphabet));
        assertEquals(nea.neaToString(alphabet), compare.neaToString(alphabet));
    }

    @Test
    public void testEasyOrToNea() {
        //kinda fails sometimes because sometimes a gets put first, sometimes b (HashMap has no order) but this is fine (use alphabet...?)
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        System.out.println("created: \n" + nea.neaToString(alphabet));
        List<State> states = new ArrayList<>();
        HashMap<Input, List<State>> transitionsInitial = new HashMap<>();
        HashMap<Input, List<State>> transitionsA = new HashMap<>();
        HashMap<Input, List<State>> transitionsB = new HashMap<>();

        State a = new State(1,"a", transitionsA, false, false);
        State b = new State(2,"b", transitionsB, false, false);


        State aFinal = new State(3,"a final", new HashMap<>(), true, false);
        State bFinal = new State(4,"b final", new HashMap<>(), true, false);

        State initial = new State(0,"Or[a,b]", transitionsInitial, false, true);

        a.setTransitions(new Input("a", TransitionType.LITERAL), aFinal);
        b.setTransitions(new Input("b", TransitionType.LITERAL), bFinal);
        initial.setTransitions(new Input("Epsilon", TransitionType.LITERAL), a);
        initial.setTransitions(new Input("Epsilon", TransitionType.LITERAL), b);


        states.add(initial);
        states.add(a);
        states.add(b);
        states.add(aFinal);
        states.add(bFinal);
        Nea compare = new Nea(states, alphabet);
        System.out.println("to compare: \n" + compare.neaToString(alphabet));
        assertEquals(nea.neaToString(alphabet), compare.neaToString(alphabet));
    }

    @Test
    public void testEasyConcatToNea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegEx regEx = new Concat(new RegEx('a'), new RegEx('b'));
        State concatAB = new State(0,"Concat[a,b]", new HashMap<>(), false, true);
        State b = new State(1,"b", new HashMap<>(), false, false);
        State bFinal = new State(2,"b final", new HashMap<>(), true, false);
        concatAB.setTransitions(new Input("a", TransitionType.LITERAL), b);
        b.setTransitions(new Input("b", TransitionType.LITERAL), bFinal);
        List<State> states = new ArrayList<>();
        states.add(concatAB);
        states.add(b);
        states.add(bFinal);
        Nea compare = new Nea(states, alphabet);

        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        assertEquals(compare.neaToString(alphabet), nea.neaToString(alphabet));
    }

    @Test
    public void testEasyLoopToNea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegEx regEx = new Loop(new RegEx('a'));
        State loopA = new State(0,"Loop[a]", new HashMap<>(), true, true);
        State a = new State(1,"a final", new HashMap<>(), true, true);
        loopA.setTransitions(new Input("a", TransitionType.LITERAL), a);
        a.setTransitions(new Input("Epsilon", TransitionType.EPSILON), loopA);
        List<State> states = new ArrayList<>();
        states.add(loopA);
        states.add(a);
        Nea compare = new Nea(states, alphabet);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        System.out.println(compare.neaToString(alphabet));
        System.out.println(nea.neaToString(alphabet));
        assertEquals(compare.neaToString(alphabet), nea.neaToString(alphabet));

    }

    //medium (two properties)

    @Test
    public void testConcatAndOrLeft() {
        // ab+c
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        alphabetList.add("c");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegEx regEx = new Or(new Concat(new RegEx('a'), new RegEx('b')), new RegEx('c'));

        State initial = new State(0,"Or[Concat[a,b],c]", new HashMap<>(), false, true);
        State concat = new State(1,"Concat[a,b]", new HashMap<>(), false, false);
        State c = new State(2,"c", new HashMap<>(), false, false);
        State cFinal = new State(3,"c final", new HashMap<>(), true, false);
        State b = new State(4,"b", new HashMap<>(), false, false);
        State bFinal = new State(5,"b final", new HashMap<>(), true, false);



        initial.setTransitions(new Input("Epsilon", TransitionType.EPSILON), concat);
        initial.setTransitions(new Input("Epsilon", TransitionType.EPSILON), c);
        concat.setTransitions(new Input("a", TransitionType.LITERAL), b);
        b.setTransitions(new Input("b", TransitionType.LITERAL), bFinal);
        c.setTransitions(new Input("c", TransitionType.LITERAL), cFinal);

        List<State> states = new ArrayList<>();
        states.add(initial);
        states.add(concat);
        states.add(c);
        states.add(b);
        states.add(bFinal);
        states.add(cFinal);
        Nea compare = new Nea(states, alphabet);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        String comp = compare.neaToString(alphabet);
        System.out.println("expected: \n" + comp);
        String got = nea.neaToString(alphabet);
        System.out.println("got: \n" + got);
        assertEquals(comp,got);


    }

    @Test
    public void testConcatAndOrRight() {
        // a+bc
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        alphabetList.add("c");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegEx regEx = new Or(new RegEx('a'), new Concat(new RegEx('b'), new RegEx('c')));

        State initial = new State(0,"Or[a,Concat[b,c]]", new HashMap<>(), false, true);
        State concat = new State(2,"Concat[b,c]", new HashMap<>(), false, false);
        State a = new State(1,"a", new HashMap<>(), false, false);
        State aFinal = new State(3,"a final", new HashMap<>(), true, false);
        State c = new State(4,"c", new HashMap<>(), false, false);
        State cFinal = new State(5,"c final", new HashMap<>(), true, false);

        initial.setTransitions(new Input("Epsilon", TransitionType.EPSILON), a);
        initial.setTransitions(new Input("Epsilon", TransitionType.EPSILON), concat);
        concat.setTransitions(new Input("b", TransitionType.LITERAL), c);
        a.setTransitions(new Input("a", TransitionType.LITERAL), aFinal);
        c.setTransitions(new Input("c", TransitionType.LITERAL), cFinal);

        List<State> states = new ArrayList<>();
        states.add(initial);
        states.add(a);
        states.add(concat);
        states.add(aFinal);
        states.add(c);
        states.add(cFinal);
        Nea compare = new Nea(states, alphabet);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        String comp = compare.neaToString(alphabet);
        System.out.println("expected: \n" + comp);
        String got = nea.neaToString(alphabet);
        System.out.println("got: \n" + got);
        assertEquals(comp,got);

    }

    @Test
    public void testComplicated() {
        //does not actually test something because putting the automaton requires mental working
        //TODO make this test test something
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("Epsilon");
        Alphabet alphabet = new Alphabet(list);
        RegEx regEx = Main.convertToSyntaxTree("ab+(ab+c)*".toCharArray(), "", "");
        System.out.println(regEx);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        System.out.println("solution: \n" + nea.neaToString(alphabet));
    }

    @Test
    public void testComplicated2() {
        List<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        Alphabet alphabet = new Alphabet(strings);
        RegEx regEx = Main.convertToSyntaxTree("a+(b*+ab)*".toCharArray(), "", "");
        System.out.println(regEx.rToString());
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        System.out.println(nea.neaToString(alphabet));
    }
}
