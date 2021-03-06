package de.fluxparticle.syntax.structure;

import de.fluxparticle.syntax.lexer.Named;

import java.util.Arrays;

/**
 * Created by sreinck on 24.12.16.
 */
public class NamedTree implements Named {

    private final String name;

    private final Object[] children;

    public NamedTree(String name, Object... children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    public Object[] getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "{" + name + ": " + Arrays.toString(children) + '}';
    }

}
