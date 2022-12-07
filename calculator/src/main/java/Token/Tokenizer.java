package Token;

import State.WhitespaceState;
import State.State;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String input;
    private final List<Token> tokens = new ArrayList<>();
    private State state = new WhitespaceState();

    public Tokenizer(final String input) {
        this.input = input + "\n";
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Token> tokenize() throws IOException {
        for (int pos = 0; pos < input.length(); pos++) {
            char c = input.charAt(pos);
            state.consume(this, c);
        }
        return tokens;
    }
}
