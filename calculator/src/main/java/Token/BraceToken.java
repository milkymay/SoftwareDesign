package Token;

import TokenVisitor.TokenVisitor;

public class BraceToken implements Token {
    private final char bracket;

    public BraceToken(char bracket) {
        this.bracket = bracket;
    }

    public boolean isOpenBracket() {
        return (bracket == '(');
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {

        return "BT(\"" + bracket + "\")";
    }
}
