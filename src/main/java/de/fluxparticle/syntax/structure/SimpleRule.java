package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 19.08.16.
 */
public class SimpleRule implements Rule {

    private final String name;

    private final String displayName;

    private final RuleType type;

    private final SingleElement[] elements;

    public SimpleRule(String name, String displayName, RuleType type, SingleElement[] elements) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.elements = elements;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    public RuleType getRuleType() {
        return type;
    }

    @Override
    public Object reduce(Object[] objects) {
        return objects;
    }

    @Override
    public SingleElement[] getElements() {
        return elements;
    }

}
