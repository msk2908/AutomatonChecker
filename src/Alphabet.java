import java.util.ArrayList;
import java.util.List;

public class Alphabet {
    List<Input> possibleInputs;

    public Alphabet(List<String> possibleInputs) {

        int length = possibleInputs.size();
        List<Input> inputs = new ArrayList<>();
        inputs.add(new Input("Epsilon", TransitionType.EPSILON));
        for (int i = 0; i < length; i++) {
            Input input = new Input(possibleInputs.get(i), TransitionType.LITERAL);
            inputs.add(input);

        }
        this.possibleInputs = inputs;
    }

    public Input add(String input) {
        Input i;
        if (input.equals("Epsilon")) {
            i = new Input(input, TransitionType.EPSILON);
        } else {
            i = new Input(input, TransitionType.LITERAL);
        }
        if (!this.possibleInputs.contains(i)) {
            this.possibleInputs.add(i);
        }
        return i;
    }

    public void add(Alphabet alphabet) {
        for (Input input : alphabet.possibleInputs) {
            boolean contains = true;
            for (Input input1 : possibleInputs) {
                if (input1.iToString().equals(input.iToString())) {
                    contains = false;
                    break;
                }
            }
            if (!this.possibleInputs.contains(input) && contains) {
                this.possibleInputs.add(input);
            }
        }
        try {
            alphabet.get("Epsilon");
        } catch (Exception e) {
            alphabet.add("Epsilon");
        }

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
        for (Input in : possibleInputs) {
            if (in.iToString().equals(input)) {
                return in;
            }
        }
        throw new IllegalArgumentException("not a possible input");
    }
}
