package Parser.ExpressionP;

import java.util.Comparator;

/**
 * data Expr = Var String                     -- Variable
 * | FieldAccess Expr String                  -- Field Access
 * | MethodInvk Expr String [Expr]            -- Method Invocation
 * | CreateObject String [Expr]               -- Object Instantiation
 * | Cast String Expr                         -- Cast
 * | Closure [(Type,String)] Expr             -- Closure
 */
public abstract class Expr implements Comparator<Expr> {
    public int order;

    public Expr(int order) {
        this.order = order;
    }

    @Override
    public int compare(Expr o1, Expr o2) {
        return Integer.compare(o1.order, o2.order);
    }

}
