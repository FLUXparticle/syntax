package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.Special;

import java.util.List;
import java.util.Set;

/**
 * Created by sreinck on 25.06.16.
 */
public class SpecialParser extends Parser {

    private final Special.Item item;

    public SpecialParser(Special.Item item) {
        this.item = item;
    }

    @Override
    Set<LexerElement> first() {
        throw new IllegalArgumentException();
    }

    @Override
    public Object check(BaseLexer l) throws ParserException {
        return null;
    }

}
