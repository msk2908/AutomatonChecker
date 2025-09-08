public class Or extends RegEx {
    RegEx regEx;
    RegEx regEx2;
    String type;
    public Or(RegEx regex, RegEx regEx2) {
        this.regEx = regex;
        this.regEx2 = regEx2;
        this.type = "or";
    }

    public String rToString() {
        return "Or["+ regEx.rToString() + "," + regEx2.rToString() + "]";
    }
}
