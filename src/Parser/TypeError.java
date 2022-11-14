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
public class TypeError {
}
