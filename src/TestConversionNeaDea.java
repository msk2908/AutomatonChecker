import org.junit.Test;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestConversionNeaDea {

    @Test
    public void testLiteralNeaToDea() {
        RegEx regEx = new RegEx('a');
        List<String > alphabetList = new ArrayList<>();
        alphabetList.add("a");
        Alphabet alphabet = new Alphabet(alphabetList);
        Nea nea = Main.convertToNea(null, regEx, new ArrayList<>(), alphabet);

        nea.convertNeaToDea();

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


        Dea dea = nea.convertNeaToDea();

        System.out.println(dea.deaToString(alphabet));
    }


}
