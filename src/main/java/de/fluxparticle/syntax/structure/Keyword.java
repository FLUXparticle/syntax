package de.fluxparticle.syntax.structure;

/**
 * Created by sreinck on 11.01.16.
 */
public class Keyword extends SingleElement {

    private final String keyword;

    private final KeywordType type;

    public Keyword(String keyword, KeywordType type) {
        this.keyword = keyword;
        this.type = type;
    }

    @Override
    public <R, D> R accept(ElementVisitor<R, D> visitor, D data) {
        return visitor.visitKeyword(keyword, type, data);
    }

    @Override
    public String toString() {
        return "'" + keyword + "'";
    }

}
