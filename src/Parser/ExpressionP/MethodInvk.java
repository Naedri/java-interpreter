package Parser.ExpressionP;

import java.util.TreeSet;

/**
 * method invocation
 * 𝑒 ::= 𝑒.m(𝑒)
 */
public class MethodInvk extends Expr {
    public Expr parent;
    public String name;
    public TreeSet<Expr> params;

    public MethodInvk(Expr parent, String name, TreeSet<Expr> params) {
        super(3);
        this.parent = parent;
        this.name = name;
        this.params = params;
    }
}
