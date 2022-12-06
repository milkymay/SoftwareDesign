package TokenVisitor;

import Token.Brace;
import Token.NumberToken;
import Token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
