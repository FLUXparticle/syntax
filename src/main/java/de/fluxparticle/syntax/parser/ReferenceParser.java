package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by sreinck on 29.06.16.
 */
public class ReferenceParser extends Parser {

    private final Supplier<Parser> parserSupplier;

    private Parser parser;

    public ReferenceParser(Supplier<Parser> parserSupplier) {
        this.parserSupplier = parserSupplier;
    }

    @Override
    Set<LexerElement> first() {
        return getParser().first();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        return getParser().check(l);
    }

    private Parser getParser() {
        if (parser == null) {
            parser = parserSupplier.get();
            if (parser == null) {
                throw new IllegalStateException();
            }
        }
        return parser;
    }

}
