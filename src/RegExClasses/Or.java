package RegExClasses;

public class Or extends RegEx {
    RegEx regExLeft;
    RegEx regExRight;
    RegExType type;

    public Or(RegEx regExLeft, RegEx regExRight) {
        super(RegExType.OR);
        this.regExLeft = regExLeft;
        this.regExRight = regExRight;
    }

    public String rToString() {
        String left = "";
        String right = "";
        if (!regExLeft.type.equals(RegExType.NONE)) {
            left = regExLeft.rToString();
        }
        if (!regExRight.type.equals(RegExType.NONE)) {
            right = regExRight.rToString();
        }

        return "Or[" + left + "," + right + "]";
    }

    public String printRegEx() {
        return regExLeft.printRegEx() + "+" + regExRight.getRegEx().printRegEx();
    }

    public RegEx getLeft() {
        return regExLeft;
    }

    public RegEx getRight() {
        return regExRight;
    }
}
