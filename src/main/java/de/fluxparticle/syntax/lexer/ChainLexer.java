package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.parser.Lexer;
import de.fluxparticle.utils.chain.Chain;

import java.util.Stack;

/**
 * Created by sreinck on 21.12.16.
 */
public class ChainLexer extends BaseLexer {

    private final Stack<Chain<LexerState>> stack = new Stack<>();

    private Chain<LexerState> chain;

    public ChainLexer(Lexer lexer) {
        chain = lexer.asStateChain();
    }

    @Override
    public LexerElement peek() {
        return chain.head().getElement();
    }

    @Override
    public boolean check(LexerElement obj) {
        if (peek().equals(obj)) {
            chain = chain.tail();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void push() {
        stack.push(chain);
    }

    @Override
    public void pop() {
        chain = stack.pop();
    }

    @Override
    public void drop() {
        stack.pop();
    }

    @Override
    protected String input() {
        return chain.head().getInput();
    }

    @Override
    protected int pos() {
        return chain.head().getPos();
    }

    public Chain<LexerState> getChain() {
        return chain;
    }

}
