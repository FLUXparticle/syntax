package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Union extends SingleElement {

    private final Element[] elements;

    public Union(Element... elements) {
        this.elements = elements;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitUnion(elements, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (elements.length == 1) {
            sb.append("?");
            sb.append(elements[0]);
        } else {
            sb.append("(");
            String delimiter = "";
            for (Element element : elements) {
                sb.append(delimiter);
                sb.append(element);
                delimiter = "|";
            }
            sb.append(")");
        }

        return sb.toString();
    }

    public Element[] getElements() {
        return elements;
    }

}
