package RegExClasses;

import java.util.ArrayList;
import java.util.List;

public class RegEx {
    Character a;
    RegExType type;
    public RegEx(RegExType type) {
        this.type = type;
    }

    public RegEx(Character a) {
        this.a = a;
        this.type = RegExType.LITERAL;
    }

    public String rToString() {
        if (this.type.equals(RegExType.NONE)) {
            a =' ';
        }
        return a.toString();
    }
    public RegEx getLeft() { return this;}
    public RegEx getRight() { return this;}
    public RegEx getRegEx() { return this;}

    public Character getA() {
        return this.a;
    }

    public RegExType getType() {
        return this.type;
    }

    public List<String> getAlphabet() {
        List<String> alphabetList = new ArrayList<>();
        switch (this.type) {
            case RegExType.LITERAL : {
                alphabetList.add(this.a.toString());
            }
            case RegExType.LOOP: {
                alphabetList.addAll(this.getRegEx().getAlphabet());
            }
            case RegExType.CONCAT: {
                alphabetList.addAll(this.getRight().getAlphabet());
                alphabetList.addAll(this.getLeft().getAlphabet());
            }
            case RegExType.OR: {
                alphabetList.addAll(this.getRight().getAlphabet());
                alphabetList.addAll(this.getLeft().getAlphabet());
            }

        }
        return alphabetList;
    }

}
