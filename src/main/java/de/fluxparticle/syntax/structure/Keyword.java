package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 11.01.16.
 */
public class Keyword extends SingleElement {

    private final KeywordType type;

    private final String keyword;

    public Keyword(KeywordType type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitKeyword(type, keyword, data);
    }

    @Override
    public String toString() {
        return "'" + keyword + "'";
    }

}
