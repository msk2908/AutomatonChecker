public class Concat extends RegEx {
    RegEx regex;
    RegEx regex2;
    String type;
    public Concat(RegEx regex, RegEx regEx2) {
        this.regex = regex;
        this.regex2 = regEx2;
        this.type = "concat";
    }

    public String rToString() {
        return "Concat["+ regex.rToString()+ "," + regex2.rToString() + "]";
    }
}
