package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerSymbol;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by sreinck on 05.01.16.
 */
public class RangeLiteralParser extends Parser {

    private final char from;

    private final char to;

    private final Set<LexerElement> first;

    public RangeLiteralParser(char from, char to) {
        this.from = from;
        this.to = to;
        first = IntStream.rangeClosed(from, to).mapToObj(ch -> new LexerSymbol((char) ch)).collect(toSet());
    }

    @Override
    Set<LexerElement> first() {
        return first;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        LexerElement ch = l.peek();
        if (first().contains(ch)) {
            l.require(ch);
            return ch;
        }

        throw l.exception(first);
    }

}
