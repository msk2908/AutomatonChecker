import java.util.Random;
import RegExClasses.*;


public class RegExCreator {
    // TODO check difficulty somehow

    /**
     * creates a random regular expression of the given length
     * @param depth the length of the expression
     * @return
     */
    public RegEx create(int depth) {
        Random rand = new Random();
        RegEx regEx = new RegEx('a');

        while (depth > 0) {
            int r = rand.nextInt(3);
            switch (r) {
                case 0 :  {
                    // add Concatenation
                    regEx = new Concat(regEx, new RegEx(getCharacter()));
                    break;
                }
                case 1 : {
                    // add Or
                    regEx = new Or(regEx, new RegEx(getCharacter()));
                    break;
                }
                case 2: {
                    // add Loop
                    regEx = new Loop(regEx);
                    break;
                }
            }
            depth--;
            r = rand.nextInt(3);
        }
        return regEx;
    }

    /**
     *
     * @return random character for new RegEx name
     */
    public char getCharacter() {
        Random r = new Random();
        return (char)(r.nextInt(26) + 'a');
    }



}
