package de.fluxparticle.syntax.structure;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sreinck on 19.08.16.
 */
public abstract class Syntax {

    public abstract Collection<Rule> getRules();

    public abstract <R, D> Map<String, R> acceptAll(ElementVisitor<R, D> visitor, D data);

}
