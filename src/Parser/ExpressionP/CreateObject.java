package Parser.ExpressionP;

import java.util.TreeSet;

/**
 * object creation/instantiation
 * 𝑒 ::= new 𝐶(𝑒)
 */
public class CreateObject extends Expr {
    public String name; // of the class
    public TreeSet<Expr> params;

    public CreateObject(String name, TreeSet<Expr> params) {
        this.name = name;
        this.params = params;
    }
}
