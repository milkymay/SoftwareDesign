package State;

import Token.OperationToken;
import Token.Tokenizer;

public class OperationState extends State {
    private final char operation;

    public OperationState(char operation) {
        this.operation = operation;
    }

    @Override
    public void consume(Tokenizer tokenizer, char c) {
        tokenizer.getTokens().add(new OperationToken(operation));
        tokenizer.setState(next(c));
    }
}
