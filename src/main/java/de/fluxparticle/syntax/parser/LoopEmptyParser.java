package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 08.01.16.
 */
public class LoopEmptyParser extends Parser {

    private final Parser p;

    public LoopEmptyParser(Parser p) {
        this.p = p;
    }

    @Override
    Set<LexerElement> first() {
        throw new IllegalArgumentException();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        List<Object> objects = new ArrayList<>();

        while (l.with(p, objects::add));

        return objects;
    }

}
