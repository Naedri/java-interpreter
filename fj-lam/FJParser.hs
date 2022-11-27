-- Haskell parser for FJ + Lambda
-- Author: Samuel da Silva Feitosa
-- Date: 03/2018
---------------------------------
module FJParser where
import Data.Map

-- FJ + Lambda syntactic constructors
-------------------------------------
data T = TClass Class
       | TInterface Interface
       deriving (Show, Eq)

-- class C extends D implements I_ { T_ f_; K M_ }
-- [(Type,String)] => fields
-- Nom de la classe, nom de la superclasse, liste des interface implémentées par la classe, Fields, Constructeur, liste des méthodes
data Class = Class String String [String] [(Type,String)] Constr [Method]
              deriving (Show, Eq)

-- Interface I extends I_ { S_ default M_ }
-- Interface n'a pas de Type ni de Fields
-- Nom de l'interface, liste des interface extends , Signatures des fonctions (type de retour nom de méthode et paramètre), Default method
data Interface = Interface String [String] [Sign] [Method]
               deriving (Show, Eq)

-- C(T_ f_) { super(f_); this.f_.f_; }
-- Nom du constructeur, Field des arguments, nom des super arguments, clé valeur entre les champs à initialisé et les arguments
data Constr = Constr String [(Type,String)] [String] [(String,String)]
            deriving (Show, Eq)

-- T m(T_ x_)
-- return type, method name and parameters
data Sign = Sign Type String [(Type,String)]
          deriving (Show, Eq)

-- S { return e; }
-- Methode avec un 
data Method = Method Sign Expr
            deriving (Show, Eq)

data Expr = Var String                               -- Variable
          | FieldAccess Expr String                  -- Field Access
          | MethodInvk Expr String [Expr]            -- Method Invocation
          | CreateObject String [Expr]               -- Object Instantiation
          | Cast String Expr                         -- Cast
          | Closure [(Type,String)] Expr             -- Closure
          deriving (Show, Eq)

-- FJ + Lambda nominal typing
-----------------------------
data Type = Type String
          deriving (Show, Eq)

-- FJ + Lambda auxiliary definitions
------------------------------------
type Env = Map String Type
type CT = Map String T

-- FJ + Lambda typing errors
----------------------------
data TypeError = VariableNotFound String
               | FieldNotFound String
               | ClassNotFound String
               | MethodNotFound String String
               | ParamsTypeMismatch [(Expr,Type)]
               | WrongClosure String Expr
               | WrongCast String Expr
               | UnknownError Expr
               deriving (Show,Eq)
