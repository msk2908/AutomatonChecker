public class Concat extends RegEx {
    RegEx regExLeft;
    RegEx regExRight;
    String type;
    public Concat(RegEx regExLeft, RegEx regExRight) {
        this.regExLeft = regExLeft;
        this.regExRight = regExRight;
        this.type = "concat";
    }

    public String rToString() {
        return "Concat["+ regExLeft.rToString()+ "," + regExRight.rToString() + "]";
    }

    public RegEx getLeft() {
        return regExLeft;
    }

    public RegEx getRight() {
        return regExRight;
    }
}
