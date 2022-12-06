package State;

import Token.NumberToken;
import Token.Tokenizer;

public class NumberState extends State {
    private final StringBuilder number = new StringBuilder();

    public NumberState(char digit) {
        this.number.append(digit);
    }

    @Override
    public void consume(Tokenizer tokenizer, char c) {
        if (Character.isDigit(c)) {
            number.append(c);
        } else {
            tokenizer.getTokens().add(new NumberToken(number.toString()));
            tokenizer.setState(next(c));
        }
    }
}
