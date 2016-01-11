package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public abstract class Element {

    public static Sequence seq(SingleElement... es) {
        return new Sequence(es);
    }

    public static  RangeLiteral rangeLit(char from, char to) {
        return new RangeLiteral(from, to);
    }

    public static  Literal lit(char literal) {
        return new Literal(literal);
    }

    public static  Reference ref(String reference) {
        return new Reference(reference);
    }

    public static  Loop loop(SingleElement element) {
        return new Loop(element, null);
    }

    public static  Loop loop(Literal delimiter, SingleElement... elements) {
        Element element = (elements.length == 1) ? elements[0] : seq(elements);
        return new Loop(element, delimiter);
    }

    public static  LoopEmpty loopEmpty(SingleElement... elements) {
        Element element = (elements.length == 1) ? elements[0] : seq(elements);
        return new LoopEmpty(element);
    }

    public static Union optional(Element element) {
        return new Union(true, element);
    }

    public static Token token(String name, SingleElement... elements) {
        return new Token(name, elements);
    }

    public static  Union unionEmpty(Element... elements) {
        return new Union(true, elements);
    }

    public static  Union union(Element... elements) {
        return new Union(false, elements);
    }

    public abstract <R, D> R accept(ElementVisitor<R, D> visitor, D data);

}
