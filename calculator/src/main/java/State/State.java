package State;

import Token.Tokenizer;

import java.util.Set;

public abstract class State {
    public static final Set<Character> braces = Set.of('(', ')');
    public static final Set<Character> operations = Set.of('+', '-', '*', '/');

    public abstract void consume(final Tokenizer tokenizer, final char c);

    public State next(final char c) {
        if (Character.isDigit(c)) {
            return new NumberState(c);
        } else if (braces.contains(c)) {
            return new BraceState(c);
        } else if (operations.contains(c)) {
            return new OperationState(c);
        } else if (Character.isWhitespace(c)) {
            return new WhitespaceState();
        }
        throw new IllegalStateException("Unexpected symbol: \"" + c + "\"");
    }
}
