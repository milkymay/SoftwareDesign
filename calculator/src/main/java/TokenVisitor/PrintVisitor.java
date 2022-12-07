package TokenVisitor;

import Token.BraceToken;
import Token.NumberToken;
import Token.OperationToken;
import Token.Token;

import java.io.OutputStream;
import java.io.PrintWriter;

public class PrintVisitor implements TokenVisitor {
    private final PrintWriter writer;

    public PrintVisitor(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    private void write(String output) {
        writer.write(output + " ");
    }

    public void print() {
        writer.write('\n');
        writer.flush();
    }

    private void visitToken(Token token) {
        write(token.toString());
    }

    @Override
    public void visit(NumberToken token) {
        visitToken(token);
    }

    @Override
    public void visit(BraceToken token) {
        visitToken(token);
    }

    @Override
    public void visit(OperationToken token) {
        visitToken(token);
    }
}
