package de.fluxparticle.syntax.structure;

import de.fluxparticle.syntax.parser.ParserHelper;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by sreinck on 19.08.16.
 */
public class SimpleSyntax implements Syntax {

    public static SimpleSyntax parse(Collection<String> lines) {
        List<SimpleRule> rules = lines.stream().map(ParserHelper::parse).collect(toList());
        return new SimpleSyntax(rules);
    }

    private final Collection<SimpleRule> rules;

    private SimpleSyntax(Collection<SimpleRule> rules) {
        this.rules = rules;
    }

    @Override
    public Collection<? extends Rule> getRules() {
        return rules;
    }

}
