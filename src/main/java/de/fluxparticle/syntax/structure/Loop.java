package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Loop extends SingleElement {

    private final boolean empty;

    private final Element element;

    private final Literal delimiter;

    public Loop(boolean empty, Element element, Literal delimiter) {
        this.empty = empty;
        this.element = element;
        this.delimiter = delimiter;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitLoop(empty, element, delimiter, data);
    }

    @Override
    public String toString() {
        return "+" + (delimiter != null ? delimiter.toString() : "") + element.toString();
    }

}
