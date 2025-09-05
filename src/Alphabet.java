import java.util.ArrayList;
import java.util.List;

public class Alphabet {
    List<Input> possibleInputs;

    public Alphabet(List<String> possibleInputs) {
        int length = possibleInputs.size();
        List<Input> inputs = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Input input = new Input(possibleInputs.get(i));
            inputs.add(input);
        }
        this.possibleInputs = inputs;
    }

    public boolean contains(Input input) {
        return this.possibleInputs.contains(input);
    }
}
