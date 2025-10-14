package RegExClasses;

public class RegEx {
    Character a;
    RegExType type;
    public RegEx(RegExType type) {
        this.type = type;
    }

    public RegEx(Character a) {
        this.a = a;
        this.type = RegExType.LITERAL;
    }

    public String rToString() {
        if (this.type.equals(RegExType.NONE)) {
            a =' ';
        }
        return a.toString();
    }
    public RegEx getLeft() { return this;}
    public RegEx getRight() { return this;}
    public RegEx getRegEx() { return this;}

    public Character getA() {
        return this.a;
    }

    public RegExType getType() {
        return this.type;
    }

}
