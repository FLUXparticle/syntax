package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public class Reference extends SingleElement {

    private final String reference;

    public Reference(String reference) {
        this.reference = reference;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitReference(reference, data);
    }

    @Override
    public String toString() {
        return reference;
    }

}
