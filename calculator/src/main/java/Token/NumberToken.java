package Token;

import TokenVisitor.TokenVisitor;

public class NumberToken implements Token {
    private final long number;

    public NumberToken(String stringNumber) {
        this.number = Long.parseLong(stringNumber);
    }

    public long getNumber() {
        return number;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NT(\"" + number + "\")";
    }
}
