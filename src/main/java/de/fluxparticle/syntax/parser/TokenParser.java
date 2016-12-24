package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerToken;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Collections;
import java.util.Set;

/**
 * Created by sreinck on 11.01.16.
 */
public class TokenParser extends Parser {

    private final String name;

    private final RuleParser parser;

    private final LexerToken token;

    public TokenParser(String name, RuleParser parser) {
        this.name = name;
        this.parser = parser;
        token = new LexerToken(parser, null);
    }

    @Override
    Set<LexerElement> first() {
        return Collections.singleton(token);
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();
        l.require(token);
        return new LexerToken(parser, peek.toString());
    }

}
