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

        return "RegExClasses.Or[" + left + "," + right + "]";
    }

    public RegEx getLeft() {
        return regExLeft;
    }

    public RegEx getRight() {
        return regExRight;
    }
}
