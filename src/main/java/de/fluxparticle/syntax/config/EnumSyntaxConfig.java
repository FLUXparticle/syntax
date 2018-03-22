package de.fluxparticle.syntax.config;

import de.fluxparticle.syntax.parser.Parser;
import de.fluxparticle.syntax.structure.EnumSyntax;

/**
 * Created by sreinck on 25.02.16.
 */
public class EnumSyntaxConfig<E extends Enum<E>> extends SyntaxConfig {

    public EnumSyntaxConfig(Class<E> clazz) {
        super(new EnumSyntax(clazz));
    }

    public Parser getParser(E rule) {
        return getParser(rule.name());
    }

    public Parser getTreeParser(E rule) {
        return getTreeParser(rule.name());
    }

}
