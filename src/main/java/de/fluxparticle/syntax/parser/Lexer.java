package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.*;

import java.util.*;

/**
 * Created by sreinck on 05.01.16.
 */
public class Lexer extends BaseLexer {

    private static final LexerWhitespace WHITESPACE = new LexerWhitespace();

    private final Scanner sc;

    private final SortedSet<String> literals;

    private final RuleParser[] tokenParsers;

    private LineLexer lineLexer;

    private LexerElement next;

    public Lexer(Scanner sc, Set<String> literals, RuleParser... tokenParsers) {
        this.sc = sc;
        this.literals = new TreeSet<>(Comparator.comparing(String::length).reversed().thenComparing(Comparator.naturalOrder()));
        this.tokenParsers = tokenParsers;

        this.literals.addAll(literals);

        nextLine();
    }

    @Override
    public LexerElement peek() {
        return next;
    }

    @Override
    public void check(LexerElement obj) throws ParserException {
        if (peek().equals(obj)) {
            nextToken();
        } else {
            throw error(Collections.singleton(obj));
        }
    }

    private void nextLine() {
        if (sc.hasNextLine()) {
            String line = sc.nextLine().replace("\t", "    ");
            lineLexer = new LineLexer(line);
        } else {
            lineLexer = null;
        }

        nextToken();
    }

    private void nextToken() {
        do {
            if (lineLexer != null && lineLexer.peek() instanceof LexerEnd) {
                nextLine();
            } else {
                next = next();
            }
        } while (next == WHITESPACE);
    }

    private LexerElement next() {
        if (lineLexer == null) {
            return null;
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
                    return new LexerToken(parser.getName(), input.substring(begin, end));
                } catch (ParserException e) {
                    // empty
                    throw new RuntimeException(e);
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
                lineLexer.check(new LexerSymbol(c));
            } catch (ParserException e) {
                // empty
                throw new RuntimeException(e);
            }
        }

        if (ch instanceof LexerSymbol && ((LexerSymbol) ch).getSymbol() == ' ') {
            return WHITESPACE;
        }

        return ch;
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
