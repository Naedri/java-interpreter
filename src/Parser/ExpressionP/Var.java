package Parser.ExpressionP;

/**
 * variable
 * 𝑒 ::= 𝑥
 */
public class Var extends Expr {
    public String name;

    public Var(String name) {
        this.name = name;
    }
}
