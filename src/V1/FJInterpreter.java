package V1;

import Parser.DefinitionP.CT;
import Parser.DefinitionP.Field;
import Parser.DefinitionP.MethodBody;
import Parser.ExpressionP.*;
import Utils.FJUtils;

import java.util.ArrayList;
import java.util.TreeSet;

public class FJInterpreter implements IInterpreter {
    //CT = Map de type String (pour le nom de la classe), Expression (le type Expr en haskell)

    /**
     * Function eval'
     * Objective : Evaluate an expression
     * Params : Class table, Expression
     * Returns : An expression after processing one reduction step
     */
    @Override
    public Expr evalPrime(CT dictionnary, Expr expression) {
        // eval' ct (CreateObject c p) = -- RC-New-Arg
        if (expression instanceof CreateObject createObject) {
            return evalPrimeAsCreateObject(dictionnary, createObject);
        }

        // eval' ct (FieldAccess e f)
        //return the param searched in FieldAccess in the dictionnary
        if (expression instanceof FieldAccess fieldAccess) {
            return evalPrimeAsFieldAccess(dictionnary, fieldAccess);
        }

        // eval' ct (MethodInvk e m p) =
        //e = parent, m = name, p = params
        if (expression instanceof MethodInvk methodInvk) {
            return evalPrimeAsMethodInvk(dictionnary, methodInvk);
        }

        // eval' ct cc@(Cast t e) =
        //le cc@ peux être lu comme :    let cc = Cast t e    in eval'(CT ct, cc)
        if (expression instanceof Cast cast) {
            return evalPrimeAsCast(dictionnary, cast);
        }

        // eval' ct cl@(Closure _ _) = Just cl
        if(expression instanceof Closure cl) {
            return cl;
        } else {
            return null;
        }

        //eval' _ _ = Nothing
        //code unreachable
        //return null;
    }

    /**
     * sub method for evalPrime() in case of expression is a CreateObject
     * @see CreateObject
     */
    private Expr evalPrimeAsCreateObject(CT dictionnary, CreateObject createObject) {
        ArrayList<Expr> p2 = new ArrayList<>();

        /*let p' = Data.List.map (\x -> case (eval' ct x) of Just x' -> x') p
            in Just (CreateObject c p')*/
        for (Expr pElem : createObject.params) {
            //eval' ct x
            if (evalPrime(dictionnary, pElem) != null) { //TODO vérifier null ou liste vide ou autre
                p2.add(evalPrime(dictionnary, pElem));
            }
        }

        createObject.params = p2;
        return createObject;
    }

