package de.fluxparticle.syntax.config;

import de.fluxparticle.syntax.lexer.MultiLiteralFinder;
import de.fluxparticle.syntax.parser.Lexer;
import de.fluxparticle.syntax.parser.Parser;
import de.fluxparticle.syntax.parser.ParserGenerator;
import de.fluxparticle.syntax.parser.RuleParser;
import de.fluxparticle.syntax.structure.EnumSyntax;
import de.fluxparticle.syntax.structure.RuleType;
import de.fluxparticle.syntax.structure.Syntax;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toSet;

/**
 * Created by sreinck on 25.02.16.
 */
public class SyntaxConfig {

    private final Map<String, Parser> parserMap;

    private final Set<String> literals;

    private final RuleParser[] tokenParsers;

    public <E extends Enum<E>> SyntaxConfig(Class<E> clazz) {
        this(new EnumSyntax(clazz));
    }

    public SyntaxConfig(Syntax syntax) {
        parserMap = syntax.acceptAll(new ParserGenerator(), null);
        literals = syntax.acceptAll(new MultiLiteralFinder(), null).values().stream()
                .flatMap(identity())
                .collect(toSet());
        tokenParsers = syntax.getRules().stream()
                .filter(rule -> rule.getRuleType() == RuleType.TOKEN)
                .map(rule -> parserMap.get(rule.name()))
                .toArray(RuleParser[]::new);
    }

    public Parser getParser(String ruleName) {
        return parserMap.get(ruleName);
    }

    public Lexer newLexer(Reader reader) {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        return new Lexer(bufferedReader, literals, tokenParsers);
    }

}
