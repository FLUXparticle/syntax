package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerEnd;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Set;

/**
 * Created by sreinck on 16.07.16.
 */
public class FullParser extends Parser {

    private final Parser parser;

    public FullParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    Set<LexerElement> first() {
        return parser.first();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        Object result = parser.check(l);

        l.require(new LexerEnd());

        return result;
    }

}
