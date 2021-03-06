package de.fluxparticle.syntax.structure;

import de.fluxparticle.syntax.lexer.LexerSymbol;
import de.fluxparticle.syntax.structure.Loop.LoopType;

import java.util.List;

import static de.fluxparticle.syntax.structure.Element.*;

/**
 * Created by sreinck on 04.01.16.
 */
public enum BNFSyntax implements Rule {

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

    NAME(rangeLit('A', 'Z'), loopEmpty(null, union(rangeLit('A', 'Z'), lit('_')))) {
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

    WORD(lit('.'), loop(union(rangeLit('a', 'z'), rangeLit('A', 'Z'))), lit('.')) {
        @Override
        public Object reduce(Object... objects) {
            List list = (List) objects[1];

            StringBuilder sb = new StringBuilder();
            for (Object o : list) {
                sb.append(o);
            }

            return new Keyword(sb.toString(), KeywordType.NORMAL);
        }
    },

    REFERENCE(ref("NAME")) {
        @Override
        public Object reduce(Object... objects) {
            return new Reference((String) objects[0]);
        }
    },

    UNION(lit('('), loop(lit('|'), ref("ELEMENT")), lit(')')) {
        @Override
        public Object reduce(Object... objects) {
            List loop = (List) objects[1];
            Element[] elements = new Element[loop.size()];
            int i = 0;

            for (Object obj : loop) {
                elements[i++] = (Element) obj;
            }

            return new Union(elements);
        }
    },

    OPTIONAL(lit('?'), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Element element = (Element) objects[1];
            return new Loop(LoopType.ONE, element, null);
        }
    },

    LOOP(lit('+'), optional(union(ref("SINGLE_LITERAL"))), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Literal delimiter = fromOptional(objects[1]);
            Element element = (Element) objects[2];
            return new Loop(LoopType.SOME, element, delimiter);
        }
    },

    LOOP_EMPTY(lit('*'), optional(union(ref("SINGLE_LITERAL"))), ref("ELEMENT")) {
        @Override
        public Object reduce(Object... objects) {
            Literal delimiter = fromOptional(objects[1]);
            Element element = (Element) objects[2];
            return new Loop(LoopType.ANY, element, delimiter);
        }
    },

    TOKEN_ELEMENT(union(ref("LITERAL"), ref("KEYWORD"), ref("WORD"), ref("OPTIONAL"), ref("UNION"), ref("LOOP"), ref("LOOP_EMPTY"))),

    SINGLE_ELEMENT(union(ref("TOKEN_ELEMENT"), ref("REFERENCE"))),

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

    RULE(ref("NAME"), optional(seq(lit('('), loop(union(rangeLit('a', 'z'), rangeLit('A', 'Z'), lit(' '))), lit(')'))), lit(' '), lit(':'), lit('='), lit(' '), optional(seq(union(ref("TOKEN"), ref("ANCHOR"), ref("INPUT")), lit(' '))), loop(lit(' '), ref("SINGLE_ELEMENT")), lit(';')) {
        @Override
        public Object reduce(Object... objects) {
            String name = (String) objects[0];

            List displayNameList = fromOptional(objects[1]);
            StringBuilder displayName = new StringBuilder();
            if (displayNameList == null) {
                displayName.append(name);
            } else {
                displayNameList = (List) displayNameList.get(1);
                for (Object o : displayNameList) {
                    displayName.append(o);
                }
            }

            List ruleTypeList = fromOptional(objects[6]);
            RuleType type = ruleTypeList != null ? (RuleType) ruleTypeList.get(0) : RuleType.SIMPLE;

            List list = (List) objects[7];
            SingleElement[] elements = new SingleElement[list.size()];
            for (int i = 0; i < list.size(); i++) {
                elements[i] = (SingleElement) list.get(i);
            }

            return new SimpleRule(name, displayName.toString(), type, elements);
        }
    };

    @SuppressWarnings("unchecked")
    private static <T> T fromOptional(Object object) {
        List<T> list = (List<T>) object;
        return list.isEmpty() ? null : list.get(0);
    }

    public static final Syntax BNF_SYNTAX = new EnumSyntax(BNFSyntax.class);

    private final SingleElement[] elements;

    BNFSyntax(SingleElement... elements) {
        this.elements = elements;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.SIMPLE;
    }

    public Object reduce(Object... objects) {
        return objects[0];
    }

    @Override
    public SingleElement[] getElements() {
        return elements;
    }

}
