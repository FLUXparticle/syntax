package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.structure.Element;
import de.fluxparticle.syntax.structure.ElementVisitor;
import de.fluxparticle.syntax.structure.Literal;
import de.fluxparticle.syntax.structure.SingleElement;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by sreinck on 11.01.16.
 */
public class MultiLiteralFinder implements ElementVisitor<Void, Set<String>> {

    @Override
    public Void visitLiteral(char literal, Set<String> data) {
        return null;
    }

    @Override
    public Void visitMultiLiteral(String literal, Set<String> data) {
        data.add(literal);
        return null;
    }

    @Override
    public Void visitKeyword(String keyword, Set<String> data) {
        return null;
    }

    @Override
    public Void visitRangeLiteral(char from, char to, Set<String> data) {
        return null;
    }

    @Override
    public Void visitLoop(Element element, Literal delimiter, Set<String> data) {
        element.accept(this, data);
        return null;
    }

    @Override
    public Void visitLoopEmpty(Element element, Set<String> data) {
        element.accept(this, data);
        return null;
    }

    @Override
    public Void visitReference(String reference, Set<String> data) {
        return null;
    }

    @Override
    public Void visitRule(String name, boolean token, Function<Object[], Object> reduce, SingleElement[] elements, Set<String> data) {
        for (SingleElement element : elements) {
            element.accept(this, data);
        }
        return null;
    }

    @Override
    public Void visitSequence(SingleElement[] elements, Set<String> data) {
        for (SingleElement element : elements) {
            element.accept(this, data);
        }
        return null;
    }

    @Override
    public Void visitToken(String name, SingleElement[] elements, Set<String> data) {
        for (SingleElement element : elements) {
            element.accept(this, data);
        }
        return null;
    }

    @Override
    public Void visitUnion(boolean nothing, Element[] elements, Set<String> data) {
        for (Element element : elements) {
            element.accept(this, data);
        }
        return null;
    }

}
