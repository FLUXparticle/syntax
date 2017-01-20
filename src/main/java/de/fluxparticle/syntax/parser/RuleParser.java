package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerToken;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.List;
import java.util.function.Function;

import static de.fluxparticle.syntax.lexer.MultiLiteralFinder.undefinedToken;

/**
 * Created by sreinck on 05.01.16.
 */
public class RuleParser extends SequenceParser {

    private final String name;

    private final Function<Object[], Object> reduce;

    private final boolean inputType;

    public RuleParser(Parser[] parsers, String name, Function<Object[], Object> reduce, boolean inputType) {
        super(parsers);
        this.name = name;
        this.reduce = reduce;
        this.inputType = inputType;
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        try {
            l.push();

            Object[] objects;
            if (inputType) {
                String str;
                LexerElement peek = l.peek();
                if (peek instanceof LexerToken && ((LexerToken) peek).getStr().equals(undefinedToken(name))) {
                    l.require(peek);
                    str = "";
                } else {
                    super.check(l);
                    str = l.getParsedInput();
                }
                objects = new Object[] { str.trim() };
            } else {
                List list = (List) super.check(l);
                objects = list.toArray();
            }
            return reduce.apply(objects);
        } catch (RuntimeException e) {
            throw l.error("exception in rule " + name, e);
        } finally {
            l.drop();
        }
    }

    public String getName() {
        return name;
    }

}
