package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 09.01.16.
 */
public abstract class LexerElement {

    public abstract int length();

    public abstract String toLexerString();

    public abstract String toString();

}
