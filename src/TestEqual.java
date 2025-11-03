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

    @Test
    public void testEqualConcat() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcat() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ac".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualLoopEasy() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualLoop() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("b*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualConcatAndLoop() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcatAndLoop() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a*b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOr() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOr() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOrConcat() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOrConcat() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("bb+b".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEverything() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*+b+c".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("b+b*c+ab".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualEverything() {
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = Main.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        RegEx regEx2 = Main.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        Dea dea1 = Main.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = Main.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

}
