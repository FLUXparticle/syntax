package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 09.01.16.
 */
public class LexerEnd extends LexerElement {

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String toLexerString() {
        return "END";
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

}
