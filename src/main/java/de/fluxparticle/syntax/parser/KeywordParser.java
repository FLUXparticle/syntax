package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class KeywordParser extends Parser {

    private final String keyword;

    public KeywordParser(String keyword) {
        this.keyword = keyword;
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(new LexerToken(null, keyword));
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();

        if (peek != null && peek.toString().equals(keyword)) {
            l.check(peek);
            return peek;
        }

        throw l.error(first());
    }

}
