import Token.Token;
import Token.Tokenizer;
import TokenVisitor.CalcVisitor;
import TokenVisitor.ParserVisitor;
import TokenVisitor.PrintVisitor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final String input;
        try {
            input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        long output = generateOutput(input);
        System.out.println(output);
    }

    static long generateOutput(String input) throws IOException {
        final Tokenizer tokenizer = new Tokenizer(input);
        final List<Token> tokens;
        tokens = tokenizer.tokenize();

        final ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : tokens) {
            token.accept(parserVisitor);
        }
        final List<Token> rpn = parserVisitor.getTokens();
        final PrintVisitor printVisitor = new PrintVisitor(System.out);
        for (Token token : rpn) {
            token.accept(printVisitor);
        }
        printVisitor.print();
        final CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : rpn) {
            token.accept(calcVisitor);
        }
        return calcVisitor.getResult();
    }
}
