package de.fluxparticle.syntax.lexer;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sreinck on 09.01.16.
 */
public abstract class BaseLexer {

    public abstract LexerElement peek();

    public abstract boolean check(LexerElement ch);

    public final void require(LexerElement ch) throws ParserException {
        if (!check(ch)) {
            throw error(Collections.singleton(ch));
        }
    }

    protected abstract String input();

    protected abstract int pos();

    public ParserException error(Set<LexerElement> expected) throws ParserException {
        return new ParserException(expected, peek(), input(), pos());
    }

    public ParserException error(String message, Throwable cause) {
        return new ParserException(message, cause, input(), pos());
    }

}
