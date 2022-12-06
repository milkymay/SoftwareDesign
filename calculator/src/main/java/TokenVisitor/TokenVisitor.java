package TokenVisitor;

import Token.BraceToken;
import Token.NumberToken;
import Token.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(BraceToken token);
    void visit(OperationToken token);
}
