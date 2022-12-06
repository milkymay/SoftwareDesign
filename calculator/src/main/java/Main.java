import Token.Token;
import Token.Tokenizer;

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
        for (Token token : tokens) {
            System.out.print(token.toString() + " ");
        }
    }
}
