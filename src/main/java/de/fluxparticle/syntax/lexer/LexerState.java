package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 21.12.16.
 */
public class LexerState {

    private final LexerElement element;

    private final String input;

    private final int pos;

    public LexerState(LexerElement element, String input, int pos) {
        this.element = element;
        this.input = input;
        this.pos = pos;
    }

    public LexerElement getElement() {
        return element;
    }

    public String getInput() {
        return input;
    }

    public int getPos() {
        return pos;
    }

}
