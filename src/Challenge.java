import RegExClasses.RegEx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Challenge {

    public void play(PlayerStats playerStats) throws Exception {
        // start on level 1, if no level given
        // level up, when points == 3
        // reset points when failing
        // count failures, level down if failing three times in a row
        SolutionChecker solutionchecker = new SolutionChecker();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String goOn = "";
        if (createNewExercise(playerStats.getLevel(), br)) {
            playerStats.win();
            System.out.println("Correct!");
        } else {
            playerStats.lose();
            System.out.println("Sorry, something was missing");
        }
        System.out.println("Go on? y/n");
        goOn = br.readLine();
        if (goOn.equals("y")) {
            play(playerStats);
        }

    }

    public void startGame() throws Exception {
        PlayerStats playerStats = new PlayerStats();
        play(playerStats);
    }

    public void startGame(int level) throws Exception {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setLevel(level);
        if (level > 0) {
            play(playerStats);
        } else {
            throw new Exception("Level can't be smaller than 1");
        }
    }


    public boolean createNewExercise(int depth, BufferedReader br) throws Exception {
        //BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
        RegExCreator regExCreator = new RegExCreator();
        RegEx regEx = regExCreator.create(depth);
        Alphabet alphabet = new Alphabet(regEx.getAlphabet());
        Nea nea = regExCreator.convertToNea(null, regEx, new ArrayList<>(), alphabet);
        System.out.println("Exercise: create an automaton that recognizes : " + regEx.printRegEx());
        SolutionChecker solutionChecker = new SolutionChecker();
        return solutionChecker.checkSolution(regEx, regExCreator.getAlphabet(regEx), nea.convertNeaToDea(), br);


    }


}
