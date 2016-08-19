package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 19.08.16.
 */
public class SimpleRule implements Rule {

    private final String name;

    private final RuleType type;

    private final SingleElement[] elements;

    public SimpleRule(String name, RuleType type, SingleElement[] elements) {
        this.name = name;
        this.type = type;
        this.elements = elements;
    }

    @Override
    public String name() {
        return name;
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
