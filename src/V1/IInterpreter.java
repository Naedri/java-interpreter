package V1;

import Parser.Definition;
import Parser.Expression;
import Parser.Expression.Expr;

import java.util.Optional;

public interface IInterpreter {
    /**
     * Objective: Evaluate an expression.
     * @param classTable
     * @param expression
     * @return An expression after processing one reduction step
     */
    public Expr evalPrime(Definition.CT classTable, Expr expression);

    /**
     * Replace actual parameters in method body expression.
     * @param paramNames List of formal parameters names
     * @param params List of actual parameters
     * @param bodyExpression Method body expression
     * @return A new changed expression
     */
    public Optional<Expression.Expr> subst(String[] paramNames, Expression.Expr[] params, Expression.Expr bodyExpression);
}
