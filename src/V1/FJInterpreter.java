package V1;

import Parser.DefinitionP.CT;
import Parser.DefinitionP.Field;
import Parser.ExpressionP.*;
import Utils.FJUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

public class FJInterpreter implements IInterpreter {
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
    //TODO refactor de la méthode en séparant en sous fonction pour chaque type d'objet
    @Override
    public Expr evalPrime(CT dictionnary, Expr expression) {
        //eval' ct (CreateObject c p) = -- RC-New-Arg
        if (expression instanceof CreateObject) {
            ArrayList<Expr> p2 = new ArrayList<>();


            /*let p' = Data.List.map (\x -> case (eval' ct x) of Just x' -> x') p
            in Just (CreateObject c p')*/
            for (Expr pElem : ((CreateObject) expression).params) {
                //eval' ct x
                if (evalPrime(dictionnary, pElem) != null) { //TODO vérifier null ou liste vide ou autre
                    p2.add(evalPrime(dictionnary, pElem));
                }
            }

            ((CreateObject) expression).params = p2;
            return expression;
        }

        //eval' ct (FieldAccess e f)
        //return the param searched in FieldAccess in the dictionnary
        if (expression instanceof FieldAccess fieldAccess) {

            //if (isValue ct e) then -- R-Field
            if (FJUtils.isValue(dictionnary, fieldAccess.ownerCLass)) {
                //case e of      (CreateObject c p) ->
                if (fieldAccess.ownerCLass instanceof CreateObject e) {

                    //case (fields ct c) of             Just flds ->
                    TreeSet<Field> flds = FJUtils.fields(dictionnary, e.name);
                    if (flds.size() != 0) {
                        //case (Data.List.findIndex (\(tp,nm) -> f == nm) flds) of
                        int idx = 0;
                        boolean found = false;

                        for (Field fieldToCompare : flds) {
                            if (((FieldAccess) expression).name.contentEquals(fieldToCompare.nameField)) {
                                found = true;
                                break;
                            } else {
                                idx++;
                            }
                        }

                        //Just idx -> Just (p !! idx)
                        if (found) {
                            return e.params.get(idx);
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                //RC-Field
                //case (eval' ct e) of
                //      Just e' -> Just (FieldAccess e' f)
                //      _ -> Nothing
                if (evalPrime(dictionnary, ((FieldAccess) expression).ownerCLass) != null) {
                    fieldAccess.ownerCLass = evalPrime(dictionnary, ((FieldAccess) expression).ownerCLass);
                    return fieldAccess;
                } else {
                    return null;
                }
            }
        }

        //TODO eval' ct (MethodInvk e m p) =
        //e = parent, m = name, p = params
        if (expression instanceof MethodInvk methodInvk) {

            //if (isValue ct e) then -- expression existe dans le dico
            if (FJUtils.isValue(dictionnary, methodInvk.parent)) {
                //if (Data.List.all (isValue ct) p) then
                boolean isAllInCT = true;
                for (Expr expr : methodInvk.params) {
                    if (!FJUtils.isValue(dictionnary, expr)) {
                        isAllInCT = false;
                        break;
                    }
                }

                if (isAllInCT) {
                    //case e of      (CreateObject c p) ->
                    if (methodInvk.parent instanceof CreateObject e) {
                        //-- R-Invk -- modif quand V2
                        //TODO
                    } else if (methodInvk.parent instanceof Cast) {
                        //TODO
                    } else {
                        return null;
                    }
                } else {
                    //TODO -- RC-Invk-Arg
                }
            } else {
                //TODO -- RC-Invk-Recv
            }
        }


        //TODO eval' ct cc@(Cast t e) =

        //TODO eval' ct cl@(Closure _ _) = Just cl

        //eval' _ _ = Nothing
        return null;
    }

    @Override
    public Optional<Expr> subst(String[] paramNames, Expr[] params, Expr bodyExpression) {
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
