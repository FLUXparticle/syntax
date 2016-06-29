package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 25.06.16.
 */
public class Special extends SingleElement {

    public enum Item {
        NEW_LINE, INDENT, UNINDENT
    }

    private final Item item;

    public Special(Item item) {
        this.item = item;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitSpecial(item, data);
    }

    @Override
    public String toString() {
        switch (item) {
            case NEW_LINE:
                return "$";
            case INDENT:
                return ">";
            case UNINDENT:
                return "<";
        }
        throw new IllegalArgumentException();
    }

}
