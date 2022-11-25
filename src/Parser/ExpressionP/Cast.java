package Parser.ExpressionP;

/**
 * cast
 * 𝑒 ::= (𝑇) 𝑒
 */
public class Cast extends Expr {
    public String f; // of the type
    public Expr expr;

    public Cast(String f, Expr expr) {
        super(5);
        this.f = f;
        this.expr = expr;
    }
}
