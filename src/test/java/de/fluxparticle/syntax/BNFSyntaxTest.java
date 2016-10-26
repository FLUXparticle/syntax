package de.fluxparticle.syntax;

import de.fluxparticle.syntax.lexer.BaseLexer;
import de.fluxparticle.syntax.lexer.LineLexer;
import de.fluxparticle.syntax.lexer.ParserException;
import de.fluxparticle.syntax.parser.Parser;
import de.fluxparticle.syntax.parser.ParserGenerator;
import de.fluxparticle.syntax.structure.BNFSyntax;
import de.fluxparticle.syntax.structure.Rule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by sreinck on 26.10.16.
 */
@RunWith(Parameterized.class)
public class BNFSyntaxTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> parameters() {
        return Stream.of(BNFSyntax.values())
                .map(r -> new Object[]{r.name(), r})
                .collect(toList());
    }

    private final BNFSyntax syntax;

    public BNFSyntaxTest(String name, BNFSyntax syntax) {
        this.syntax = syntax;
    }

    @Test
    public void testParser() throws Exception {
        parser();
    }

    @Test
    public void testCheck() throws Exception {
        check();
    }

    @Test
    public void testEquals() throws Exception {
        String expected = syntax.toRuleString();
        String actual = check().toRuleString();

        Assert.assertEquals(expected, actual);
    }

    private Parser parser() throws Exception {
        Parser ruleParser = null;

        ParserGenerator parserGenerator = new ParserGenerator();
        for (BNFSyntax syntax : BNFSyntax.values()) {
            ruleParser = syntax.accept(parserGenerator, null);
        }

        return ruleParser;
    }

    private Rule check() throws Exception {
        Parser ruleParser = parser();

        String ruleString = syntax.toRuleString();
        BaseLexer lexer = new LineLexer(ruleString);
        try {
            return (Rule) ruleParser.check(lexer);
        } catch (ParserException e) {
            e.printLexerState();
            if (e.getCause() != null) {
                e.getCause().printStackTrace(System.out);
            }
            throw e;
        }
    }

}
