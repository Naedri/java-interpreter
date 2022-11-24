package Parser.ExpressionP;

import java.util.List;

/**
 * object creation/instantiation
 * 𝑒 ::= new 𝐶(𝑒)
 */
public class CreateObject extends Expr {
    public String name; // of the class
    public List<Expr> params;

    public CreateObject(String name, List<Expr> params) {
        this.name = name;
        this.params = params;
    }
}
