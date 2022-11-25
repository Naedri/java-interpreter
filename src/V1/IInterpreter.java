package V1;

import Parser.DefinitionP.CT;
import Parser.ExpressionP.Expr;

import java.util.TreeSet;

public interface IInterpreter {
    /**
     * Objective: Evaluate an expression.
     *
     * @param classTable
     * @param expression
     * @return An expression after processing one reduction step
     */
    Expr evalPrime(CT classTable, Expr expression);

    /**
     * Replace actual parameters in method body expression.
     *
     * @param paramNames     List of formal parameters names
     * @param params         List of actual parameters
     * @param bodyExpression Method body expression
     * @return A new changed expression
     */
    Expr subst(TreeSet<String> paramNames, TreeSet<Expr> params, Expr bodyExpression);
}
