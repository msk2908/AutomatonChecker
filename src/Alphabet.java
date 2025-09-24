import java.util.ArrayList;
import java.util.List;

public class Alphabet {
    List<Input> possibleInputs;

    public Alphabet(List<String> possibleInputs) {

        int length = possibleInputs.size();
        List<Input> inputs = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (possibleInputs.get(i).equals("Epsilon")) {//"ε"
                inputs.add(new Input("Epsilon", TransitionType.EPSILON));
            } else {
                Input input = new Input(possibleInputs.get(i), TransitionType.LITERAL);
                inputs.add(input);
            }

        }
        this.possibleInputs = inputs;
    }

    public boolean contains(Input input) {
        return this.possibleInputs.contains(input);
    }
    public List<String> aToString() {
        List<String> inputList = new ArrayList<>();
        for (Input input : possibleInputs) {
            inputList.add(input.input);
        }

        return inputList;
    }

    public Input get(String input) throws IllegalArgumentException {
        for (Input in: possibleInputs) {
            if (in.iToString().equals(input)) {
                return in;
            }
        }
        throw new IllegalArgumentException("not a possible input");
    }
}
