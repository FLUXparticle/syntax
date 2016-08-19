package de.fluxparticle.syntax.structure;

import sun.misc.SharedSecrets;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * Created by sreinck on 04.01.16.
 */
public class EnumSyntax extends Syntax {

    private final Rule[] rules;

    public <T extends Enum<T>> EnumSyntax(Class<T> clazz) {
        T[] constants = SharedSecrets.getJavaLangAccess().getEnumConstantsShared(clazz);

        if (constants instanceof Rule[]) {
            rules = (Rule[]) constants;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Collection<Rule> getRules() {
        return unmodifiableList(asList(rules));
    }

    public <R, D> Map<String, R> acceptAll(ElementVisitor<R, D> visitor, D data) {
        Map<String, R> map = new LinkedHashMap<>();

        for (Rule rule : rules) {
            R result = rule.accept(visitor, data);
            map.put(rule.name(), result);
        }

        return map;
    }

}
