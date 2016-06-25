package de.fluxparticle.syntax.lexer;

/**
 * Created by sreinck on 08.01.16.
 */
public class LexerToken extends LexerElement {

    private final String name;

    private final String str;

    public LexerToken(String name, String str) {
        this.name = name;
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

        LexerToken that = (LexerToken) o;

        boolean check = false;

        if (name != null && that.name != null) {
            if (!name.equals(that.name)) return false;
            check = true;
        }

        if (str != null && that.str != null) {
            if (!str.equals(that.str)) return false;
            check = true;
        }

        return check;
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
