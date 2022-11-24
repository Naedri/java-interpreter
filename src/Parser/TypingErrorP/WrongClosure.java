package Parser.TypingErrorP;

import Parser.ExpressionP.Expr;

public class WrongClosure extends TypeError {
    public String type;
    public Expr lambdaClosure;

    public WrongClosure(String type, Expr lambdaClosure) {
        this.type = type;
        this.lambdaClosure = lambdaClosure;
    }
}
