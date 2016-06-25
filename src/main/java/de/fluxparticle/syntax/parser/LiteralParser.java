package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class LiteralParser extends Parser {

    private final LexerSymbol lexerSymbol;

    public LiteralParser(char literal) {
        lexerSymbol = new LexerSymbol(literal);
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(lexerSymbol);
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        l.require(lexerSymbol);
        return lexerSymbol;
    }

}
