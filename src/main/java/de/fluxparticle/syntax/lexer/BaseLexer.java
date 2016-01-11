package de.fluxparticle.syntax.lexer;

import java.util.Set;

/**
 * Created by sreinck on 09.01.16.
 */
public abstract class BaseLexer {

    public abstract LexerElement peek();

    public abstract void check(LexerElement ch) throws ParserException;

    protected abstract String input();

    protected abstract int pos();

    public ParserException error(Set<LexerElement> expected) throws ParserException {
        return new ParserException(expected, peek(), input(), pos());
    }

    public ParserException error(String message, Throwable cause) {
        return new ParserException(message, cause, input(), pos());
    }

}
