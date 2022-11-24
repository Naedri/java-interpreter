package Parser.ExpressionP;

import Parser.DefinitionP.Field;

import java.util.List;

/**
 * closure / 𝜆-expression
 * 𝑒 ::= (𝑇 𝑥) → 𝑒
 */
public class Closure extends Expr {
    public List<Field> params;
    public Expr body;

    public Closure(List<Field> params, Expr body) {
        this.params = params;
        this.body = body;
    }
}
