package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerSymbol;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Collections;
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
    public Set<Character> chars() {
        return Collections.singleton(lexerSymbol.getSymbol());
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        l.require(lexerSymbol);

        return lexerSymbol;
    }

}
