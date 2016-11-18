package de.fluxparticle.syntax.structure;

import sun.misc.SharedSecrets;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * Created by sreinck on 04.01.16.
 */
public class EnumSyntax implements Syntax {

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
    public Collection<? extends Rule> getRules() {
        return unmodifiableList(asList(rules));
    }

}
