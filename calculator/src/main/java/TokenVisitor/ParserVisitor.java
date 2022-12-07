package TokenVisitor;

import Token.BraceToken;
import Token.NumberToken;
import Token.OperationToken;
import Token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> tokens = new ArrayList<>();
    private final Stack<Token> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token.isOpenBracket()) {
            stack.push(token);
        } else {
            while (true) {
                if (stack.empty()) {
                    throw new IllegalStateException("Check brackets");
                }
                Token top = stack.pop();
                if (top instanceof BraceToken &&
                        ((BraceToken) top).isOpenBracket()) {
                    break;
                }
                tokens.add(top);
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        while (!stack.empty()) {
            final Token onTop = stack.peek();
            if (onTop instanceof OperationToken && ((OperationToken) onTop).getPriority() <= token.getPriority()) {
                tokens.add(onTop);
                stack.pop();
            } else {
                break;
            }
        }
        stack.push(token);
    }

    public List<Token> getTokens() {
        while (!stack.empty()) {
            tokens.add(stack.pop());
        }
        return tokens;
    }
}
