package TokenVisitor;

import Token.BraceToken;
import Token.NumberToken;
import Token.OperationToken;
import Token.Token;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Token> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.push(token);
    }

    @Override
    public void visit(BraceToken token) {
        throw new IllegalStateException("Brackets do not exist in RPN");
    }

    @Override
    public void visit(OperationToken token) {
        expect("zero", token);
        Token rightToken = stack.pop();
        expect("one", token);
        Token leftToken = stack.pop();
        if ((leftToken instanceof NumberToken) && (rightToken instanceof NumberToken)) {
            long rightNumber = ((NumberToken) rightToken).getNumber();
            long leftNumber = ((NumberToken) leftToken).getNumber();
            stack.push(new NumberToken(Long.toString(token.count(leftNumber, rightNumber))));
            return;
        }
        throw new IllegalStateException("Operands must be NT, but got: " +
                leftToken.getClass() + " and " + rightToken.getClass());
    }

    private void expect(String expected, OperationToken token) {
        if (stack.empty()) {
            throw new IllegalStateException("Not enough operands (" + expected + ") for " + token.toString());
        }
    }

    public long getResult() {
        Token result = stack.pop();
        if (!stack.empty()) {
            throw new IllegalStateException("Stack was expected to contain result only");
        }
        if (!(result instanceof NumberToken)) {
            throw new IllegalStateException("Result is not a NT, but: " + result.getClass());
        }
        return ((NumberToken) result).getNumber();
    }

}
