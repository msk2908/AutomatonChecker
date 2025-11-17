import RegExClasses.Concat;
import RegExClasses.Loop;
import RegExClasses.Or;
import RegExClasses.RegEx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestsRegExOnly {
    RegExCreator regExCreator = new RegExCreator();

    @Test
    public void testOrSimpleRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "a+b".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesSimpleRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(a+b)".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testOrWithTwoBracesSimpleRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "((a+b))".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesNotSoSimpleRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(a+(a+b))".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new Or(new RegEx('a'), new RegEx('b')));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesNotSoSimpleRegExTree2() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "((a+b)+a)".toCharArray();
        RegEx regEx = new Or(new Or(new RegEx('a'), new RegEx('b')), new RegEx('a'));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesAndLoopRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(a+(a+b)*)".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new Loop(new Or(new RegEx('a'), new RegEx('b'))));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesAndLoopRandomShitGoingOnHereRegExTree() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(a+(a+b)*+(a*b)*(ab))".toCharArray();
        RegEx firstBrace = new Loop(new Or(new RegEx('a'), new RegEx('b')));
        RegEx secondBrace = new Loop(new Concat( new Loop(new RegEx('a')), new RegEx('b')));
        RegEx concatenation = new Concat(secondBrace, new Concat(new RegEx('a'), new RegEx('b')));
        RegEx regEx = new Or(new RegEx('a'), new Or(firstBrace, concatenation));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithConcat() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(abc)*".toCharArray();
        RegEx regEx = new Loop(new Concat(new RegEx('a'), new Concat( new RegEx('b'), new RegEx('c'))));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithOr() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(ab+c)*".toCharArray();
        RegEx regEx = new Loop(new Or(new Concat(new RegEx('a'),  new RegEx('b')), new RegEx('c')));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithLoop() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(b*+c)*".toCharArray();
        RegEx regEx = new Loop(new Or(new Loop(new RegEx('b')), new RegEx('c')));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithOr2() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(b*+c)".toCharArray();
        RegEx regEx = new Or(new Loop(new RegEx('b')), new RegEx('c'));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenlvl3() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "ab(ab+b*)".toCharArray();
        RegEx brace = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        RegEx c1 = new Concat(new Concat(new RegEx('a'), new RegEx('b')), brace);
        System.out.println(c1);
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }
    @Test
    public void testConcatenationSeemsToBeBrokenlvl2p5() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "b(ab+b*)".toCharArray();
        RegEx brace = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        RegEx c1 = new Concat(new RegEx('b'), brace);
        System.out.println(c1);
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenlvl2() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "(ab+b*)".toCharArray();
        RegEx c1 = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        System.out.println(c1);
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenLvl1() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "abc".toCharArray();
        RegEx c1 = new Concat(new RegEx('a'), new Concat(new RegEx('b'), new RegEx('c')));
        System.out.println(c1);
        RegEx result = regExCreator.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithBracesBcIFuckedItUp() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "ab(c+d)*".toCharArray();
        RegEx right = new Loop(new Or(new RegEx('c'), new RegEx('d')));
        RegEx regEx = new Concat(new Concat(new RegEx('a'), (new RegEx('b'))), right);
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenLvl2() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "a+(b*+ab)*".toCharArray();
        RegEx braces = new Or(new Loop(new RegEx('b')), new Concat(new RegEx('a'), new RegEx('b')));
        RegEx regEx = new Or(new RegEx('a'), new Loop(braces));
        System.out.println(regEx.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testFinal() {
        RegExCreator regExCreator = new RegExCreator();
        char[] regExChar = "a+b+ab+b*+(ab*+b*)*".toCharArray();
        RegEx braces = new Loop(new Or(new Concat(new RegEx('a'), new Loop(new RegEx('b'))), new Loop(new RegEx('b'))));
        RegEx loopB = new Loop(new RegEx('b'));
        RegEx concat = new Concat(new RegEx('a'), new RegEx('b'));
        RegEx all = new Or(new RegEx('a'), new Or(new RegEx('b'), new Or(concat, new Or(loopB, braces))));
        System.out.println(all.rToString());
        RegEx result = regExCreator.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(all.rToString(), result.rToString());
    }

    @Test
    public void testGetAlphabet() {
        RegExCreator regExCreator = new RegExCreator();
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        alphabetList.add("c");
        Alphabet check = new Alphabet(alphabetList);
        RegEx regEx = regExCreator.convertToSyntaxTree("ab(ac+b*)".toCharArray(), "", "");
        Alphabet alphabet = Main.getAlphabet(regEx);
        System.out.println(alphabet.aToString());
        System.out.println(check.aToString());
        assertEquals(alphabet.aToString(), check.aToString());
    }

}