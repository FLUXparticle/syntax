package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;

import java.util.Set;

/**
 * Created by sreinck on 05.01.16.
 */
public abstract class Parser {

    abstract Set<LexerElement> first();

    public abstract Set<Character> chars();

    public abstract Object check(BaseLexer l) throws ParserException;

}
