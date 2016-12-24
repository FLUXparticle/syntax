package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 11.01.16.
 */
public class MultiLiteral extends SelectableElement {

    private final String literal;

    public MultiLiteral(String literal) {
        this.literal = literal;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitMultiLiteral(literal, data);
    }

    @Override
    public String toString() {
        return "'" + literal + "'";
    }

    @Override
    public String getSelector() {
        return literal;
    }

}
