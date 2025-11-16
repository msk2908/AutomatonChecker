import RegExClasses.RegEx;
import RegExClasses.RegExType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEqual {

    @Test
    public void testEqualVeryEasy() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualVeryEasy() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, new RegEx(RegExType.NONE), new ArrayList<>(), new Alphabet(new ArrayList<>())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEasy() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualConcat() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcat() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ac".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualLoopEasy() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualLoop() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("b*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualConcatAndLoop() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcatAndLoop() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOr() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOr() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOrConcat() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOrConcat() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("bb+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEverything() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*+b+c".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b+b*c+ab".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualEverything() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testTransitionInWrongOrder() throws Exception {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ba".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

}