    /**
     * sub method for evalPrime() in case of expression is a FieldAccess
     * @see FieldAccess
     */
    private Expr evalPrimeAsFieldAccess(CT dictionnary, FieldAccess fieldAccess) {
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
                        if (fieldAccess.name.contentEquals(fieldToCompare.nameField)) {
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
                }
            }
        } else {
            //RC-Field
            //case (eval' ct e) of
            //      Just e' -> Just (FieldAccess e' f)
            //      _ -> Nothing
            if (evalPrime(dictionnary, fieldAccess.ownerCLass) != null) {
                fieldAccess.ownerCLass = evalPrime(dictionnary, fieldAccess.ownerCLass);
                return fieldAccess;
            }
        }

        return null;
    }

    /**
     * sub method for evalPrime() in case of expression is a MethodInvk
     * @see MethodInvk
     */
    private Expr evalPrimeAsMethodInvk(CT dictionnary, MethodInvk methodInvk) {
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
                    //-- R-Invk

                    //case (mbody ct m c) of    Just (fpn, e')
                    MethodBody methodBody = FJUtils.mbody(dictionnary, methodInvk.name, e.name);
                    if(methodBody != null) {
                        //subst (fpn ++ ["this"]) (p ++ [e]) e' //TODO possible en immutable en créeant une copie locale. Nécessaire ?

                        TreeSet<String> copyNameFields = methodBody.nameFields; //(fpn ++ ["this"])
                        copyNameFields.add("this");

                        TreeSet<Expr> copyParams = methodInvk.params; //(p ++ [e])
                        copyParams.add(methodInvk.parent);

                        return subst(copyNameFields, copyParams, methodBody.body);
                    }
                } else if (methodInvk.parent instanceof Cast && ((Cast) methodInvk.parent).expr instanceof Closure) { //(Cast i (Closure cp exp)) ->
                    //TODO
                    Cast castParent = (Cast) methodInvk.parent;
                    Closure closureParent = (Closure) castParent.expr;

                    //case (mbody ct m i) of    Just (fpn, e')
                    MethodBody mbody = FJUtils.mbody(dictionnary, methodInvk.name, castParent.f);
                    if(mbody != null) {
                        // --R-Default
                        return subst(mbody.nameFields, methodInvk.params, mbody.body);
                    } else {
                        // --R-Lam

                        //subst (snd (unzip cp)) p exp
                        TreeSet<String> closureParentParamsNameFields = new TreeSet<>();//snd (unzip cp)
                        for (Field param : closureParent.params) {
                            closureParentParamsNameFields.add(param.nameField);
                        }

                        return subst(closureParentParamsNameFields, methodInvk.params, closureParent.body);
                    }
                }
            } else {
                // RC-Invk-Arg
                //let p' = Data.List.map (\x -> case (eval' ct x) of Just x' -> x') p
                TreeSet<Expr> pPrime = new TreeSet<>();

                //param = x
                Expr result;
                for (Expr param : methodInvk.params) {
                    result = evalPrime(dictionnary, param);
                    if(result != null)
                        pPrime.add(result);
                }

                //in Just (MethodInvk e m p')
                return new MethodInvk(methodInvk.parent, methodInvk.name, pPrime);
            }
        } else {
            // -- RC-Invk-Recv
            //Just e' -> Just (MethodInvk e' m p)
            Expr ePrime = evalPrime(dictionnary, methodInvk.parent);

            if(ePrime != null) {
                return new MethodInvk(ePrime, methodInvk.name, methodInvk.params);
            }
        }

        return null;
    }

    /**
     * sub method for evalPrime() in case of expression is a Cast
     * @see Cast
     */
    private Expr evalPrimeAsCast(CT dictionnary, Cast cast) {
        //cast.f = t, cast.expr = e
        //if (isValue ct e)
        if(FJUtils.isValue(dictionnary, cast.expr)) {
            //obj@(CreateObject c' p)
            if(cast.expr instanceof CreateObject obj) {
                if(FJUtils.subtyping(dictionnary, obj.name, cast.f)) {
                    //-- R-Cast
                    return obj;
                }
            } else if(cast.expr instanceof Cast castParent && castParent.expr instanceof Closure) {
                if(FJUtils.subtyping(dictionnary, castParent.f, cast.f)) {
                    //-- R-Cast-Lam
                    return castParent;
                }
            } else {
                //-- annotated lambda expression is a value
                return cast;
            }
        } else {
            //case (eval' ct e) of       Just e' -> Just (Cast t e')
            Expr ePrime = evalPrime(dictionnary, cast.expr);
            if(ePrime != null) {
                return new Cast(cast.f, ePrime);
            }
        }

        return null;
    }


    /**
     * -- Function: eval
     * -- Objective: Evaluate an expression recursively.
     * -- Params: Class table, Expression.
     * -- Returns: A value after all the reduction steps.
     */
    public Expr eval(CT dictionnary, Expr expr) {
        if(FJUtils.isValue(dictionnary, expr)) {
            return expr;
        } else {
            //maybe e (eval ct) (eval' ct e)
            if(eval(dictionnary, evalPrime(dictionnary, expr)) != null) {
                return eval(dictionnary, evalPrime(dictionnary, expr));
            } else {
                return expr;
            }
        }
    }


    /**
     * -- Function: subst
     * -- Objective: Replace actual parameters in method body expression.
     * -- Params: List of formal parameters names, List of actual parameters,
     * -- Method body expression.
     * -- Returns: A new changed expression.
     * @param paramNames     List of formal parameters names
     * @param params         List of actual parameters
     * @param bodyExpression Method body expression
     * @return
     */
    //TODO
    @Override
    public Expr subst(TreeSet<String> paramNames, TreeSet<Expr> params, Expr bodyExpression) {
        /*if(bodyExpression instanceof Var var) {

        }*/

        return null;
    }


}
