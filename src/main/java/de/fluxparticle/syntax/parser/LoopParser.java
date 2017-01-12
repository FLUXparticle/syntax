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

    private final boolean empty;

    private final Parser p;

    private final Parser tailParser;

    private final Function<Object, Object> f;

    public LoopParser(boolean empty, Parser p, LiteralParser delimiterParser) {
        this.empty = empty;
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
        if (empty) {
            throw new IllegalArgumentException();
        } else {
            return p.first();
        }
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        List<Object> objects = new ArrayList<>();

        if (l.with(p, objects::add)) {
            while (l.with(tailParser, o -> objects.add(f.apply(o)))) ;
        } else if (!empty) {
            throw l.exception(first());
        }

        if (objects.size() == 1) {
            Object o = objects.get(0);
            if (o instanceof String && ((String) o).isEmpty()) {
                if (empty) {
                    objects.clear();
                } else {
                    throw l.exception(first());
                }
            }
        }

        return objects;
    }

}
