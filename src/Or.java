public class Or extends RegEx {
    RegEx regEx;
    RegEx regEx2;
    public Or(RegEx regex, RegEx regEx2) {
        this.regEx = regex;
        this.regEx2 = regEx2;
    }
}
