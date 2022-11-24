package Parser.ExpressionP;

import java.util.List;

/**
 * method invocation
 * 𝑒 ::= 𝑒.m(𝑒)
 */
public class MethodInvk extends Expr {
    public Expr parent;
    public String name;
    public List<Expr> params;

    public MethodInvk(Expr parent, String name, List<Expr> params) {
        this.parent = parent;
        this.name = name;
        this.params = params;
    }
}
