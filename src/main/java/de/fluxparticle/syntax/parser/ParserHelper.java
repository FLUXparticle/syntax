package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.LineLexer;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.BNFSyntax;
import de.fluxparticle.syntax.structure.Rule;
import de.fluxparticle.syntax.structure.Syntax;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sreinck on 05.01.16.
 */
public class ParserHelper {

    private static final Parser ruleParser = Syntax
            .acceptAll(BNFSyntax.class, new ParserGenerator(), null)
            .get(BNFSyntax.RULE);

    public static Rule parse(String str) {
        LineLexer l = new LineLexer(str);
        try {
            return (Rule) ruleParser.check(l);
        } catch (ParserException e) {
            System.out.println(e.getMessage());
            e.printLexerState();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace(System.out);
            }
            System.exit(0);
            return null;
        }
    }

}
