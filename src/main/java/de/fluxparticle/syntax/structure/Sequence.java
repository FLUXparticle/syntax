package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Sequence extends Element {

    protected final SingleElement[] elements;

    public Sequence(SingleElement... elements) {
        this.elements = elements;
    }

    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitSequence(elements, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('{');

        String delimiter = "";
        for (SingleElement element : elements) {
            sb.append(delimiter);
            sb.append(element);
            delimiter = " ";
        }

        sb.append('}');

        return sb.toString();
    }

}
