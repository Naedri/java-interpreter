package Parser.DefinitionP;

import Parser.ExpressionP.Expr;

import java.util.Comparator;

/**
 * Method declaration
 * 𝑀 ::= 𝑆 { return 𝑒; }
 */
public class Method implements Comparator<Method> {
    public Signature signature;
    public Expr body;

    public Method(Signature signature, Expr body) {
        this.signature = signature;
        this.body = body;
    }

    @Override
    public int compare(Method o1, Method o2) {
        Comparators.SignatureComparator sc = new Comparators.SignatureComparator();
        //TODO improve by going into body
        return sc.compare(o1.signature, o2.signature);
    }
}
