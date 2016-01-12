package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class MultiLiteralParser extends Parser {

    private final String literal;

    public MultiLiteralParser(String literal) {
        this.literal = literal;
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(new LexerToken(null, literal));
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();

        if (peek != null && peek.toString().equals(literal)) {
            l.check(peek);
            return peek;
        }

        throw l.error(first());
    }

}
