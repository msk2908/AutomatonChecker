package RegExClasses;

public class Concat extends RegEx {
    RegEx regExLeft;
    RegEx regExRight;
    RegExType type;
    public Concat(RegEx regExLeft, RegEx regExRight) {
        super(RegExType.CONCAT);
        this.regExLeft = regExLeft;
        this.regExRight = regExRight;
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
