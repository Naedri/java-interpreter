package Parser.ExpressionP;


/**
 * data Expr = Var String                     -- Variable
 * | FieldAccess Expr String                  -- Field Access
 * | MethodInvk Expr String [Expr]            -- Method Invocation
 * | CreateObject String [Expr]               -- Object Instantiation
 * | Cast String Expr                         -- Cast
 * | Closure [(Type,String)] Expr             -- Closure
 */
public abstract class Expr {
}
