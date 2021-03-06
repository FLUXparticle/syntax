package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.parser.Parser;

import java.util.Set;
import java.util.function.Consumer;

import static java.util.Collections.singleton;

/**
 * Created by sreinck on 09.01.16.
 */
public abstract class BaseLexer {

    public abstract LexerElement peek();

    public abstract boolean check(LexerElement ch);

    public final void require(LexerElement ch) throws ParserException {
        if (!check(ch)) {
            throw exception(singleton(ch));
        }
    }

    public final boolean with(Parser p, Consumer<Object> consumer) {
        try {
            push();
            Object o = p.check(this);
            consumer.accept(o);
            drop();
            return true;
        } catch (ParserException e) {
            pop();
            return false;
        }
    }

    public abstract void push();

    public abstract void pop();

    public abstract void drop();

    public abstract String getParsedInput();

    protected abstract String input();

    protected abstract int pos();

    public ParserException exception(Set<LexerElement> expected) throws ParserException {
        return new ParserException(expected, peek(), input(), pos());
    }

    public ParserRuntimeException error(String message, Throwable cause) {
        return new ParserRuntimeException(message, cause, input(), pos());
    }

}
