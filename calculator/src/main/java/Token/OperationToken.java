package Token;

import TokenVisitor.TokenVisitor;

import java.util.List;

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
        return "OT(\"" + operation + "\")";
    }

    public int getPriority() {
        return List.of('*', '/').contains(operation) ? 1 : 2;
    }

    public long count(long left, long right) {
        return switch (this.operation) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            default -> throw new IllegalStateException(this.operation + " is not a correct operation sign");
        };
    }
}
