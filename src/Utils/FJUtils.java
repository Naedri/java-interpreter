package Utils;

import Parser.DefinitionP.*;
import Parser.ExpressionP.Cast;
import Parser.ExpressionP.Closure;
import Parser.ExpressionP.CreateObject;
import Parser.ExpressionP.Expr;

import java.util.TreeSet;

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
     *
     * @param dictionnary class table of the code
     * @param nameT1      = t = class or interface A
     * @param nameT2      = t' = class or interface B
     * @return true if A is a subtype of B
     */
    //TODO si on teste avant que t2 est une classe ou interface, on peut simplifier les tests
    //TODO passer en pattern visiteur
    public static Boolean subtyping(CT dictionnary, String nameT1, String nameT2) {
        try {
            // compare if the two object
            if (nameT1.contentEquals(nameT2)) return true;

            //case (Data.Map.lookup t ct) of
            //If t1 don't exist on the dictionnary, we can't compare
            Type castNameTypeT1 = new Type(nameT1);
            Type castNameTypeT2 = new Type(nameT2);

            if (dictionnary.containsKey(castNameTypeT1)) {
                //Is t1 a class or an Interface ?
                if (dictionnary.get(castNameTypeT1).eType == EType.CLASS) { //TODO définir condition
                    //t1 is a class
                    C classT1 = (C) dictionnary.get(castNameTypeT1);
                    T superclassT1 = classT1.extensions.first();
                    //Definition.T superclassT1 = classT1.extensions[0];
                    //Class _ t'' il _ _ _
                    //need to get : Class _ (name of the superclass) (list of interfaces implemented) _ _ _

                    //if (t' == t'' || Data.List.elem t' il) then
                    //"if (t2) equals to (superclass of t1) OR if (t2) (is one of the interfaces implemented by t1) --> 2nd condition is possible because we don't know if t2 CLASS or INTERFACE
                    //if (castNameTypeT2.equals(superclassT1.name) || Arrays.stream(classT1.implementations).toList().contains(castNameTypeT2)) {
                    if (castNameTypeT2.equals(superclassT1.name) || classT1.implementations.contains(castNameTypeT2)) {
                        return true;
                    } else {
                        //subtyping ct t'' t' || Data.List.any (\t'' -> subtyping ct t'' t') il
                        //"if (superclass of t1) is subtype of t2 OR "is there at least one of the interfaces implemented by t1 that is a subclass of t2"

                        //return (subtyping(dictionnary, superclassT1.name, nameT2) || Arrays.stream(classT1.implementations).anyMatch(i -> subtyping(dictionnary, i.name, nameT2)));
                        return (subtyping(dictionnary, superclassT1.name, nameT2) || classT1.implementations.stream().anyMatch(i -> subtyping(dictionnary, i.name, nameT2)));
                    }
                } else if (dictionnary.get(castNameTypeT1).eType == EType.INTERFACE) { //TODO définir condition
                    //t1 is an Interface
                    I interfaceT1 = (I) dictionnary.get(castNameTypeT1);
                    //Interface _ il _ _
                    //todo Need to get : Interface

                    //Data.List.elem t' il || Data.List.any (\t'' -> subtyping ct t'' t') il
                    //"if (t2) (is one of the interfaces implemented by t1) OR "is there at least one of the interfaces implemented by t1 that is a subclass of t2"
                    //return (Arrays.stream(interfaceT1.implementations).toList().contains(castNameTypeT2) || Arrays.stream(interfaceT1.implementations).anyMatch(i -> subtyping(dictionnary, i.name, nameT2)));
                    return (interfaceT1.implementations.contains(castNameTypeT2) || interfaceT1.implementations.stream().anyMatch(i -> subtyping(dictionnary, i.name, nameT2)));
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO return null ou liste vide ? --> return list pour utiliser la récursivité et compléter la liste suppérieure
    public static TreeSet<Field> fields(CT dictionnary, String nameT1) {
        TreeSet<Field> listFields = new TreeSet<>(new Comparators.FieldComparator());

        if (nameT1.contentEquals("Object")) return listFields;

        Type castNameTypeT1 = new Type(nameT1);

        //case (Data.Map.lookup c ct) of Just (TClass (Class _ c'' _ attrs _ _)) ->
        if (dictionnary.containsKey(castNameTypeT1) && dictionnary.get(castNameTypeT1).eType == EType.CLASS) {
            C classT1 = (C) dictionnary.get(castNameTypeT1);
            //Definition.T superclassT1 = classT1.extensions[0];
            T superclassT1 = classT1.extensions.first();

            //case (fields ct c'') of    Just base -> Just (base ++ attrs)
            //We take the fiedls of the superclass T and we add the fields of the class
            listFields.addAll(fields(dictionnary, superclassT1.name));
            //listFields.addAll(classT1.declaration.fields);
        }

        return listFields;
    }


    /**
     * -- Function: isValue
     * -- Objective: Check if an expression represents a value.
     * -- Params: Class table, Expression.
     * -- Returns: Boolean indicating if an expression is a value.
     * -----------------------------------------------------------
     *
     * @param dictionnary class table
     * @param exp         Expression to test
     * @return Boolean indicating if an expression is a value.
     */
    public static Boolean isValue(CT dictionnary, Expr exp) {

        //is an Object instanciation ?
        if (exp instanceof CreateObject) {
            //isValue _ (CreateObject c []) = True
            //is an Object instanciation with no Expr? Expr = CreateObject String [Expr] => same as the next, but "all (?) [] = True"
            if (((CreateObject) exp).params.size() == 0) {
                return true;
            } else {
                //isValue ct (CreateObject c p) = Data.List.all (isValue ct) p    --is an Object instanciation with an Expr ? Expr = CreateObject String [Expr]  => is there all element of p that are defined in CT ?
                for (Expr expr : ((CreateObject) exp).params) {
                    if (!isValue(dictionnary, expr)) return false;
                }
                return true;
            }
        }

        //isValue ct (Closure _ _) = True
        //is a lambda expression ?
        if (exp instanceof Closure) return true;

        //isValue ct (Cast _ (Closure _ _)) = True
        //is a "cast" of a lambda expression ?
        return exp instanceof Cast && ((Cast) exp).expr instanceof Closure;
    }

    /*@Override
    public Expression.Expr lambdaMark(Expression.Expr exp, EType a) {
        return null;
    }

    public static Expression.Expr removeRuntimeAnnotation(Expression.Expr exp){ return null; }*/
}
