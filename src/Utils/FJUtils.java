package Utils;

import Parser.Definition;
import Parser.Expression;

import java.util.Arrays;
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
     * @param dictionnary
     * @param nameT1 = t
     * @param nameT2 = t'
     * @return
     */
    @Override
    public Boolean subtyping(Definition.CT dictionnary, String nameT1, String nameT2) {


        try {
            // compare if the two object
            if(nameT1.contentEquals(nameT2)) return true;

            //case (Data.Map.lookup t ct) of
            //If t1 don't exist on the dictionnary, we can't compare
            Definition.Type castNameTypeT1 = Definition.getInstance().new Type(nameT1);
            Definition.Type castNameTypeT2 = Definition.getInstance().new Type(nameT2);

            if(dictionnary.containsKey(castNameTypeT1)) {
                //compare if the first Object is a Class or an Interface


                //Is t1 a class or an Interface ?
                if(dictionnary.get(castNameTypeT1).EType == Definition.EType.CLASS) { //TODO définir condition
                    //t1 is a class
                    Definition.C classT1 = (Definition.C) dictionnary.get(castNameTypeT1);

                    //Class _ t'' il _ _ _)
                    //need to get : Class _ (name of the superclass) (list of interfaces implemented) _ _ _

                    //if (t' == t'' || Data.List.elem t' il) then
                    //"if (t2) equals to (superclass of t1) OR if (t2) (is one of the interfaces implemented by t1)
                    if(castNameTypeT2.equals(classT1.extensions[0].name) || Arrays.stream(classT1.implementations).toList().contains(castNameTypeT2)) {

                    }

                    return true;
                } else if(dictionnary.get(castNameTypeT1).EType == Definition.EType.INTERFACE) { //TODO définir condition
                    //t1 is an Interface
                    Definition.I interfaceT1 = (Definition.I) dictionnary.get(castNameTypeT1);

                    return true;
                }
            }

            return false;
        } catch(Exception e) {
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
