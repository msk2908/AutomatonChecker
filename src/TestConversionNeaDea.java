
import RegExClasses.RegEx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class TestConversionNeaDea {

    @Test
    public void testLiteralNeaToDea() {

        RegEx regEx = new RegEx('a');
        List<String > alphabetList = new ArrayList<>();
        alphabetList.add("a");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }

    @Test
    public void testEasyLoopNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("a*".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        System.out.println(nea.neaToString(alphabet));
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.minimize();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }

    @Test
    public void testEasyConcatNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("aa".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        //nea.drawNea();
        System.out.println("Nea: \n"+ nea.neaToString(alphabet));
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }

    @Test
    public void testEasyOrNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("a+b".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        System.out.println("Nea: \n"+ nea.neaToString(alphabet));
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }


    @Test
    public void testOrAndConcatNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("ab+a".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }


    @Test
    public void testOrAndBraceNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("(a+b)+a".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }


    @Test
    public void testLoopAndOrNeaToDea() {
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        RegExCreator regExCreator = new RegExCreator();
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("(a+b)*".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }


    @Test
    public void testNeaToDeaComplicated() {
        RegExCreator regExCreator = new RegExCreator();
        //does not actually test something because putting the automaton requires mental working
        //TODO make this test test something
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Alphabet alphabet = new Alphabet(list);
        RegEx regEx = regExCreator.convertToSyntaxTree("ab+(ab+c)*".toCharArray(), "", "");
        System.out.println(regEx);
        Nea nea = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        //nea.drawNea();
        System.out.println("Nea: \n" + nea.neaToString(alphabet));

        Dea dea = nea.convertNeaToDea();
        dea.minimize();
        dea.drawDea();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }


}
