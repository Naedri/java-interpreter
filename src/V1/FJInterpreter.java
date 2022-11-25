package V1;

import Parser.DefinitionP.CT;
import Parser.DefinitionP.Comparators;
import Parser.DefinitionP.Field;
import Parser.DefinitionP.MethodBody;
import Parser.ExpressionP.*;
import Utils.FJUtils;

import java.util.TreeSet;

public class FJInterpreter {
    //CT = Map de type String (pour le nom de la classe), Expression (le type Expr en haskell)

    /**
     * Function eval'
     * Objective : Evaluate an expression
     * Params : Class table, Expression
     * Returns : An expression after processing one reduction step
     */
    public static Expr evalPrime(CT dictionnary, Expr expression) {
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
        if (expression instanceof Closure cl) {
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
     *
     * @see CreateObject
     */
    private static Expr evalPrimeAsCreateObject(CT dictionnary, CreateObject createObject) {
        TreeSet<Expr> p2 = new TreeSet<>(new Comparators.ExprComparator());

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
     *
     * @see FieldAccess
     */
    private static Expr evalPrimeAsFieldAccess(CT dictionnary, FieldAccess fieldAccess) {
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
                        Expr[] params = (Expr[]) e.params.toArray();
                        return params[idx];
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
     *
     * @see MethodInvk
     */
    private static Expr evalPrimeAsMethodInvk(CT dictionnary, MethodInvk methodInvk) {
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
                    if (methodBody != null) {
                        //subst (fpn ++ ["this"]) (p ++ [e]) e' //TODO possible en immutable en créeant une copie locale. Nécessaire ?

                        TreeSet<String> copyNameFields = methodBody.nameFields; //(fpn ++ ["this"])
                        copyNameFields.add("this");

                        TreeSet<Expr> copyParams = methodInvk.params; //(p ++ [e])
                        copyParams.add(methodInvk.parent);

                        return subst(copyNameFields, copyParams, methodBody.body);
                    }
                } else if (methodInvk.parent instanceof Cast castParent && ((Cast) methodInvk.parent).expr instanceof Closure) { //(Cast i (Closure cp exp)) ->
                    Closure closureParent = (Closure) castParent.expr;

                    //case (mbody ct m i) of    Just (fpn, e')
                    MethodBody mbody = FJUtils.mbody(dictionnary, methodInvk.name, castParent.f);
                    if (mbody != null) {
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
                TreeSet<Expr> pPrime = new TreeSet<>(new Comparators.ExprComparator());

                //param = x
                Expr result;
                for (Expr param : methodInvk.params) {
                    result = evalPrime(dictionnary, param);
                    if (result != null)
                        pPrime.add(result);
                }

                //in Just (MethodInvk e m p')
                return new MethodInvk(methodInvk.parent, methodInvk.name, pPrime);
            }
        } else {
            // -- RC-Invk-Recv
            //Just e' -> Just (MethodInvk e' m p)
            Expr ePrime = evalPrime(dictionnary, methodInvk.parent);

            if (ePrime != null) {
                return new MethodInvk(ePrime, methodInvk.name, methodInvk.params);
            }
        }

        return null;
    }

    /**
     * sub method for evalPrime() in case of expression is a Cast
     *
     * @see Cast
     */
    private static Expr evalPrimeAsCast(CT dictionnary, Cast cast) {
        //cast.f = t, cast.expr = e
        //if (isValue ct e)
        if (FJUtils.isValue(dictionnary, cast.expr)) {
            //obj@(CreateObject c' p)
            if (cast.expr instanceof CreateObject obj) {
                if (FJUtils.subtyping(dictionnary, obj.name, cast.f)) {
                    //-- R-Cast
                    return obj;
                }
            } else if (cast.expr instanceof Cast castParent && castParent.expr instanceof Closure) {
                if (FJUtils.subtyping(dictionnary, castParent.f, cast.f)) {
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
            if (ePrime != null) {
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
    public static Expr eval(CT dictionnary, Expr expr) {
        if (FJUtils.isValue(dictionnary, expr)) {
            return expr;
        } else {
            //maybe e (eval ct) (eval' ct e)
            if (eval(dictionnary, evalPrime(dictionnary, expr)) != null) {
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
     *
     * @param paramNames     List of formal parameters names
     * @param params         List of actual parameters
     * @param bodyExpression Method body expression
     * @return
     */
    public static Expr subst(TreeSet<String> paramNames, TreeSet<Expr> params, Expr bodyExpression) {
        //subst p v (Var x) : p = paramNames, v = params, (Var x) = bodyExpression
        if (bodyExpression instanceof Var var) {
            return substAsVar(paramNames, params, var);
        }

        //subst p v (FieldAccess e f) : p = paramNames, v = params, e = bodyExpression.ownerCLass, f = bodyExpression.name
        if (bodyExpression instanceof FieldAccess fieldAccess) {
            return substAsFieldAccess(paramNames, params, fieldAccess);
        }

        //subst p v (MethodInvk e n ap): p = paramNames, v = params, e = bodyExpression.parent, n = bodyExpression.name, ap = bodyExpression.params
        if (bodyExpression instanceof MethodInvk methodInvk) {
            return substAsMethodInvk(paramNames, params, methodInvk);
        }

        //subst p v (CreateObject c ap): p = paramNames, v = params, c = bodyExpression.name, ap = bodyExpression.params
        if (bodyExpression instanceof CreateObject createObject) {
            return substAsCreateObject(paramNames, params, createObject);
        }

        //subst p v (Cast c e): p = paramNames, v = params, c = bodyExpression.f, e = bodyExpression.expr
        if (bodyExpression instanceof Cast cast) {
            return substAsCast(paramNames, params, cast);
        }

        //subst p v cl@(Closure cp e): p = paramNames, v = params, cp = bodyExpression.params, e = bodyExpression.body
        if (bodyExpression instanceof Closure closure) {
            //subst p v cl@(Closure cp e) = Just cl  --Do nothing
            return closure;
        }

        return null;
    }

    /**
     * sub method for subst() in case of expression is a Var
     *
     * @see Var
     */
    private static Expr substAsVar(TreeSet<String> paramNames, TreeSet<Expr> params, Var var) {
        //subst p v (Var x) : p = paramNames, v = params, (Var x) = bodyExpression

        //case (Data.List.elemIndex x p) of   Just idx
        //int idx = paramNames.stream().findFirst(var.name);
        int idx = -1; //-1 to use idx++ directly in the for in case of x is the first element of paramNames
        boolean found = false; //to avoid to return the last index of the list if there is no x in paramNames
        for (String paramName : paramNames) {
            idx++;
            if (paramName.contentEquals(var.name)) {
                found = true;
                break;
            }
        }

        if (found) {
            //Just (v !! idx)
            Expr[] paramsTab = (Expr[]) params.toArray();
            return paramsTab[idx];
        }

        return null;
    }

    /**
     * sub method for subst() in case of expression is a FieldAccess
     *
     * @see FieldAccess
     */
    private static Expr substAsFieldAccess(TreeSet<String> paramNames, TreeSet<Expr> params, FieldAccess fieldAccess) {
        //subst p v (FieldAccess e f) : p = paramNames, v = params, e = fieldAccess.ownerCLass, f = fieldAccess.name

        //case (subst p v e) of   Just e'
        Expr substResult = subst(paramNames, params, fieldAccess.ownerCLass);
        if (substResult != null) {
            //Just (FieldAccess e' f)
            return new FieldAccess(substResult, fieldAccess.name);
        }

        return null;
    }

    /**
     * sub method for subst() in case of expression is a MethodInvk
     *
     * @see MethodInvk
     */
    private static Expr substAsMethodInvk(TreeSet<String> paramNames, TreeSet<Expr> params, MethodInvk methodInvk) {
        //subst p v (MethodInvk e n ap): p = paramNames, v = params, e = bodyExpression.parent, n = methodInvk.name, ap = methodInvk.params

        // case (subst p v e) of    Just e' -> Just (MethodInvk e' n ap')
        //if ePrime is null, no need to create ap'
        Expr ePrime = subst(paramNames, params, methodInvk.parent);

        if (ePrime != null) {
            //let ap' = Data.List.map (\x -> case (subst p v x) of Just x' -> x') ap
            TreeSet<Expr> apPrime = new TreeSet<>(new Comparators.ExprComparator());

            Expr substResult;
            for (Expr param : methodInvk.params) {
                substResult = subst(paramNames, params, param);
                if (substResult != null)
                    apPrime.add(substResult);
            }

            return new MethodInvk(ePrime, methodInvk.name, apPrime);
        }

        return null;
    }

    /**
     * sub method for subst() in case of expression is a CreateObject
     *
     * @see CreateObject
     */
    private static Expr substAsCreateObject(TreeSet<String> paramNames, TreeSet<Expr> params, CreateObject createObject) {
        //subst p v (CreateObject c ap): p = paramNames, v = params, c = createObject.name, ap = createObject.params

        //let ap' = Data.List.map (\x -> case (subst p v x) of Just x' -> x') ap
        TreeSet<Expr> apPrime = new TreeSet<>(new Comparators.ExprComparator());

        Expr substResult;
        for (Expr param : createObject.params) {
            substResult = subst(paramNames, params, param);
            if (substResult != null)
                apPrime.add(substResult);
        }

        return new CreateObject(createObject.name, apPrime);
    }

    /**
     * sub method for subst() in case of expression is a Cast
     *
     * @see Cast
     */
    private static Expr substAsCast(TreeSet<String> paramNames, TreeSet<Expr> params, Cast cast) {
        //subst p v (Cast c e): p = paramNames, v = params, c = cast.f, e = cast.expr

        //case (subst p v e) of     Just e' -> Just (Cast c e')
        Expr substResult = subst(paramNames, params, cast.expr);
        if (substResult != null) {
            return new Cast(cast.f, substResult);
        }

        return null;
    }

}
