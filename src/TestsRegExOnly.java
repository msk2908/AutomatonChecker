import RegExClasses.Concat;
import RegExClasses.Loop;
import RegExClasses.Or;
import RegExClasses.RegEx;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestsRegExOnly {

    @Test
    public void testOrSimpleRegExTree() {
        char[] regExChar = "a+b".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesSimpleRegExTree() {
        char[] regExChar = "(a+b)".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testOrWithTwoBracesSimpleRegExTree() {
        char[] regExChar = "((a+b))".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new RegEx('b'));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesNotSoSimpleRegExTree() {
        char[] regExChar = "(a+(a+b))".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new Or(new RegEx('a'), new RegEx('b')));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesNotSoSimpleRegExTree2() {
        char[] regExChar = "((a+b)+a)".toCharArray();
        RegEx regEx = new Or(new Or(new RegEx('a'), new RegEx('b')), new RegEx('a'));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesAndLoopRegExTree() {
        char[] regExChar = "(a+(a+b)*)".toCharArray();
        RegEx regEx = new Or(new RegEx('a'), new Loop(new Or(new RegEx('a'), new RegEx('b'))));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(result.rToString(), regEx.rToString());
    }

    @Test
    public void testOrWithBracesAndLoopRandomShitGoingOnHereRegExTree() {
        char[] regExChar = "(a+(a+b)*+(a*b)*(ab))".toCharArray();
        RegEx firstBrace = new Loop(new Or(new RegEx('a'), new RegEx('b')));
        RegEx secondBrace = new Loop(new Concat( new Loop(new RegEx('a')), new RegEx('b')));
        RegEx concatenation = new Concat(secondBrace, new Concat(new RegEx('a'), new RegEx('b')));
        RegEx regEx = new Or(new RegEx('a'), new Or(firstBrace, concatenation));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithConcat() {
        char[] regExChar = "(abc)*".toCharArray();
        RegEx regEx = new Loop(new Concat(new RegEx('a'), new Concat( new RegEx('b'), new RegEx('c'))));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithOr() {
        char[] regExChar = "(ab+c)*".toCharArray();
        RegEx regEx = new Loop(new Or(new Concat(new RegEx('a'),  new RegEx('b')), new RegEx('c')));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithLoop() {
        char[] regExChar = "(b*+c)*".toCharArray();
        RegEx regEx = new Loop(new Or(new Loop(new RegEx('b')), new RegEx('c')));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithOr2() {
        char[] regExChar = "(b*+c)".toCharArray();
        RegEx regEx = new Or(new Loop(new RegEx('b')), new RegEx('c'));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenlvl3() {
        char[] regExChar = "ab(ab+b*)".toCharArray();
        RegEx brace = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        RegEx c1 = new Concat(new Concat(new RegEx('a'), new RegEx('b')), brace);
        System.out.println(c1);
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }
    @Test
    public void testConcatenationSeemsToBeBrokenlvl2p5() {
        char[] regExChar = "b(ab+b*)".toCharArray();
        RegEx brace = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        RegEx c1 = new Concat(new RegEx('b'), brace);
        System.out.println(c1);
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenlvl2() {
        char[] regExChar = "(ab+b*)".toCharArray();
        RegEx c1 = new Or(new Concat(new RegEx('a'), new RegEx('b')), new Loop(new RegEx('b')));
        System.out.println(c1);
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenLvl1() {
        char[] regExChar = "abc".toCharArray();
        RegEx c1 = new Concat(new RegEx('a'), new Concat(new RegEx('b'), new RegEx('c')));
        System.out.println(c1);
        RegEx result = Main.convertToSyntaxTree(regExChar,"", "");
        System.out.println(result.rToString());
        assertEquals(c1.rToString(), result.rToString());
    }

    @Test
    public void testLoopWithBracesBcIFuckedItUp() {
        char[] regExChar = "ab(c+d)*".toCharArray();
        RegEx right = new Loop(new Or(new RegEx('c'), new RegEx('d')));
        RegEx regEx = new Concat(new Concat(new RegEx('a'), (new RegEx('b'))), right);
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testConcatenationSeemsToBeBrokenLvl2() {
        char[] regExChar = "a+(b*+ab)*".toCharArray();
        RegEx braces = new Or(new Loop(new RegEx('b')), new Concat(new RegEx('a'), new RegEx('b')));
        RegEx regEx = new Or(new RegEx('a'), new Loop(braces));
        System.out.println(regEx.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(regEx.rToString(), result.rToString());
    }

    @Test
    public void testFinal() {
        char[] regExChar = "a+b+ab+b*+(ab*+b*)*".toCharArray();
        RegEx braces = new Loop(new Or(new Concat(new RegEx('a'), new Loop(new RegEx('b'))), new Loop(new RegEx('b'))));
        RegEx loopB = new Loop(new RegEx('b'));
        RegEx concat = new Concat(new RegEx('a'), new RegEx('b'));
        RegEx all = new Or(new RegEx('a'), new Or(new RegEx('b'), new Or(concat, new Or(loopB, braces))));
        System.out.println(all.rToString());
        RegEx result = Main.convertToSyntaxTree(regExChar, "", "");
        System.out.println(result.rToString());
        assertEquals(all.rToString(), result.rToString());
    }

}