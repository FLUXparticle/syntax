package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.Loop.LoopType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.function.Function.identity;

/**
 * Created by sreinck on 05.01.16.
 */
public class LoopParser extends Parser {

    private final LoopType type;

    private final Parser p;

    private final Parser tailParser;

    private final Function<Object, Object> f;

    public LoopParser(LoopType type, Parser p, LiteralParser delimiterParser) {
        this.type = type;
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
        if (type == LoopType.SOME) {
            return p.first();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<Character> chars() {
        Set<Character> chars = new HashSet<>();
        chars.addAll(p.chars());
        chars.addAll(tailParser.chars());
        return chars;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        List<Object> objects = new ArrayList<>();

        if (l.with(p, objects::add)) {
            if (type != LoopType.ONE) {
                while (l.with(tailParser, o -> objects.add(f.apply(o))));
            }
        } else if (type == LoopType.SOME) {
            throw l.exception(first());
        }

        return objects;
    }

}
