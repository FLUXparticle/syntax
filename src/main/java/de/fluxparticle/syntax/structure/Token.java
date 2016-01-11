package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 09.01.16.
 */
public class Token {

    private final String name;

    private final SingleElement[] elements;

    public Token(String name, SingleElement[] elements) {
        this.name = name;
        this.elements = elements;
    }

    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitToken(name, elements, data);
    }

    @Override
    public String toString() {
        return name;
    }

}
