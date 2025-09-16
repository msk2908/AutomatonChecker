import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestRegExToNea {

    // easy (only one property)
    @Test
    public void testLiteralToNea() {
        RegEx regEx = new RegEx('a');
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>());
        System.out.println(nea.aToString());
        List<State> states = new ArrayList<>();
        State state2 = new State("a final destination", new HashMap<>(), true, false);
        HashMap transitionsInitial = new HashMap();
        State initial = new State("a", transitionsInitial, false, true);
        initial.setTransitions(new Input("a", TransitionType.LITERAL), state2.name);
        states.add(initial);
        states.add(state2);
        Nea compare = new Nea(states);
        assertEquals(compare, nea);
    }

    @Test
    public void testOrToNea() {

    }

    @Test
    public void testConcatToNea() {

    }

    @Test
    public void testLoopToNea() {

    }

    //medium (two properties)

}
