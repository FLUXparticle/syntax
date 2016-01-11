package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.List;
import java.util.function.Function;

/**
 * Created by sreinck on 05.01.16.
 */
public class RuleParser extends SequenceParser {

    private final String name;

    private final Function<Object[], Object> reduce;

    public RuleParser(Parser[] parsers, String name, Function<Object[], Object> reduce) {
        super(parsers);
        this.name = name;
        this.reduce = reduce;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        try {
            List list = (List) super.check(l);
            return reduce.apply(list.toArray());
        } catch (RuntimeException e) {
            throw l.error("exception in rule " + name, e);
        }
    }

    public String getName() {
        return name;
    }

}
