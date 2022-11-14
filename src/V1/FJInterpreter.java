package V1;

import Parser.Definition;
import Parser.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FJInterpreter extends Object implements IInterpreter {
    //TODO conversion de eval par des surcharges
    //CT = Map de type String (pour le nom de la classe), Expression (le type Expr en haskell)

    /**
     * Function eval'
     * Objective : Evaluate an expression
     * Params : Class table, Expression
     * Returns : An expression after processing one reduction step
     */
    //TODO définir type retour
    //Expr pour surcharge ?
    //Retourne soit une valeur soit null
    //TODO conversion de la méthode
    @Override
    public Expression.Expr evalPrime(Definition.CT classTable, Expression.Expr expression) {
        Map p2 = new HashMap<String, Expression>();

       /* for(Map.Entry<String, Expr> entry : classTable.entrySet()) {
            //Expr reduction = eval(classTable, entry.getValue());
        }*/

        /*
        if(true) {
            return new CreateObject();
            //eval' ct (CreateObject c p) = -- RC-New-Arg
        } else {
            return null; //Maybe Nothing
        }*/

        return null;
    }

    @Override
    public Optional<Expression.Expr> subst(String[] paramNames, Expression.Expr[] params, Expression.Expr bodyExpression) {
        return Optional.empty();
    }

    //TODO doit tester R-Field et RC-Field
    /*
    public static Expr eval(Map<String, Expr> classTable, Expr.FieldAccess expression) {
        return null;
    }*/

   /* public static Expression eval(Map<String, Expression> classTable, MethodInvk expression) {

        return null;
    }*/

    //eval' ct cc@(Cast t e)
    /*public static Expression eval(Map classTable, CreateObject expression) {

        return null;
    }*/

    //eval' ct cl@(Closure _ _) = Just cl
    /*public static Expression eval(Map classTable, CreateObject expression) {

        return null;
    }*/

    //eval' _ _ = Nothing
    /*public static Expression eval(Map classTable, CreateObject expression) {

        return null;
    }*/
}
