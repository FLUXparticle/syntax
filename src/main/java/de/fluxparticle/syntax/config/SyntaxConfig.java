package de.fluxparticle.syntax.config;

import de.fluxparticle.syntax.lexer.MultiLiteralFinder;
import de.fluxparticle.syntax.parser.Lexer;
import de.fluxparticle.syntax.parser.Parser;
import de.fluxparticle.syntax.parser.ParserGenerator;
import de.fluxparticle.syntax.parser.RuleParser;
import de.fluxparticle.syntax.structure.Syntax;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by sreinck on 25.02.16.
 */
public class SyntaxConfig<T extends Enum<T>> {

    private final Map<T, Parser> parserMap;

    private final Set<String> literals = new HashSet<>();

    private final RuleParser[] tokenParsers;

    public SyntaxConfig(Class<T> syntax) {
        parserMap = Syntax.acceptAll(syntax, new ParserGenerator(), null);
        Syntax.acceptAll(syntax, new MultiLiteralFinder(), literals);
        tokenParsers = parserMap.keySet().stream()
                .filter(s -> ((Syntax) s).getRule().isToken())
                .map(parserMap::get).map(p -> (RuleParser) p)
                .toArray(RuleParser[]::new);
    }

    public Parser getParser(T rule) {
        return parserMap.get(rule);
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
