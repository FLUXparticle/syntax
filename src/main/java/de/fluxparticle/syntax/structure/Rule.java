package de.fluxparticle.syntax.structure;

import java.util.function.Function;

/**
 * Created by sreinck on 03.01.16.
 */
public class Rule {

    private final String name;

    private final RuleType ruleType;

    private Function<Object[], Object> reduce;

    private final SingleElement[] elements;

    public Rule(String name, RuleType ruleType, Function<Object[], Object> reduce, SingleElement... elements) {
        this.name = name;
        this.ruleType = ruleType;
        this.reduce = reduce;
        this.elements = elements;
    }

    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitRule(name, ruleType, reduce, elements, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        sb.append(" :=");

        for (SingleElement element : elements) {
            sb.append(" ");
            sb.append(element);
        }

        sb.append(';');

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setReduce(Function<Object[], Object> reduce) {
        this.reduce = reduce;
    }

    public SingleElement[] getElements() {
        return elements;
    }

}
