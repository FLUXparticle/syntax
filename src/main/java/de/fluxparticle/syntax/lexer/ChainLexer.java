package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.parser.Lexer;
import de.fluxparticle.utils.chain.Chain;

import java.io.PrintWriter;
import java.io.StringWriter;
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
    public String getParsedInput() {
        Chain<LexerState> cur = stack.peek();
        String line = cur.head().getInput();

        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        do {
            Chain<LexerState> next = cur.tail();
            String newLine = next.head().getInput();
            if (newLine != line) {
                out.println(line.substring(cur.head().getPos()));
                out.print(line.substring(0, next.head().getPos()));
                line = newLine;
            } else {
                out.print(line.substring(cur.head().getPos(), next.head().getPos()));
            }
            cur = next;
        } while (cur != chain);

        return writer.toString();
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
