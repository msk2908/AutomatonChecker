public class Or extends RegEx {
    RegEx regExLeft;
    RegEx regExRight;
    String type;

    public Or(RegEx regExLeft, RegEx regExRight) {
        this.regExLeft = regExLeft;
        this.regExRight = regExRight;
        this.type = "or";
    }

    public String rToString() {
        return "Or[" + regExLeft.rToString() + "," + regExRight.rToString() + "]";
    }

    public RegEx getLeft() {
        return regExLeft;
    }

    public RegEx getRight() {
        return regExRight;
    }
}
