package de.fluxparticle.syntax.parser;

import de.fluxparticle.syntax.lexer.LineLexer;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.structure.BNFSyntax;
import de.fluxparticle.syntax.structure.SimpleRule;

import static de.fluxparticle.syntax.structure.BNFSyntax.BNF_SYNTAX;

/**
 * Created by sreinck on 05.01.16.
 */
public class ParserHelper {

    private static final Parser RULE_PARSER = BNF_SYNTAX
            .acceptAll(new ParserGenerator(), null)
            .get(BNFSyntax.RULE.name());

    public static SimpleRule parse(String str) {
        LineLexer l = new LineLexer(str);
        try {
            return (SimpleRule) RULE_PARSER.check(l);
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
