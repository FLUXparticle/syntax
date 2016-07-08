package de.fluxparticle.syntax.structure;

import de.fluxparticle.syntax.lexer.LexerElement;
import de.fluxparticle.syntax.lexer.LexerSymbol;

import java.util.List;

import static de.fluxparticle.syntax.structure.Element.*;

/**
 * Created by sreinck on 04.01.16.
 */
public enum BNFSyntax implements Syntax {

    SINGLE_LITERAL(lit('\''), rangeLit((char) 32, (char) 126), lit('\'')) {
        @Override
        public Object reduce(Object... objects) {
            LexerSymbol symbol = (LexerSymbol) objects[1];
            return new Literal(symbol.getSymbol());
        }
    },

    RANGE_LITERAL(lit('['), rangeLit((char) 32, (char) 126), lit('-'), rangeLit((char) 32, (char) 126), lit(']')) {
        @Override
        public Object reduce(Object... objects) {
            LexerSymbol from = (LexerSymbol) objects[1];
            LexerSymbol to = (LexerSymbol) objects[3];
            return new RangeLiteral(from.getSymbol(), to.getSymbol());
        }
    },

    MULTI_LITERAL(lit('"'), loop(union(lit('!'), rangeLit((char) 35, (char) 126))), lit('"')) {
        @Override
        public Object reduce(Object... objects) {
            List list = (List) objects[1];

            StringBuilder sb = new StringBuilder();
            for (Object o : list) {
                sb.append(o);
            }

            return new MultiLiteral(sb.toString());
        }
    },

    LITERAL(union(ref("SINGLE_LITERAL"), ref("RANGE_LITERAL"), ref("MULTI_LITERAL"))),

    NAME(rangeLit('A', 'Z'), loopEmpty(union(rangeLit('A', 'Z'), lit('_')))) {
        @Override
        public Object reduce(Object... objects) {
            LexerSymbol head = (LexerSymbol) objects[0];
            List tail = (List) objects[1];

            StringBuilder sb = new StringBuilder();
            sb.append(head);
            for (Object o : tail) {
                sb.append(o);
            }

            return sb.toString();
        }
    },

    KEYWORD(lit(':'), loop(union(rangeLit('a', 'z'), rangeLit('A', 'Z'))), lit(':')) {
        @Override
        public Object reduce(Object... objects) {
            List list = (List) objects[1];

            StringBuilder sb = new StringBuilder();
            for (Object o : list) {
                sb.append(o);
            }

            return new Keyword(sb.toString(), KeywordType.HIGHLIGHT);
        }
    },

    COMMENT(lit('/'), lit('*'), loop(union(rangeLit('a', 'z'), rangeLit('A', 'Z'))), lit('*'), lit('/')) {
        @Override
        public Object reduce(Object... objects) {
            List list = (List) objects[2];

            StringBuilder sb = new StringBuilder("/*");
            for (Object o : list) {
                sb.append(o);
            }
            sb.append("*/");

            return new Keyword(sb.toString(), KeywordType.COMMENT);
        }
    },

    REFERENCE(ref("NAME")) {
        @Override
        public Object reduce(Object... objects) {
            return new Reference((String) objects[0]);
        }
    },

    OPTIONAL(lit('?'), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Element element = (Element) objects[1];
            return new Union(true, element);
        }
    },

    UNION(lit('('), unionEmpty(lit('|')), loop(lit('|'), ref("ELEMENT")), lit(')')) {
        @Override
        public Object reduce(Object... objects) {
            LexerElement first = (LexerElement) objects[1];
            List loop = (List) objects[2];
            boolean nothing = first != null;
            Element[] elements = new Element[loop.size()];
            int i = 0;

            for (Object obj : loop) {
                elements[i++] = (Element) obj;
            }

            return new Union(nothing, elements);
        }
    },

    LOOP(lit('+'), unionEmpty(ref("SINGLE_LITERAL")), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Literal delimiter = (Literal) objects[1];
            Element element = (Element) objects[2];
            return new Loop(element, delimiter);
        }
    },

    LOOP_EMPTY(lit('*'), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Element element = (Element) objects[1];
            return new LoopEmpty(element);
        }
    },

    TOKEN_ELEMENT(union(ref("LITERAL"), ref("KEYWORD"), ref("COMMENT"), ref("OPTIONAL"), ref("UNION"), ref("LOOP"), ref("LOOP_EMPTY"))),
/*
    SPECIAL(union(lit('$'), lit('>'), lit('<'))) {
        @Override
        public Object reduce(Object... objects) {
            LexerSymbol symbol = (LexerSymbol) objects[0];

            switch (symbol.getSymbol()) {
                case '$':
                    return new Special(Special.Item.NEW_LINE);
                case '>':
                    return new Special(Special.Item.INDENT);
                case '<':
                    return new Special(Special.Item.UNINDENT);
            }

            return null;
        }
    },
*/
    SINGLE_ELEMENT(union(ref("TOKEN_ELEMENT"), /* ref("TOKEN"), */ ref("REFERENCE")/*, ref("SPECIAL")*/)),

    SEQUENCE(lit('{'), loop(lit(' '), ref("SINGLE_ELEMENT")), lit('}')) {
        @Override
        public Object reduce(Object... objects) {
            List list = (List) objects[1];

            SingleElement[] elements = new SingleElement[list.size()];
            for (int i = 0; i < elements.length; i++) {
                elements[i] = (SingleElement) list.get(i);
            }

            return new Sequence(elements);
        }
    },

    ELEMENT(union(ref("SEQUENCE"), ref("SINGLE_ELEMENT"))),

    ANCHOR(lit('@')) {
        @Override
        public Object reduce(Object... objects) {
            return RuleType.ANCHOR;
        }
    },

    INPUT(lit('$')) {
        @Override
        public Object reduce(Object... objects) {
            return RuleType.INPUT;
        }
    },

    TOKEN(lit('#')) {
        @Override
        public Object reduce(Object... objects) {
            return RuleType.TOKEN;
        }
    },

    RULE(ref("NAME"), lit(' '), lit(':'), lit('='), lit(' '), optional(seq(union(ref("TOKEN"), ref("ANCHOR"), ref("INPUT")), lit(' '))), loop(lit(' '), ref("SINGLE_ELEMENT")), lit(';')) {
        @Override
        public Object reduce(Object... objects) {
            String name = (String) objects[0];
            List ruleTypeList = (List) objects[5];
            RuleType type = ruleTypeList != null ? (RuleType) ruleTypeList.get(0) : RuleType.SIMPLE;
            List list = (List) objects[6];

            SingleElement[] elements = new SingleElement[list.size()];
            for (int i = 0; i < list.size(); i++) {
                elements[i] = (SingleElement) list.get(i);
            }

            return new Rule(name, type, objs -> null, elements);
        }
    };

    private final Rule rule;

    BNFSyntax(SingleElement... elements) {
        this.rule = new Rule(name(), RuleType.SIMPLE, this::reduce, elements);
    }

    public Object reduce(Object... objects) {
        return objects[0];
    }

    @Override
    public Rule getRule() {
        return rule;
    }

}
