package Utils;

import Parser.Definition;
import Parser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
     * @param dictionnary class table of the code
     * @param nameT1 = t = class or interface A
     * @param nameT2 = t' = class or interface B
     * @return true if A is a subtype of B
     */
    //TODO si on teste avant que t2 est une classe ou interface, on peut simplifier les tests
    //TODO passer en pattern visiteur
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
                //Is t1 a class or an Interface ?
                if(dictionnary.get(castNameTypeT1).EType == Definition.EType.CLASS) { //TODO définir condition
                    //t1 is a class
                    Definition.C classT1 = (Definition.C) dictionnary.get(castNameTypeT1);
                    Definition.T superclassT1 = classT1.extensions[0];
                    //Class _ t'' il _ _ _
                    //need to get : Class _ (name of the superclass) (list of interfaces implemented) _ _ _

                    //if (t' == t'' || Data.List.elem t' il) then
                    //"if (t2) equals to (superclass of t1) OR if (t2) (is one of the interfaces implemented by t1) --> 2nd condition is possible because we don't know if t2 CLASS or INTERFACE
                    if(castNameTypeT2.equals(superclassT1.name) || Arrays.stream(classT1.implementations).toList().contains(castNameTypeT2)) {
                        return true;
                    } else {
                        //subtyping ct t'' t' || Data.List.any (\t'' -> subtyping ct t'' t') il
                        //"if (superclass of t1) is subtype of t2 OR "is there at least one of the interfaces implemented by t1 that is a subclass of t2"

                        return (subtyping(dictionnary, superclassT1.name, nameT2) || Arrays.stream(classT1.implementations).anyMatch(i -> subtyping(dictionnary, i.name, nameT2)));
                    }
                } else if(dictionnary.get(castNameTypeT1).EType == Definition.EType.INTERFACE) { //TODO définir condition
                    //t1 is an Interface
                    Definition.I interfaceT1 = (Definition.I) dictionnary.get(castNameTypeT1);
                    //Interface _ il _ _
                    //todo Need to get : Interface

                    //Data.List.elem t' il || Data.List.any (\t'' -> subtyping ct t'' t') il
                    //"if (t2) (is one of the interfaces implemented by t1) OR "is there at least one of the interfaces implemented by t1 that is a subclass of t2"
                    return (Arrays.stream(interfaceT1.implementations).toList().contains(castNameTypeT2) Arrays.stream(interfaceT1.implementations).anyMatch(i -> subtyping(dictionnary, i.name, nameT2));
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
