classDiagram
direction BT
class C
class CT
class Cast
class ClassNotFound
class Closure
class CreateObject
class D
class Definition {
  - Definition instance
   Definition instance
}
class EType {
<<enumeration>>
  + values() EType[]
  + valueOf(String) EType
}
class Environment
class Expr
class Expression
class FJInterpreter {
  + evalPrime(CT, Expr) Expr
  + subst(String[], Expr[], Expr) Optional~Expr~
}
class FJUtils {
  + subtyping(CT, String, String) Boolean
  + fields(CT, String) ArrayList~Field~
  + isValue(CT, Expr) Boolean
}
class Field
class FieldAccess
class FieldNotFound
class I
class IInterpreter {
<<Interface>>
  + evalPrime(CT, Expr) Expr
  + subst(String[], Expr[], Expr) Optional~Expr~
}
class IUtils {
<<Interface>>

}
class InitiatedField
class K
class L
class M
class MethodInvk
class MethodNotFound
class MismatchType
class P
class ParamsTypeMismatch
class S
class T
class Type {
  + equals(Type) boolean
}
class TypeError
class TypingError
class UnknownError
class Var
class Variable
class VariableNotFound
class WrongCast
class WrongClosure
class mainTest {
  + main(String[]) void
}

Definition  -->  C 
C  -->  T 
Definition  -->  CT 
Cast  -->  Expr 
Expression  -->  Cast 
ClassNotFound  -->  TypeError 
TypingError  -->  ClassNotFound 
Closure  -->  Expr 
Expression  -->  Closure 
CreateObject  -->  Expr 
Expression  -->  CreateObject 
Definition  -->  D 
Definition  -->  EType 
Definition  -->  Environment 
Expression  -->  Expr 
FJInterpreter  ..>  IInterpreter 
FJUtils  ..>  IUtils 
Definition  -->  Field 
FieldAccess  -->  Expr 
Expression  -->  FieldAccess 
FieldNotFound  -->  TypeError 
TypingError  -->  FieldNotFound 
Definition  -->  I 
I  -->  T 
Definition  -->  InitiatedField 
Definition  -->  K 
L  -->  D 
Definition  -->  L 
Definition  -->  M 
MethodInvk  -->  Expr 
Expression  -->  MethodInvk 
MethodNotFound  -->  TypeError 
TypingError  -->  MethodNotFound 
TypingError  -->  MismatchType 
P  -->  D 
Definition  -->  P 
ParamsTypeMismatch  -->  TypeError 
TypingError  -->  ParamsTypeMismatch 
Definition  -->  S 
Definition  -->  T 
Definition  -->  Type 
TypingError  -->  TypeError 
UnknownError  -->  TypeError 
TypingError  -->  UnknownError 
Var  -->  Expr 
Expression  -->  Var 
Definition  -->  Variable 
VariableNotFound  -->  TypeError 
TypingError  -->  VariableNotFound 
WrongCast  -->  TypeError 
TypingError  -->  WrongCast 
WrongClosure  -->  TypeError 
TypingError  -->  WrongClosure 
