import RegExClasses.RegEx;
import RegExClasses.RegExType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEqual {

    @Test
    public void testEqualVeryEasy() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualVeryEasy() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, new RegEx(RegExType.NONE), new ArrayList<>(), new Alphabet(new ArrayList<>())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEasy() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualConcat() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ab".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcat() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ac".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualLoopEasy() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a*".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualLoop() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("b*".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("a*".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualConcatAndLoop() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ab*".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualConcatAndLoop() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab*".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("a*b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOr() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("a+b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOr() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("a+b".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("b+b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualOrConcat() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualOrConcat() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab+b".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("bb+b".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testNotEqualEverything() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab*+b+c".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("b+b*c+ab".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualEverything() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ab*+b+cb".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testEqualEverything2() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("(ab*+b+cb)*".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("(ab*+b+cb)*".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertTrue(solutionChecker.compareDea(dea1, dea2));
    }

    @Test
    public void testTransitionInWrongOrder() throws Exception {
        RegExCreator regExCreator = new RegExCreator();
        SolutionChecker solutionChecker = new SolutionChecker();
        RegEx regEx = regExCreator.convertToSyntaxTree("ab".toCharArray(), null, null);
        RegEx regEx2 = regExCreator.convertToSyntaxTree("ba".toCharArray(), null, null);
        Dea dea1 = regExCreator.convertToNea(null, regEx, new ArrayList<>(), new Alphabet(regEx.getAlphabet())).convertNeaToDea();
        Dea dea2 = regExCreator.convertToNea(null, regEx2, new ArrayList<>(), new Alphabet(regEx2.getAlphabet())).convertNeaToDea();
        assertFalse(solutionChecker.compareDea(dea1, dea2));
    }

}
