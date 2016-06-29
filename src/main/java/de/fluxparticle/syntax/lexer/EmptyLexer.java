package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 26.06.16.
 */
public class EmptyLexer extends BaseLexer {

    @Override
    public LexerElement peek() {
        return new LexerEnd();
    }

    @Override
    public boolean check(LexerElement ch) {
        return false;
    }

    @Override
    protected String input() {
        return "";
    }

    @Override
    protected int pos() {
        return 0;
    }

}
