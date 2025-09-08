import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests {
    @Test
    public void testATest() {
        assertEquals(0,0);
    }

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
        assertEquals(result.rToString(), regEx.rToString());
    }

}