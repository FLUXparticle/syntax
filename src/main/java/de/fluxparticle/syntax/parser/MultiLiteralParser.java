package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerToken;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

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
    public Set<Character> chars() {
        return lexerToken.toString().chars().mapToObj(ch -> (char) ch).collect(toSet());
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement peek = l.peek();

        l.require(lexerToken);

        return peek;
    }

}
