public class RegEx {
    Character a;
    String type;
    public RegEx() {
        this.type = "";
    }

    public RegEx(Character a) {
        this.a = a;
        this.type = "literal";
    }

    public String rToString() {
       return a.toString();
    }
}
