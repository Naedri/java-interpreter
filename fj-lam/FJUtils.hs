-- Utilitary functions for FJ + Lambda interpreter
-- Author: Samuel da Silva Feitosa
-- Date: 03/2018
--------------------------------------------------
module FJUtils where
import FJParser
import Data.Map
import Data.List

-- Function: subtyping
-- Objective: Check classes for subtyping.
-- Params: Class table, Class A (ou interface A), Class B (ou interface B).
-- Returns: Returns if class A is subtype of class B.
-----------------------------------------------------
subtyping :: CT -> String -> String -> Bool
subtyping ct t t' 
-- class A <=> t
-- class B <=> t'
  | t == t' = True -- comparaison du hash code (ne pas déterminer avec typeof, car on ne sait pas s'il renvoie le T le plus bas ou non => à déterminer dans intellij 
  | otherwise = 
      case (Data.Map.lookup t ct) of 
        -- renvoie un T ( => ligne 10 de FJParser.hs) => on va après le décomposer 
        -- on récupère t dans la ct : ct.get(t) ; est ce que t est un C ou un I ?
        Just (TClass (Class _ t'' il _ _ _)) ->
          -- Décomposition : Just (<datatype> (<constructeur> <params>)) => ligne 17 de FJParser.hs
          -- (T) t : C, avec TClass <=> nom de data type / Class <=> le nom du constructeur / _ / t'' <=> super type de la classe t càd classe A / il <=> liste des interfaces de t (classe A)
          if (t' == t'' || Data.List.elem t' il) then
            -- Data.List.elem t' il <=> regarde si t' est un élement de il <=> il se pose la question : est ce que la classe B est parmis les interfaces que A implémente <=> est ce que A implémente B
            True
          else 
            subtyping ct t'' t' || -- est ce que la superclasse de A est sous type de B
            Data.List.any (\t'' -> subtyping ct t'' t') il -- il.forEach(i -> {subtyping(ct)}) <=> est ce que parmis les interfaces/classes de A et ses sur-type, il y en a une qui est un sous-type de la classe B 
        Just (TInterface (Interface _ il _ _)) ->
          -- Décomposition : Just (<datatype> (<constructeur> <params>)) => ligne 23 de FJParser.hs
          -- t : I, avec t non classe A mais interface A
          Data.List.elem t' il || -- est ce que la classe B se trouve parmi les interfaces de A
          Data.List.any (\t'' -> subtyping ct t'' t') il -- si parmi les interface de A ou de leur sur-type
        _ -> False -- si c'est ni une classe ni une interface 
                 
-- Function: fields
-- Objective: Search for a class on class table and returns its fields.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the field list or Nothing.
-----------------------------------------------------------------------
fields :: CT -> String -> Maybe [(Type,String)]
fields _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas d'attributs 
fields ct c = case (Data.Map.lookup c ct) of -- on cherche dans la classeTable le nom de la classe
              --  si on retrouve la classe
                Just (TClass (Class _ c'' _ attrs _ _)) -> -- on décompose la classe retrouvée pour en extraire le nom de sa superclasse (c'') et les fields/attributs de la classe c (attrs)
                  case (fields ct c'') of -- dans le cas on l'on peut faire un appel récursif à fonction fields, on le fait
                    -- et si on récupère les attributs de la superclasse
                    Just base -> Just (base ++ attrs) -- on ajoute les nouveaux attributs (ceux de base - de la superclasse) (base) aux précédents (ceux des classes inférieures) (attrs)
                    -- et si on ne trouve rien
                    _ -> Nothing -- on ajoute rien de plus
                -- si on ne retrouve pas la classe
                _ -> Nothing -- on ne renvoie rien

-- Function: absmethods
-- Objective: Search for a class or interface on class table and returns its
-- abstract methods.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the method signature or Nothin.
-- /!\ Returns: Just[] for a concrete class
----------------------------------------------------------------------------
absmethods :: CT -> String -> Maybe [Sign]
absmethods _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas de méthode 
absmethods ct t = 
  case (Data.Map.lookup t ct) of -- on cherche dans la classeTable (ct) le nom de la classe/interface(t)
    -- si on retrouve le nom donné en tant que classe dans la CT
    Just (TClass (Class _ t' il _ _ _)) -> -- on décompose la classe retrouvée pour en extraire le nom de sa superclasse (t') et la liste des interfaces que cette classe implémente (il <=> implemented list)
      -- => ici (t) est une classe
      case (absmethods ct t') of  -- dans le cas on l'on peut faire un appel récursif à fonction absmethods, on le fait avec la classeTable (ct) et la superclasse (t')
        Just bam -> -- si on obtient qlq chose (càd une liste de signature de la superclasse <=> base abstract method <=> les méthodes non implémentées par la superclasse)
          -- on ajoute dans une liste (bam') les méthodes abstraites de toutes les interfaces implémentées par la classe (il)
          -- Data.List.concatMap permet d'obtenir à partir d'une liste des liste (2dimensions), une liste de méthodes (1dimension)
          let bam' = Data.List.concatMap (\i -> case (absmethods ct i) of
                                                  Just am -> am
                                                  _ -> []
                                         ) il
              -- on construit une nouvelle liste (bam'') qui contient la liste des méthodes abstraites (les signatures) sans doublons de bam et bam'' 
                -- bam signatures de la superclasse (t) <=> liste != bam' <=> signatures de l'ensemble des interfaces qu'implémente (t)
              -- unionBy est similaire à union, mais il permet de fournir un test d'égalité qui lorsqu'il est True n'ajoute pas les élements du membre de droite => on fait une left union
              bam'' = unionBy (\(Sign _ n' _) 
                                (Sign _ n'' _) -> n' == n'') bam bam'
              -- on récupère la liste des signatures methodes concrètes de la classe (t) et de la superclasse (t') (cam <=> concrete abstract method) 
              cam = case (methods ct t) of
                      Just meths -> 
                        case (methods ct t') of
                          Just bmeths -> 
                            unionBy (\(Sign _ n' _) 
                                      (Sign _ n'' _) -> n' == n'')
                              (Data.List.map (\(Method s e) -> s) meths)
                              (Data.List.map (\(Method s e) -> s) bmeths)
                          _ -> Data.List.map (\(Method s e) -> s) meths
                      _ -> []
            in Just (bam'' Data.List.\\ cam) -- on retire, de la liste des signatures des interfaces et de la superclasse de la classe (t) (bam''), les signatures des méthodes des concrètes de la classe (c) (avec '\\' <=> intersection)
        _ -> Nothing -- dans le cas on l'on ne peut pas faire d'appel récursif, ou que la liste des signatures retournée est vide, on n'ajoute rien
    -- si on retrouve le nom donné en tant que interface dans la CT
    Just (TInterface (Interface _ il ameths _)) -> -- on décompose l'interface retrouvée pour en extraire la liste des interface qu'elle étend (il) et la liste des signatures de méthode qu'elle contient (ameths <=> abstract methods)
    -- => ici (t) est une interface
      -- on ajoute dans une liste (bam) les méthodes abstraites de toutes les interfaces implémentées par l'interface (il)
      let bam = Data.List.concatMap (\i -> case (absmethods ct i) of
                                             Just am -> am
                                             _ -> []
                                    ) il
          -- on forme une liste (ameths') sans doublons qui contient les signatures de méthode de l'interface (ameths) et les signatures de méthodes des 'super' interfaces (bam) 
          ameths' = unionBy (\(Sign _ n' _) 
                              (Sign _ n'' _) -> n' == n'') ameths bam
        in Just (ameths')
    _ -> Nothing


-- Function: methods
-- Objective: Search for a class on class table and returns its methods.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the method list of Nothing.
------------------------------------------------------------------------
methods :: CT -> String -> Maybe [Method]
methods _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas de méthode 
methods ct t = 
  case (Data.Map.lookup t ct) of 
    -- on retourne sa définition si on la retrouve dans la ct
    Just (TClass (Class _ cb il _ _ meths)) -> -- on récupère la superclasse (cb), la liste des interfaces (il) et les méthodes concrètes (meths)
    -- => ici (t) est une classe
      case (methods ct cb) of
        Just bms -> -- les méthodes concrètes de la superclasse (bms <=> base concrete methods)
          -- concaténation dans une seule map (bim <=> base interface methods) les defaults méthodes de l'ensemble des interfaces (il)
          let bim = Data.List.concatMap (\i -> case (methods ct i) of
                                                 Just m -> m
                                                 _ -> []
                                        ) il
              -- left union des méthodes concrètes de la classe (t) avec les méthodes de la superclasse (bms)
              m' = unionBy (\(Method (Sign _ n' _) _)
                             (Method (Sign _ n'' _) _) -> n' == n'') meths bms
              -- left union des méthodes concrètes de la classe et la superclasse (m') avec les méthodes default des interfaces (bim)
              m'' = unionBy (\(Method (Sign _ n' _) _)
                              (Method (Sign _ n'' _) _) -> n' == n'') m' bim
            in Just m''
        _ -> Nothing
    Just (TInterface (Interface _ il _ defmeths)) -> -- on récupère la liste des interfaces (il) etendues par l'interface (t) et ses default methodes (defmeths)
    -- => ici (t) est une interface
      -- concaténation dans une seule map (bim <=> base interface methods) les defaults méthodes de l'ensemble des interfaces que l'interface (t) implémente (il)
      let bim = Data.List.concatMap (\i -> case (methods ct i) of 
                                             Just m -> m
                                             _ -> []
                                    ) il
          -- left union des defaults méthodes (defmeths) de l'interface (t) et de celles des 'super' interfaces (bim)  
          m' = unionBy (\(Method (Sign _ n' _) _)
                         (Method (Sign _ n'' _) _) -> n' == n'') defmeths bim
        in Just m'
    -- on retourne rien si on la ne retrouve pas dans la ct
    _ -> Nothing


-- Function: mtype
-- Objective: Search for a class on class table, then looks up for a method 
-- and returns its type.
-- Params: Class table, Method name, Class name.
-- Returns: A monad Maybe containing the method type.
---------------------------------------------------------------------------
mtype :: CT -> String -> String -> Maybe ([Type], Type)
mtype _ _ "Object" = Nothing
mtype ct m t = 
  case (absmethods ct t) of
    Just absmeths ->  -- si on a : Just [x] ou Just[x,x...] /!\ mais aussi Just [] (<=> cas pour une classe concrète) 
      case (Data.List.find (\(Sign _ m' _) -> m == m') absmeths) of -- dans les méthodes abstraites récupérées, on sélectione la méthode (m), en ne prennant que sa signature
        Just (Sign r _ p) -> Just (fst (unzip p), r) -- on récupère seulement le type des arguments de la méthode (m)
        -- avec unzip qui permet de prendre les types de tous les paramètres de méthodes (=> [(a,b)] --unzip-> ([a],[b]))   
        _ -> case (methods ct t) of -- si la classe est concrète
               Just meths -> 
                 case (Data.List.find 
                         (\(Method (Sign _ m' _) _) -> m == m') meths) of --- dans les méthodes abstraites récupérées, on sélectione la méthode (m), en ne prennant que sa signature
                   Just (Method (Sign r _ p) _) -> Just (fst (unzip p), r) -- même chose que précédement
                   _ -> Nothing
               _ -> Nothing
    -- /!\ la phrase suivante est fausse : si (t) présente n'a pas au moins une méthode abstraite (càd que (t) est une classe concrète)
    -- /!\ la phrase suivante est vrai : si la classe n'est pas trouvée dans la ct (car c'est le seul cas où Nothing est renvoyé par absmethods) 
    -- Les auteurs auraient dû mettre : Nothing -> Nothing
    _ -> Nothing -- => on ne renvoie rien

-- Function: mbody
-- Objective: Search for a class on class table, then looks up for a method
-- and returns its body.
-- Params: Class table, Method name, Class name.
-- Returns: A monad Maybe containing the method body or Nothing.
---------------------------------------------------------------------------
mbody :: CT -> String -> String -> Maybe ([String], Expr)
mbody _ _ "Object" = Nothing -- Object, which has no fields (and no methods), so the invocations of super have no arguments
mbody ct m t =
  case (methods ct t) of 
    Just meths -> case (Data.List.find (\(Method (Sign _ m' _) _) 
                          -> m == m') meths) of
      Just (Method (Sign _ _ p) e) -> Just (snd (unzip p), e)
      _ -> Nothing
    _ -> Nothing
 
-- Function: isValue 
-- Objective: Check if an expression represents a value.
-- Params: Class table, Expression.
-- Returns: Boolean indicating if an expression is a value.
-----------------------------------------------------------
isValue :: CT -> Expr -> Bool
-- si l'Expr fournie est de type CreateObject (-- Object Instantiation)
isValue _ (CreateObject c []) = True -- si l'on instancie un objet sans argument, pas la peine de regarder la validité des paramètres
isValue ct (CreateObject c p) = Data.List.all (isValue ct) p  -- si l'on instancie un objet avec argument, on appelle isValue sur chacun s'ils bien renvoient une valeur
-- si l'Expr fournie est de type Closure
isValue ct (Closure _ _) = True  -- <=> isValue _ (Closure _ _) = True
-- si l'Expr fournie est de type Cast d'une Closure
isValue ct (Cast _ (Closure _ _)) = True -- isValue _ (Cast _ (Closure _ _)) = True
-- pour les autres cas de Expr (càd Var, FieldAccess et MethodInvk)
isValue _ _ = False

-- Function: lambdaMark
-- Objective: Annotate the types for lambda expressions.
-- Params: Expression, Type.
-- Returns: A lambda expression annotated with its type, or the expression if
-- it is not a lambda expression.
-----------------------------------------------------------------------------
lambdaMark :: Expr -> Type -> Expr
lambdaMark c@(Closure _ _) (Type t) = Cast t c
lambdaMark e _ = e


-- Function: removeRuntimeAnnotation
-- Objective: Remove runtime annotations in lambda expressions.
-- Params: Expression.
-- Returns: An expression without the runtime type annotations.
---------------------------------------------------------------
removeRuntimeAnnotation :: Expr -> Expr
removeRuntimeAnnotation (FieldAccess e f) = 
  let e' = removeRuntimeAnnotation e
    in FieldAccess e' f
removeRuntimeAnnotation (MethodInvk e m p) = 
  let e' = removeRuntimeAnnotation e
      p' = Data.List.map removeRuntimeAnnotation p
    in MethodInvk e' m p'
removeRuntimeAnnotation (CreateObject c p) = 
  let p' = Data.List.map removeRuntimeAnnotation p
    in CreateObject c p'                              
removeRuntimeAnnotation (Closure p e) = 
  let e' = removeRuntimeAnnotation e
    in Closure p e'
removeRuntimeAnnotation (Cast t e) = removeRuntimeAnnotation e
removeRuntimeAnnotation e = e

