package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class LiteralParser extends Parser {

    private final char literal;

    public LiteralParser(char literal) {
        this.literal = literal;
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(new LexerSymbol(literal));
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();

        if (peek != null && peek.toString().equals(Character.toString(literal))) {
            l.check(peek);
            return peek;
        }

        throw l.error(first());
    }

}
