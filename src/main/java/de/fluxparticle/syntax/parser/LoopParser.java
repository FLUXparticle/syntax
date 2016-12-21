package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.function.Function.identity;

/**
 * Created by sreinck on 05.01.16.
 */
public class LoopParser extends Parser {

    private final Parser p;

    private final Parser tailParser;

    private final Function<Object, Object> f;

    public LoopParser(Parser p, LiteralParser delimiterParser) {
        this.p = p;
        if (delimiterParser == null) {
            tailParser = p;
            f = identity();
        } else {
            tailParser = new SequenceParser(delimiterParser, p);
            f = obj -> ((List) obj).get(1);
        }
    }

    @Override
    Set<LexerElement> first() {
        return p.first();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        List<Object> objects = new ArrayList<>();

        Object obj = p.check(l);
        objects.add(obj);

        while (l.with(tailParser, o -> objects.add(f.apply(o))));

        return objects;
    }

}
