package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 03.01.16.
 */
public interface Rule {

    default  <R, D> R acceptRule(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitRule(name(), getRuleType(), this::reduce, getElements(), data);
    }

    default String toRuleString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name());
        sb.append(" :=");

        for (SingleElement element : getElements()) {
            sb.append(" ");
            sb.append(element);
        }

        sb.append(';');

        return sb.toString();
    }

    String name(); // interner Name

    String toString(); // Display-Name

    RuleType getRuleType();

    Object reduce(Object[] objects);

    SingleElement[] getElements();

}
