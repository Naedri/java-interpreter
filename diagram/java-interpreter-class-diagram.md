classDiagram
direction BT
class C {
  + ClassDeclaration declaration
}
class CT
class Cast {
  + String f
  + Expr expr
}
class ClassDeclaration {
  + List~Field~ fields
  + Constructor constructor
}
class ClassNotFound {
  + String name
}
class Closure {
  + List~Field~ params
  + Expr body
}
class Constructor {
  + List~Field~ params
  + List~String~ superParams
  + String name
  + List~InitiatedField~ initiatedFields
}
class CreateObject {
  + List~Expr~ params
  + String name
}
class Definition {
  - Definition instance
  + getInstance() Definition
}
class EType {
<<enumeration>>
  +  CLASS
  +  INTERFACE
  + values() EType[]
  + valueOf(String) EType
}
class Environment
class Expr
class Expression
class FJInterpreter {
  + subst(String[], Expr[], Expr) Optional~Expr~
  + evalPrime(CT, Expr) Expr
}
class FJUtils {
  + subtyping(CT, String, String) Boolean
  + fields(CT, String) ArrayList~Field~
  + isValue(CT, Expr) Boolean
}
class Field {
  + Type type
  + String nameField
}
class FieldAccess {
  + String name
  + Expr ownerCLass
}
class FieldNotFound {
  + String name
}
class I
class IInterpreter {
<<Interface>>
  + evalPrime(CT, Expr) Expr
  + subst(String[], Expr[], Expr) Optional~Expr~
}
class IUtils {
<<Interface>>

}
class InitiatedField {
  + String fieldName
  + String paramName
}
class InterfaceDeclaration {
  + Signature[] signature
}
class Method {
  + Signature signature
  + Expr body
}
class MethodInvk {
  + String name
  + Expr parent
  + List~Expr~ params
}
class MethodNotFound {
  + String name
  + String type
}
class MismatchType {
  + Expr expr
  + Type type
}
class ParamsTypeMismatch {
  + MismatchType[] params
}
class Signature {
  + String name
  + List~Field~ params
  + Type returnType
}
class T {
  + String name
  + T[] extensions
  + TDeclaration tDeclaration
  + EType EType
  + T[] implementations
}
class TDeclaration {
  + Method[] methods
}
class Type {
  + String name
  + equals(Type) boolean
}
class TypeError
class TypingError
class UnknownError {
  + Expr expr
}
class Var {
  + String name
}
class Variable {
  + String name
}
class VariableNotFound {
  + String name
}
class WrongCast {
  + String type
  + Expr expr
}
class WrongClosure {
  + String type
  + Expr lambdaClosure
}
class mainTest {
  + main(String[]) void
}

Definition  -->  C 
C  -->  T 
Definition  -->  CT 
Cast  -->  Expr 
Expression  -->  Cast 
Definition  -->  ClassDeclaration 
ClassDeclaration  -->  TDeclaration 
ClassNotFound  -->  TypeError 
TypingError  -->  ClassNotFound 
Closure  -->  Expr 
Expression  -->  Closure 
Definition  -->  Constructor 
CreateObject  -->  Expr 
Expression  -->  CreateObject 
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
Definition  -->  InterfaceDeclaration 
InterfaceDeclaration  -->  TDeclaration 
Definition  -->  Method 
MethodInvk  -->  Expr 
Expression  -->  MethodInvk 
MethodNotFound  -->  TypeError 
TypingError  -->  MethodNotFound 
TypingError  -->  MismatchType 
ParamsTypeMismatch  -->  TypeError 
TypingError  -->  ParamsTypeMismatch 
Definition  -->  Signature 
Definition  -->  T 
Definition  -->  TDeclaration 
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
