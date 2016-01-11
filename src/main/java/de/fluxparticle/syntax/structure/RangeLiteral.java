package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class RangeLiteral extends TokenElement {

    private final char from;

    private final char to;

    public RangeLiteral(char from, char to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitRangeLiteral(from, to, data);
    }

    @Override
    public String toString() {
        return String.format("[%c-%c]", from, to);
    }

}
