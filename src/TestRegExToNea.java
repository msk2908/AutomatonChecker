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
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>());
        System.out.println("created: " + nea.neaToString());
        List<State> states = new ArrayList<>();
        State state2 = new State("a final destination", new HashMap<>(), true, false);
        HashMap transitionsInitial = new HashMap();
        State initial = new State("a", transitionsInitial, false, true);
        initial.setTransitions(new Input("a", TransitionType.LITERAL), state2);
        states.add(initial);
        states.add(state2);
        Nea compare = new Nea(states);
        System.out.println("to compare: " + compare.neaToString());
        assertEquals(nea.neaToString(), compare.neaToString());
    }

    @Test
    public void testOrToNea() {
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>());
        System.out.println("created: \n" + nea.neaToString());
        List<State> states = new ArrayList<>();
        HashMap transitionsInitial = new HashMap();
        HashMap transitionsA = new HashMap();
        HashMap transitionsB = new HashMap();

        State a = new State("a", transitionsA, false, false);
        State b = new State("b", transitionsB, false, false);


        State aFinal = new State("a final destination", new HashMap<>(), true, false);
        State bFinal = new State("b final destination", new HashMap<>(), true, false);


        State initial = new State("Or[a,b]", transitionsInitial, false, true);


        a.setTransitions(new Input("a", TransitionType.LITERAL), aFinal);
        b.setTransitions(new Input("b", TransitionType.LITERAL), bFinal);
        initial.setTransitions(new Input("Epsilon", TransitionType.LITERAL), a);
        initial.setTransitions(new Input("Epsilon", TransitionType.LITERAL), b);


        states.add(initial);
        states.add(a);
        states.add(aFinal);
        states.add(b);
        states.add(bFinal);
        Nea compare = new Nea(states);
        System.out.println("to compare: \n" + compare.neaToString());
        assertEquals(nea.neaToString(), compare.neaToString());
    }

    @Test
    public void testConcatToNea() {

    }

    @Test
    public void testLoopToNea() {

    }

    //medium (two properties)

}
