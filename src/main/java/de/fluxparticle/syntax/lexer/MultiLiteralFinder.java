package de.fluxparticle.syntax.lexer;

import de.fluxparticle.utils.chain.Chain;
import de.fluxparticle.syntax.structure.*;
import de.fluxparticle.utils.chain.Chain;

import java.util.function.Function;

import static de.fluxparticle.utils.chain.Chain.emptyChain;
import static de.fluxparticle.utils.chain.Chain.singletonChain;

/**
 * Created by sreinck on 11.01.16.
 */
public class MultiLiteralFinder implements ElementVisitor<Chain<String>, Void> {

    public static String undefinedToken(String name) {
        return '`' + name + '`';
    }

    @Override
    public Chain<String> visitLiteral(char literal, Void data) {
        return emptyChain();
    }

    @Override
    public Chain<String> visitMultiLiteral(String literal, Void data) {
        return singletonChain(literal);
    }

    @Override
    public Chain<String> visitKeyword(String keyword, KeywordType type, Void data) {
        return emptyChain();
    }

    @Override
    public Chain<String> visitRangeLiteral(char from, char to, Void data) {
        return emptyChain();
    }

    @Override
    public Chain<String> visitLoop(Loop.LoopType empty, Element element, Literal delimiter, Void data) {
        return element.accept(this, data);
    }

    @Override
    public Chain<String> visitReference(String reference, Void data) {
        return emptyChain();
    }

    @Override
    public Chain<String> visitRule(String name, RuleType ruleType, Function<Object[], Object> reduce, SingleElement[] elements, Void data) {
        Chain<String> result;

        if (ruleType == RuleType.INPUT || ruleType == RuleType.TOKEN) {
            result = singletonChain(undefinedToken(name));
        } else {
            result = emptyChain();
        }

        return Chain.from(elements).flatMap(element -> element.accept(this, data)).concat(result);
    }

    @Override
    public Chain<String> visitSequence(SingleElement[] elements, Void data) {
        return Chain.from(elements).flatMap(element -> element.accept(this, data));
    }

    @Override
    public Chain<String> visitSpecial(Special.Item item, Void data) {
        return emptyChain();
    }

    @Override
    public Chain<String> visitUnion(Element[] elements, Void data) {
        return Chain.from(elements).flatMap(element -> element.accept(this, data));
    }

}
