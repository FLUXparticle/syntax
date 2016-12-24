package de.fluxparticle.syntax.lexer;

import java.util.Stack;

/**
 * Created by sreinck on 09.01.16.
 */
public class LineLexer extends BaseLexer {

    private final Stack<Integer> stack = new Stack<>();

    private final String input;

    private int pos;

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

    public boolean check(LexerElement ch) {
        if (peek().equals(ch)) {
            pos++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void push() {
        stack.push(pos);
    }

    @Override
    public void pop() {
        pos = stack.pop();
    }

    @Override
    public void drop() {
        stack.pop();
    }

    @Override
    public String getParsedInput() {
        return input.substring(stack.peek(), pos);
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
