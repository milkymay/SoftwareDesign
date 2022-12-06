package Token;

import TokenVisitor.TokenVisitor;

public class NumberToken implements Token {
    private final long number;

    public NumberToken(String stringNumber) {
        this.number = Long.parseLong(stringNumber);
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Long.toString(number);
    }
}
