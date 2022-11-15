package Parser;

/**
 * -- FJ + Lambda typing errors
 * ----------------------------
 * data TypeError = VariableNotFound String
 * | FieldNotFound String
 * | ClassNotFound String
 * | MethodNotFound String String
 * | ParamsTypeMismatch [(Expr,Type)]
 * | WrongClosure String Expr
 * | WrongCast String Expr
 * | UnknownError Expr
 */
public class TypingError {
    public abstract class TypeError {
    }

    public class VariableNotFound extends TypeError {
        public String name;

        public VariableNotFound(String name) {
            this.name = name;
        }
    }

    public class FieldNotFound extends TypeError {
        public String name;

        public FieldNotFound(String name) {
            this.name = name;
        }
    }

    public class ClassNotFound extends TypeError {
        public String name;

        public ClassNotFound(String name) {
            this.name = name;
        }
    }

    public class MethodNotFound extends TypeError {
        public String name;
        public String type;

        public MethodNotFound(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    public class ParamsTypeMismatch extends TypeError {
        public MismatchType[] params;

        public ParamsTypeMismatch(MismatchType[] params) {
            this.params = params;
        }
    }

    public class WrongClosure extends TypeError {
        public String type;
        public Expression.Expr lambdaClosure;

        public WrongClosure(String type, Expression.Expr lambdaClosure) {
            this.type = type;
            this.lambdaClosure = lambdaClosure;
        }
    }

    public class WrongCast extends TypeError {
        public String type;
        public Expression.Expr expr;

        public WrongCast(String type, Expression.Expr expr) {
            this.type = type;
            this.expr = expr;
        }
    }

    public class UnknownError extends TypeError {
        public Expression.Expr expr;

        public UnknownError(Expression.Expr expr) {
            this.expr = expr;
        }
    }

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
