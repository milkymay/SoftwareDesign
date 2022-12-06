package Token;

import TokenVisitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
