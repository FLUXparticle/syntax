package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.*;
import de.fluxparticle.syntax.structure.ruletype.RuleType;

import java.util.HashMap;
import java.util.List;
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
        return new ReferenceParser(() -> parserMap.get(reference));
    }

    @Override
    public Parser visitRule(String name, RuleType ruleType, Function<Object[], Object> reduce, SingleElement[] elements, Void data) {
        RuleParser p = new RuleParser(parsers(elements), name, reduce);
        switch (ruleType.getType()) {
            case TOKEN:
                TokenParser t = new TokenParser(name);
                parserMap.put(name, t);
                break;
            case SIMPLE:
            case ANCHOR:
                parserMap.put(name, p);
                break;
        }
        return p;
    }

    @Override
    public Parser visitSequence(SingleElement[] elements, Void data) {
        return new SequenceParser(parsers(elements));
    }

    @Override
    public Parser visitSpecial(Special.Item item, Void data) {
        return new SpecialParser(item);
    }

    @Override
    public Parser visitUnion(boolean nothing, Element[] elements, Void data) {
        return new UnionParser(nothing, parsers(elements));
    }

    private Parser[] parsers(Element[] elements) {
        return Stream.of(elements).map(e -> e.accept(this, null)).toArray(Parser[]::new);
    }

}
