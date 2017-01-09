package de.fluxparticle.syntax.structure;

import java.util.function.Function;

/**
 * Created by sreinck on 03.01.16.
 */
public interface ElementVisitor<R, D> {

    R visitLiteral(char literal, D data);

    R visitMultiLiteral(String literal, D data);

    R visitKeyword(String keyword, KeywordType type, D data);

    R visitRangeLiteral(char from, char to, D data);

    R visitLoop(boolean empty, Element element, Literal delimiter, D data);

    R visitReference(String reference, D data);

    R visitRule(String name, RuleType ruleType, Function<Object[], Object> reduce, SingleElement[] elements, D data);

    R visitSequence(SingleElement[] elements, D data);

//    R visitToken(String name, SingleElement[] elements, D data);

    R visitSpecial(Special.Item item, D data);

    R visitUnion(boolean nothing, Element[] elements, D data);

}
