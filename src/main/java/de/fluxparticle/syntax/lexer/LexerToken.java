package de.fluxparticle.syntax.lexer;

import de.fluxparticle.syntax.parser.*;

/**
 * Created by sreinck on 08.01.16.
 */
public class LexerToken extends LexerElement {

    private final RuleParser parser;

    private final String name;

    private final String str;

    public LexerToken(RuleParser parser, String str) {
        this.parser = parser;
        this.name = parser != null ? parser.getName() : null;
        this.str = str;
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public String toLexerString() {
        if (name == null) {
            return String.format("* '%s'", str);
        } else if (str == null) {
            return name + " *";
        } else {
            return String.format("%s '%s'", name, str);
        }
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexerToken other = (LexerToken) o;

        boolean equalNames = false;

        if (name != null && other.name != null) {
            equalNames = name.equals(other.name);
        }

        if (str != null && other.str != null) {
            return str.equals(other.str);
        } else if (str != null) {
            return check(other.parser, this.str);
        } else if (other.str != null) {
            return check(this.parser, other.str);
        }

        return equalNames;
    }

    private boolean check(RuleParser parser, String str) {
        try {
            LineLexer l = new LineLexer(str);
            parser.check(l);
            l.require(new LexerEnd());
            return true;
        } catch (ParserException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getStr() {
        return str;
    }
}
