package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerToken;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 11.01.16.
 */
public class TokenParser extends Parser {

    private final String name;

    private final LexerToken token;

    public TokenParser(String name) {
        this.name = name;
        token = new LexerToken(name, null);
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(token);
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();
        l.require(token);
        return peek;
    }

}
