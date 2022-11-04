package V1;

import Utils.CreateObject;
import Utils.Expression;
import Utils.FieldAccess;
import Utils.MethodInvk;

import java.util.Map;

public class FJInterpreter extends Object {

    /**
     * Function eval'
     * Objective : Evaluate an expression
     * Params : Class table, Expression
     * Returns : An expression after processing one reduction step
     */
    //TODO définir type retour
    //Expr pour surcharge ?
    //Retourne soit une valeur soit null
    public static Expression eval(Map classTable, CreateObject expression) {
        String p2 = null;


        return null;
    }

    public static Expression eval(Map classTable, MethodInvk expression) {

        return null;
    }

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
