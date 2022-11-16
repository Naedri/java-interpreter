package Utils;

import Parser.Definition;
import Parser.Expression;

import java.util.Optional;

public class FJUtils implements IUtils {
    //public static class subtyping(Definition.CT table, Class a, Class b){ }
    //public static class fields(Definition.CT table, Class a, Class b){ }
    /*public static class absmethods(Definition.CT table, Class a, Class b){ }
    public static class methods(Definition.CT table, Class a, Class b){ }
    public static class mtype(Definition.CT table, Class a, Class b){ }
    public static class mbody(Definition.CT table, Class a, Class b){ }*/
    //public static class isValue(Definition.CT table, Class a, Class b){ }
    //public static class lambdaMark(Definition.CT table, Class a, Class b){ }

    /**
     * -- Function: subtyping
     * -- Objective: Check classes for subtyping.
     * -- Params: Class table, Class A (ou interface A), Class B (ou interface B).
     * -- Returns: Returns if class A is subtype of class B.
     * @param table
     * @param a
     * @param b
     * @return
     */
    @Override
    public Boolean subtyping(Definition.CT table, Definition.T t1, Definition.T t2) {
        try {
            //TODO vérifier le equals si besoin de l'override ou d'utiliser hashcode
            if(t1.getClass().isInstance(t2.getClass())) {
                return true;
            }


            //case (Data.Map.lookup t ct) of
            //If t1 don't exist on the dictionnary, we can't compare
            if(table.containsKey(t1)) {
                //Is t1 a class or an Interface ?
                if(t1.EType == Definition.EType.CLASS) { //TODO définir condition
                    //t1 is a class


                    return true;
                } else if(t1.EType == Definition.EType.INTERFACE) { //TODO définir condition
                    //t1 is an Interface

                    return true;
                }
            }

            return false;
        } catch(Exception) {
            return false;
        }
    }

    /*@Override
    public Optional fields(Definition.CT table, Class a, Class b) {
        return Optional.empty();
    }

    @Override
    public Boolean isValue(Definition.CT table, Expression.Expr exp) {
        return null;
    }

    @Override
    public Expression.Expr lambdaMark(Expression.Expr exp, Definition.EType a) {
        return null;
    }

    public static Expression.Expr removeRuntimeAnnotation(Expression.Expr exp){ return null; }*/
}
