package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 08.01.16.
 */
public class LoopEmpty extends SingleElement {

    private final Element element;

    public LoopEmpty(Element element) {
        this.element = element;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitLoopEmpty(element, data);
    }

    @Override
    public String toString() {
        return "*" + element.toString();
    }

}
