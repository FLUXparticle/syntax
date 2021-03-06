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

    private final Parser[] parsers;

    private Set<LexerElement> first;

    public UnionParser(Parser... parsers) {
        this.parsers = parsers;
    }

    @Override
    Set<LexerElement> first() {
        if (first == null) {
            first = calcFirst();
        }
        return first;
    }

    @Override
    public Set<Character> chars() {
        Set<Character> chars = new HashSet<>();
        for (Parser parser : parsers) {
            chars.addAll(parser.chars());
        }
        return chars;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        for (Parser p : parsers) {
            if (l.with(p, atomicReference::set)) {
                return atomicReference.get();
            }
        }

        throw l.exception(first());
    }

    private Set<LexerElement> calcFirst() {
        Set<LexerElement> result = new HashSet<>();

        for (Parser p : parsers) {
            result.addAll(p.first());
        }

        return result;
    }

}
