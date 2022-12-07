import Token.Token;
import Token.Tokenizer;
import TokenVisitor.ParserVisitor;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final Tokenizer tokenizer = new Tokenizer(System.in);
        final List<Token> tokens;
        try {
            tokens = tokenizer.tokenize();
        } catch (IllegalStateException | IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        final ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : tokens) {
            token.accept(parserVisitor);
        }
        final List<Token> rpn = parserVisitor.getTokens();
        for (Token token : rpn) {
            System.out.print(token + " ");
        }

    }
}
