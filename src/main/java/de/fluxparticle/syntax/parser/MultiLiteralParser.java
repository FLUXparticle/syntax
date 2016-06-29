package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class MultiLiteralParser extends Parser {

    private final LexerToken lexerToken;

    public MultiLiteralParser(String literal) {
        lexerToken = new LexerToken(null, literal);
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(lexerToken);
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();

        l.require(lexerToken);

        return peek;
    }

}
