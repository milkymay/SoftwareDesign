package State;

import Token.Tokenizer;

public class WhitespaceState extends State {

    @Override
    public void consume(Tokenizer tokenizer, char c) {
        tokenizer.setState(next(c));
    }
}
