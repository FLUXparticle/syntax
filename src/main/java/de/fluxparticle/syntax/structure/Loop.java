package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Loop extends SingleElement {

    public enum LoopType {
        SOME, ANY, ONE;
    }

    private final LoopType type;

    private final Element element;

    private final Literal delimiter;

    public Loop(LoopType type, Element element, Literal delimiter) {
        this.type = type;
        this.element = element;
        this.delimiter = delimiter;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitLoop(type, element, delimiter, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        switch (type) {
            case SOME:
                sb.append('+');
                break;
            case ANY:
                sb.append('*');
                break;
            case ONE:
                sb.append('?');
                break;
        }

        if (delimiter != null) {
            sb.append(delimiter);
        }

        sb.append(element);

        return sb.toString();
    }

}
