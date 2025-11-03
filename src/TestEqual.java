import RegExClasses.RegEx;
import RegExClasses.RegExType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEqual {

    @Test
    public void testEqualVeryEasy() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualVeryEasy() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, new RegEx(RegExType.NONE), new ArrayList<>(), new Alphabet(new ArrayList<>())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEasy() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }
}
