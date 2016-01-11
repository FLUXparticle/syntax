package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 09.01.16.
 */
public class LexerWhitespace extends LexerElement {

    @Override
    public int length() {
        return 1;
    }

    @Override
    public String toLexerString() {
        return "WHITESPACE";
    }

    @Override
    public String toString() {
        return " ";
    }

}
