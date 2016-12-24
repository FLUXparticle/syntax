package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 19.08.16.
 */
public class SimpleRule extends Sequence implements Rule {

    private final String name;

    private final String displayName;

    private final RuleType type;

    public SimpleRule(String name, String displayName, RuleType type, SingleElement[] elements) {
        super(elements);
        this.name = name;
        this.displayName = displayName;
        this.type = type;
    }

    @Override
    public <R, D> R acceptRule(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitRule(name, type, this::reduce, elements, data);
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
        if (type == RuleType.INPUT) {
            return objects[0];
        } else {
            return new NamedTree(name, objects);
        }
    }

    @Override
    public SingleElement[] getElements() {
        return elements;
    }

}
