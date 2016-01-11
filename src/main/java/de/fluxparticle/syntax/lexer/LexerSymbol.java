package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 08.01.16.
 */
public class LexerSymbol extends LexerElement {

    private final char ch;

    public LexerSymbol(char ch) {
        this.ch = ch;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public String toLexerString() {
        return "SYMBOL " + ch;
    }

    @Override
    public String toString() {
        return Character.toString(ch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexerSymbol that = (LexerSymbol) o;

        return ch == that.ch;
    }

    @Override
    public int hashCode() {
        return (int) ch;
    }

    public char getSymbol() {
        return ch;
    }

}
