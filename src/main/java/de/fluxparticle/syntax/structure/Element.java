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
        return new Loop(false, element, null);
    }

    public static  Loop loop(Literal delimiter, SingleElement... elements) {
        Element element = (elements.length == 1) ? elements[0] : seq(elements);
        return new Loop(false, element, delimiter);
    }

    public static  Loop loopEmpty(Literal delimiter, SingleElement... elements) {
        Element element = (elements.length == 1) ? elements[0] : seq(elements);
        return new Loop(true, element, delimiter);
    }

    public static Union optional(Element element) {
        return new Union(true, element);
    }

    public static  Union unionEmpty(Element... elements) {
        return new Union(true, elements);
    }

    public static  Union union(Element... elements) {
        return new Union(false, elements);
    }

    public abstract <R, D> R accept(ElementVisitor<R, D> visitor, D data);

    public abstract String toString();

}
