package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by sreinck on 05.01.16.
 */
public class UnionParser extends Parser {

    private final boolean nothing;

    private final Parser[] parsers;

    private Set<LexerElement> first;

    public UnionParser(boolean nothing, Parser... parsers) {
        this.nothing = nothing;
        this.parsers = parsers;
    }

    @Override
    Set<LexerElement> first() {
        if (first == null) {
            if (nothing) {
                throw new IllegalArgumentException();
            }

            first = calcFirst();
        }
        return first;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        for (Parser p : parsers) {
            if (l.with(p, atomicReference::set)) {
                return atomicReference.get();
            }
        }

        if (!nothing) {
            throw l.exception(first());
        }

        return null;
    }

    private Set<LexerElement> calcFirst() {
        Set<LexerElement> result = new HashSet<>();

        for (Parser p : parsers) {
            result.addAll(p.first());
        }

        return result;
    }

}
