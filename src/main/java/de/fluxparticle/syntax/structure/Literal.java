package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Literal extends TokenElement {

    private final char literal;

    public Literal(char literal) {
        this.literal = literal;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitLiteral(literal, data);
    }

    @Override
    public String toString() {
        return "'" + literal + "'";
    }

}
