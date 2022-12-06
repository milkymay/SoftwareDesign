package State;

import Token.BraceToken;
import Token.Tokenizer;

public class BraceState extends State {
    private final char bracket;

    public BraceState(char bracket) {
        this.bracket = bracket;
    }

    @Override
    public void consume(Tokenizer tokenizer, char c) {
        tokenizer.getTokens().add(new BraceToken(bracket));
        tokenizer.setState(next(c));
    }
}
