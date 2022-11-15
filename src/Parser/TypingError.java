package Parser;

/**
 * -- FJ + Lambda typing errors
 * ----------------------------
 * data TypeError = VariableNotFound String
 *                | FieldNotFound String
 *                | ClassNotFound String
 *                | MethodNotFound String String
 *                | ParamsTypeMismatch [(Expr,Type)]
 *                | WrongClosure String Expr
 *                | WrongCast String Expr
 *                | UnknownError Expr
 */
public class TypingError {
    public abstract class TypeError{}
    public class VariableNotFound extends TypeError {public String name;}
    public class FieldNotFound extends TypeError {public String name;}
    public class ClassNotFound extends TypeError {public String name;}
    public class MethodNotFound extends TypeError {public String name; public String type;}
    public class ParamsTypeMismatch extends TypeError {public MismatchType[] params;}
    public class WrongClosure extends TypeError { public String type; public Expression.Expr lambdaClosure;}
    public class WrongCast extends TypeError { public String type; public Expression.Expr expr;}
    public class UnknownError extends TypeError { public Expression.Expr expr;}

    /**
     * To be used by the ParamsTypeMismatch
     */
    public class MismatchType {
        public Definition.Type type;
        public Expression.Expr expr;
        public MismatchType(Definition.Type type, Expression.Expr expr) {
            this.type = type;
            this.expr = expr;
        }
    }
}
