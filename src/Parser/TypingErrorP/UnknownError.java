package Parser.TypingErrorP;

import Parser.ExpressionP.Expr;

public class UnknownError extends TypeError {
    public Expr expr;

    public UnknownError(Expr expr) {
        this.expr = expr;
    }
}
