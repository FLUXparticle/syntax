package de.fluxparticle.syntax.structure.ruletype;

/**
 * Created by sreinck on 29.06.16.
 */
public abstract class RuleType {

    public enum Type {
        SIMPLE,
        TOKEN,
        ANCHOR
    }

    private final Type type;

    public RuleType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
