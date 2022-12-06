package Token;

import TokenVisitor.TokenVisitor;

public class OperationToken implements Token {
    private final char operation;

    public OperationToken(char operation) {
        this.operation = operation;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return " " + operation + " ";
    }
}
