package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by sreinck on 05.01.16.
 */
public class ParserGenerator implements ElementVisitor<Parser, Void> {

    private final Map<String, Parser> parserMap = new HashMap<>();

    @Override
    public Parser visitLiteral(char literal, Void data) {
        return new LiteralParser(literal);
    }

    @Override
    public Parser visitMultiLiteral(String literal, Void data) {
        return new MultiLiteralParser(literal);
    }

    @Override
    public Parser visitKeyword(String keyword, Void data) {
        return new MultiLiteralParser(keyword);
    }

    @Override
    public Parser visitRangeLiteral(char from, char to, Void data) {
        return new RangeLiteralParser(from, to);
    }

    @Override
    public Parser visitLoop(Element element, Literal delimiter, Void data) {
        Parser p = element.accept(this, null);
        LiteralParser delimiterParser = delimiter != null ? (LiteralParser) delimiter.accept(this, null) : null;
        return new LoopParser(p, delimiterParser);
    }

    @Override
    public Parser visitLoopEmpty(Element element, Void data) {
        Parser p = element.accept(this, null);
        return new LoopEmptyParser(p);
    }

    @Override
    public Parser visitReference(String reference, Void data) {
        Parser parser = parserMap.get(reference);
        if (parser == null) {
            parser = new Parser() {

                @Override
                Set<LexerElement> first() {
                    return getParser().first();
                }

                @Override
                public Object check(BaseLexer l) throws ParserException {
                    return getParser().check(l);
                }

                private Parser getParser() {
                    Parser p = parserMap.get(reference);
                    if (p == null) {
                        throw new IllegalStateException();
                    }
                    return p;
                }
            };
        }
        return parser;
    }

    @Override
    public Parser visitRule(String name, boolean token, Function<Object[], Object> reduce, SingleElement[] elements, Void data) {
        RuleParser p = new RuleParser(parsers(elements), name, reduce);
        if (token) {
            TokenParser t = new TokenParser(name);
            parserMap.put(name, t);
        } else {
            parserMap.put(name, p);
        }
        return p;
    }

    @Override
    public Parser visitSequence(SingleElement[] elements, Void data) {
        return new SequenceParser(parsers(elements));
    }

    @Override
    public Parser visitToken(String name, SingleElement[] elements, Void data) {
        throw new NotImplementedException();
    }

    @Override
    public Parser visitUnion(boolean nothing, Element[] elements, Void data) {
        return new UnionParser(nothing, parsers(elements));
    }

    private Parser[] parsers(Element[] elements) {
        return Stream.of(elements).map(e -> e.accept(this, null)).toArray(Parser[]::new);
    }

}
