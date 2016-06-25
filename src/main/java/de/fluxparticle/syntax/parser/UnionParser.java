package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        LexerElement peek = l.peek();
        for (Parser p : parsers) {
            Set<LexerElement> first = p.first();
            if (first.contains(peek)) {
                return p.check(l);
            }
        }

        if (!nothing) {
            throw l.error(first());
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
