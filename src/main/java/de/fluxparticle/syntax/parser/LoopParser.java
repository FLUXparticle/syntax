package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public class LoopParser extends Parser {

    private final Parser p;

    private final LiteralParser delimiterParser;

    public LoopParser(Parser p, LiteralParser delimiterParser) {
        this.p = p;
        this.delimiterParser = delimiterParser;
    }

    @Override
    Set<LexerElement> first() {
        return p.first();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        Set<LexerElement> next = delimiterParser != null ? delimiterParser.first() : p.first();

        List<Object> objects = new ArrayList<>();

        Object obj = p.check(l);
        objects.add(obj);

        while (next.contains(l.peek())) {
            if (delimiterParser != null) {
                delimiterParser.check(l);
            }

            obj = p.check(l);
            objects.add(obj);
        }

        return objects;
    }
}
