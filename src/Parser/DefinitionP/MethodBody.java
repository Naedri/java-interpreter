package Parser.DefinitionP;

import Parser.ExpressionP.Expr;

import java.util.TreeSet;

/**
 * Class created as a wrapper for the return of the method FJUtils.mbody
 */
public class MethodBody {
    public TreeSet<String> nameFields;
    public Expr body;

    public MethodBody(Expr body) {
        this.nameFields = new TreeSet<>();
        this.body = body;
    }

    public MethodBody(TreeSet<String> nameFields, Expr body) {
        this.nameFields = nameFields;
        this.body = body;
    }
}
