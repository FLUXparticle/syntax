package de.fluxparticle.syntax.structure;

import sun.misc.SharedSecrets;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by sreinck on 04.01.16.
 */
public interface Syntax {

    default String toRuleString() {
        return getRule().toString();
    }

    default  <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return getRule().accept(visitor, data);
    }

    static <T extends Enum<T>, R, D> Map<T, R> acceptAll(Class<T> clazz, ElementVisitor<R, D> visitor, D data) {
        Map<T, R> map = new EnumMap<>(clazz);

        T[] syntaxes = SharedSecrets.getJavaLangAccess().getEnumConstantsShared(clazz);

        for (T t : syntaxes) {
            Syntax s = (Syntax) t;
            R result = s.getRule().accept(visitor, data);
            map.put(t, result);
        }

        return map;
    }

    Rule getRule();

    String name();

}
