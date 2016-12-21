package de.fluxparticle.syntax.structure;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Created by sreinck on 19.08.16.
 */
public interface Syntax {

    Collection<? extends Rule> getRules();

    default <R, D> Map<String, R> acceptAll(ElementVisitor<R, D> visitor, D data) {
        return getRules().stream()
                .collect(toMap(Rule::name, rule -> rule.acceptRule(visitor, data)));
    }

}
