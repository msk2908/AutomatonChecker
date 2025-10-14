package RegExClasses;

public class Loop extends RegEx {
    RegEx regEx;
    RegExType type;
    public Loop(RegEx regEx) {
        super(RegExType.LOOP);
        this.regEx = regEx;
    }

    public String rToString() {
        return "RegExClasses.Loop[" + regEx.rToString() + "]" ;
    }

    public RegEx getRegEx() {
        return regEx;
    }
}
