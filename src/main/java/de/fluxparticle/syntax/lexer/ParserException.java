package de.fluxparticle.syntax.lexer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sreinck on 05.01.16.
 */
public class ParserException extends Exception {

    private final String input;

    private final int pos;

    public ParserException(String message, Throwable cause, String input, int pos) {
        super(message, cause);
        this.input = input;
        this.pos = pos;
    }

    public ParserException(Set<LexerElement> expected, LexerElement actual, String input, int pos) {
        super("got (" + actual.toLexerString() + ") but expected " + extractLexerStrings(expected) + " at " + pos);
        this.input = input;
        this.pos = pos;
    }

    public void printLexerState() {
        System.out.println(input);
        for (int i = 0; i < pos; i++) {
            System.out.print(' ');
        }
        System.out.println('^');
    }

    private static List<String> extractLexerStrings(Set<LexerElement> expected) {
        return expected.stream().map(LexerElement::toLexerString).collect(Collectors.toList());
    }

}
