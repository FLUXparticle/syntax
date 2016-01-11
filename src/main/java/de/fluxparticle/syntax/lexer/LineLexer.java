package de.fluxparticle.syntax.lexer;

import java.util.Collections;

/**
 * Created by sreinck on 09.01.16.
 */
public class LineLexer extends BaseLexer {

    String input;

    int pos;

    public LineLexer(String input) {
        this.input = input;
        pos = 0;
    }

    public LexerElement peek() {
        if (input == null || pos >= input.length()) {
            return new LexerEnd();
        }
        return new LexerSymbol(input.charAt(pos));
    }

    public void check(LexerElement obj) throws ParserException {
        if (peek().equals(obj)) {
            pos++;
        } else {
            throw error(Collections.singleton(obj));
        }
    }

    @Override
    public String input() {
        return input;
    }

    @Override
    public int pos() {
        return pos;
    }

}
