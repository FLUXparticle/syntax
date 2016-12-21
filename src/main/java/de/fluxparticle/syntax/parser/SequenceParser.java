package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class SequenceParser extends Parser {

    private final Parser[] parsers;

    public SequenceParser(Parser... parsers) {
        this.parsers = parsers;
    }

    @Override
    Set<LexerElement> first() {
        return parsers[0].first();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        List<Object> objects = new ArrayList<>();

        for (Parser p : parsers) {
            Object obj = p.check(l);
            objects.add(obj);
        }

        return objects;
    }

}
