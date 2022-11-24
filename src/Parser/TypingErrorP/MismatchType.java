package Parser.TypingErrorP;

import Parser.DefinitionP.Type;
import Parser.ExpressionP.Expr;

/**
 * To be used by the ParamsTypeMismatch
 */
public class MismatchType {
    public Type type;
    public Expr expr;

    public MismatchType(Type type, Expr expr) {
        this.type = type;
        this.expr = expr;
    }
}
