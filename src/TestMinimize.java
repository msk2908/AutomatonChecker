import RegExClasses.RegEx;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestMinimize {

    @Test
    public void testMinimizeLoopAndOrNeaToDea() {
        RegExCreator regExCreator = new RegExCreator();
        List<String> alphabetList = new ArrayList<>();
        alphabetList.add("a");
        alphabetList.add("b");
        Alphabet alphabet = new Alphabet(alphabetList);
        Nea nea = regExCreator.convertToNea(null, regExCreator.convertToSyntaxTree("(a+b)*".toCharArray(), "", ""), new ArrayList<>(), alphabet);
        //nea.drawNea();
        Dea dea = nea.convertNeaToDea();
        dea.drawDea();
        dea.minimize();
        dea.drawDea();
        keepOpen();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }

    @Test
    public void testMinimizeComplicated() {
        //does not actually test something because putting the automaton requires mental working
        RegExCreator regExCreator = new RegExCreator();
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
        dea.drawDea();
        dea.minimize();
        dea.drawDea();
        keepOpen();
        System.out.println("Dea: \n" + dea.deaToString(alphabet));
    }

    private void keepOpen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER zum Beenden...");
        scanner.nextLine();
        System.exit(0);
    }


}
