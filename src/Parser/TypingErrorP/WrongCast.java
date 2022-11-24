package Parser.TypingErrorP;

import Parser.ExpressionP.Expr;

public class WrongCast extends TypeError {
    public String type;
    public Expr expr;

    public WrongCast(String type, Expr expr) {
        this.type = type;
        this.expr = expr;
    }
}
