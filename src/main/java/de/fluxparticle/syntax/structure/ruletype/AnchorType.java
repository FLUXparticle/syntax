package de.fluxparticle.syntax.structure.ruletype;

/**
 * Created by sreinck on 29.06.16.
 */
public class AnchorType extends RuleType {

    private final String name;

    public AnchorType(String name) {
        super(Type.ANCHOR);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
