public class Loop extends RegEx{
    RegEx regEx;
    String type;
    public Loop(RegEx regEx) {
        super();
        this.regEx = regEx;
        this.type = "loop";
    }

    public String rToString() {
        return "Loop[" + regEx.rToString() + "]" ;
    }
}
