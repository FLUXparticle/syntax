package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.structure.*;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

/**
 * Created by sreinck on 11.01.16.
 */
public class MultiLiteralFinder implements ElementVisitor<Stream<String>, Void> {

    @Override
    public Stream<String> visitLiteral(char literal, Void data) {
        return empty();
    }

    @Override
    public Stream<String> visitMultiLiteral(String literal, Void data) {
        return of(literal);
    }

    @Override
    public Stream<String> visitKeyword(String keyword, KeywordType type, Void data) {
        return empty();
    }

    @Override
    public Stream<String> visitRangeLiteral(char from, char to, Void data) {
        return empty();
    }

    @Override
    public Stream<String> visitLoop(Element element, Literal delimiter, Void data) {
        return element.accept(this, data);
    }

    @Override
    public Stream<String> visitLoopEmpty(Element element, Void data) {
        return element.accept(this, data);
    }

    @Override
    public Stream<String> visitReference(String reference, Void data) {
        return empty();
    }

    @Override
    public Stream<String> visitRule(String name, RuleType ruleType, Function<Object[], Object> reduce, SingleElement[] elements, Void data) {
        return of(elements).flatMap(element -> element.accept(this, data));
    }

    @Override
    public Stream<String> visitSequence(SingleElement[] elements, Void data) {
        return of(elements).flatMap(element -> element.accept(this, data));
    }

    @Override
    public Stream<String> visitSpecial(Special.Item item, Void data) {
        return empty();
    }

    @Override
    public Stream<String> visitUnion(boolean nothing, Element[] elements, Void data) {
        return of(elements).flatMap(element -> element.accept(this, data));
    }

}
