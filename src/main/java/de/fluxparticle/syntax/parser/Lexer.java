package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;
import de.fluxparticle.utils.chain.Chain;
import de.fluxparticle.utils.chain.EagerChain;
import de.fluxparticle.utils.chain.LazyChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static de.fluxparticle.utils.chain.Chain.emptyChain;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;

/**
 * Created by sreinck on 05.01.16.
 */
public class Lexer extends BaseLexer {

    private static final LexerWhitespace WHITESPACE = new LexerWhitespace();

    private final BufferedReader reader;

    private final SortedSet<String> literals;

    private final RuleParser[] tokenParsers;

    private LineLexer lineLexer;

    private LexerElement next;

    private boolean eof;

    public Lexer(BufferedReader reader, Set<String> literals, RuleParser... tokenParsers) {
        this.reader = reader;
        this.literals = new TreeSet<>(comparing(String::length).reversed().thenComparing(naturalOrder()));
        this.tokenParsers = tokenParsers;

        this.literals.addAll(literals);

        nextLine();
    }

    @Override
    public LexerElement peek() {
        return next;
    }

    @Override
    public boolean check(LexerElement obj) {
        if (peek().equals(obj)) {
            nextToken();
            return true;
        } else {
            return false;
        }
    }

    public Chain<LexerState> asStateChain() {
        return new LazyChain<>(() -> {
            LexerState state = new LexerState(next, input(), pos());
            if (next instanceof LexerEnd) {
                return new EagerChain<>(state, emptyChain());
            } else {
                nextToken();
                return new EagerChain<>(state, asStateChain());
            }
        });
    }

    private void nextLine() {
        String line;

        try {
            line = reader.readLine();
        } catch (IOException e) {
            line = null;
        }

        if (line != null) {
            line = line.replace("\t", "    ");
            lineLexer = new LineLexer(line);
        } else {
            if (lineLexer == null) {
                lineLexer = new LineLexer("");
            }
            eof = true;
        }

        nextToken();
    }

    private void nextToken() {
        do {
            if (lineLexer != null && lineLexer.peek() instanceof LexerEnd && !eof) {
                nextLine();
            } else {
                next = next();
            }
        } while (next == WHITESPACE);
    }

    private LexerElement next() {
        if (lineLexer == null) {
            throw new NoSuchElementException();
        }

        LexerElement ch = lineLexer.peek();
        for (RuleParser parser : tokenParsers) {
            Set<LexerElement> first = parser.first();
            if (first.contains(ch)) {
                try {
                    String input = lineLexer.input();
                    int begin = lineLexer.pos();
                    parser.check(lineLexer);
                    int end = lineLexer.pos();
                    return new LexerToken(parser, input.substring(begin, end));
                } catch (ParserException e) {
                    // empty
                }
            }
        }

        String tail = input().substring(lineLexer.pos());

        for (String literal : literals) {
            if (tail.startsWith(literal)) {
                ch = new LexerToken(null, literal);
            }
        }

        for (char c : ch.toString().toCharArray()) {
            try {
                lineLexer.require(new LexerSymbol(c));
            } catch (ParserException e) {
                // empty
            }
        }

        if (ch instanceof LexerSymbol && ((LexerSymbol) ch).getSymbol() == ' ') {
            return WHITESPACE;
        }

        return ch;
    }

    @Override
    public void push() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getParsedInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String input() {
        return lineLexer != null ? lineLexer.input() : null;
    }

    @Override
    protected int pos() {
        return lineLexer != null ? lineLexer.pos() - next.length() : 0;
    }

}
