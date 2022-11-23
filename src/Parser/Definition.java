package Parser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * To define object from the paper
 */
// TODO: evaluate if we want to split the class into separated files
public class Definition {

    private static Definition instance;

    private Definition() {
    }

    public static Definition getInstance() {
        if (instance == null) {
            instance = new Definition();
        }

        return instance;
    }


    /*********************Lambda syntactic constructors***************************************/
    /**
     * Type as an enum
     */
    public enum EType {
        CLASS("Class"), // Definition.C,
        INTERFACE("Interface"); // Definition.I
        private final String type;

        // private enum constructor
        private EType(String type) {
            this.type = type;
        }
    }

    /**
     * Γ to represent an environment
     */
    //TODO we should have a Singleton for this
    public static class Environment extends HashMap<Variable, Type> {
        public Environment() {
        }
    }

    /**
     * Class Table
     * TODO how can we use Field class wrapper to type the hash map ?
     * TODO singleton ? if so need a factory
     */
    //TODO we should have a Singleton for this (only one dictionnary for one app)
    public static class CT extends HashMap<Type, T> {
        public CT() {
        }
    }

    /**
     * Type definition
     * 𝑇 ::= 𝐶 | 𝐼
     */
    public abstract class T implements Comparator<T> {
        public final EType eType;
        public String name;
        public TreeSet<T> extensions; // no duplicates allowed
        public TreeSet<T> implementations; // no duplicates allowed
        public TDeclaration tDeclaration;

        //classes
        public T(EType eType, String name, TreeSet<T> extensions, TreeSet<T> implementations, TDeclaration tDeclaration) {
            this.eType = eType;
            this.name = name;
            this.extensions = extensions;
            this.implementations = implementations;
            this.tDeclaration = tDeclaration;
        }

        //interfaces et classes sans implémentations avec plusieurs extensions
        //si classe, extensions taille 1 élément => utiliser constructeur suivant
        public T(EType eType, String name, TreeSet<T> extensions, TDeclaration tDeclaration) {
            this.eType = eType;
            this.name = name;
            this.extensions = extensions;
            this.implementations = new TreeSet<T>();
            this.tDeclaration = tDeclaration;
        }

        //interface ou une classe sans implémentations avec une seule extension
        public T(EType eType, String name, T extension, TDeclaration tDeclaration) {
            this.eType = eType;
            this.name = name;
            //this.extensions = new TreeSet<T>((Comparator<? super T>) extension);
            this.extensions = new TreeSet<T>();
            this.extensions.add(extension);
            this.implementations = new TreeSet<T>();
            this.tDeclaration = tDeclaration;
        }

        public T(EType eType, String name, T extension, TreeSet<T> implementations, ClassDeclaration tDeclaration) {
            this.eType = eType;
            this.name = name;
            //this.extensions = new TreeSet<T>((Comparator<? super T>) extension);
            this.extensions = new TreeSet<T>();
            this.extensions.add(extension);
            this.implementations = implementations;
            this.tDeclaration = tDeclaration;
        }

        @Override
        public int compare(T o1, T o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    /**
     * Class type
     * 𝑇 ::= 𝐶
     */
    public class C extends T implements Comparator<T> {
        /**
         * data Class = Class String String [String] [(Type,String)] Constr [Method]
         * Name of the class, name of the superclass, list of interfaces implemented, list of fields, constructor, list of methods
         * TODO params ?
         */
        public C(String name, C extension, TreeSet<I> implementations, ClassDeclaration declaration) {
            super(EType.CLASS, name, (T) extension, I.fromIToT(implementations), declaration);
        }

        // we still have to ask for the declaration in order to retrieve the constructor even if there is no field
        public C(String name, ClassDeclaration declaration) {
            super(EType.CLASS, name, new C("Object", new ClassDeclaration(new Constructor("ObjectConstructor"))), declaration);
        }

        public static TreeSet<T> fromCToT(TreeSet<C> set) {
            TreeSet<T> newSet = new TreeSet<T>();
            Iterator<T> iterator = newSet.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                System.out.print("iterator test " + element.toString());
                newSet.add(element);
            }
            return newSet;
        }

    }

    /**
     * Interface type
     * 𝑇 ::= 𝐼
     * 𝑃 ::= interface 𝐼 extends 𝐼 {𝑆; default 𝑀}
     */
    public class I extends T {
        /**
         * data Interface = Interface String [String] [Sign] [Method]
         * Name of the interface, list of superinterfaces, function signatures, Default method
         */
        public I(String name, TreeSet<I> extensions, InterfaceDeclaration declaration) {
            super(EType.INTERFACE, name, I.fromIToT(extensions), new TreeSet<>(), declaration);
        }

        public static TreeSet<T> fromIToT(TreeSet<I> set) {
            TreeSet<T> newSet = new TreeSet<T>();
            Iterator<T> iterator = newSet.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                System.out.print("iterator test " + element.toString());
                newSet.add(element);
            }
            return newSet;
        }
    }

    /**
     * Declaration
     */
    public abstract class TDeclaration {
        //either default methods (interface) or concrete methods (class)
        public TreeSet<Method> methods;

        public TDeclaration(TreeSet<Method> methods) {
            this.methods = methods;
        }

        public TDeclaration() {
            this.methods = new TreeSet<Method>() {
            };
        }
    }

    /**
     * Class declaration
     * 𝐿 ::= class 𝐶 {𝑇 𝑓; 𝐾 𝑀}
     */
    public class ClassDeclaration extends TDeclaration {
        public TreeSet<Field> fields;
        public Constructor constructor; // constructor

        public ClassDeclaration(TreeSet<Field> fields, Constructor constructor, TreeSet<Method> methods) {
            super(methods);
            this.fields = fields;
            this.constructor = constructor;
        }

        public ClassDeclaration(TreeSet<Field> fields, Constructor constructor) {
            this.fields = fields;
            this.constructor = constructor;
        }

        public ClassDeclaration(Constructor constructor) {
            this.fields = new TreeSet<>();
            this.constructor = constructor;
        }
    }

    /**
     * Interface declaration
     * 𝑃 ::= interface 𝐼 {𝑆; default 𝑀}
     */
    public class InterfaceDeclaration extends TDeclaration {
        public TreeSet<Signature> signatures;

        public InterfaceDeclaration(TreeSet<Method> methods, TreeSet<Signature> signatures) {
            super(methods);
            this.signatures = signatures;
        }

        public InterfaceDeclaration(TreeSet<Signature> signatures) {
            this.signatures = signatures;
        }

        public InterfaceDeclaration() {
            this.signatures = new TreeSet<Signature>();
        }
    }

    /**
     * Constructor declaration
     * 𝐾 ::= 𝐶(𝑇 𝑓) {super(𝑓); this.𝑓 = 𝑓; }
     */
    public class Constructor {
        public String name; //class name // should be the same than the class
        public TreeSet<Field> args; // in haskell =>  [(Type,String)] //no duplicatas allowed, so Set
        public TreeSet<String> superArgs; // in haskell => [String]
        public TreeSet<InitiatedField> initiatedFields; // in haskell => [String, String]

        /**
         * @param name            of the constructor of the class
         * @param args            of the constructor (name, superParams, initiatedFields)
         * @param superArgs       : args that are redirected by the super() (if the super is present)
         * @param initiatedFields : couple of String to indicate which Field will be initiated by which params from the constructor
         */
        public Constructor(String name, TreeSet<Field> args, TreeSet<String> superArgs, TreeSet<InitiatedField> initiatedFields) {
            this.name = name;
            this.args = args;
            this.superArgs = superArgs;
            this.initiatedFields = initiatedFields;
        }

        public Constructor(String name, TreeSet<Field> args, TreeSet<InitiatedField> initiatedFields) {
            this.name = name;
            this.args = args;
            this.superArgs = new TreeSet<String>();
            this.initiatedFields = initiatedFields;
        }

        public Constructor(String name) {
            this.name = name;
            this.args = new TreeSet<Field>();
            this.superArgs = new TreeSet<String>();
            this.initiatedFields = new TreeSet<InitiatedField>();
        }
    }

    /**
     * Signature declaration (return type, method name and parameters)
     * 𝑆 ::= 𝑇 m(𝑇 𝑥)
     */
    public class Signature {
        public Type returnType;
        public String name;
        public TreeSet<Field> params;

        public Signature(Type returnType, String name, TreeSet<Field> params) {
            this.returnType = returnType;
            this.name = name;
            this.params = params;
        }

        public Signature(Type returnType, String name) {
            this.returnType = returnType;
            this.name = name;
            this.params = new TreeSet<Field>();
        }
    }

    /**
     * Method declaration
     * 𝑀 ::= 𝑆 { return 𝑒; }
     */
    public class Method {
        public Signature signature;
        public Expression.Expr body;

        public Method(Signature signature, Expression.Expr body) {
            this.signature = signature;
            this.body = body;
        }
    }

    /**
     * To initiate field by constructor from one of its parameter
     * this.𝑓 = 𝑓;
     * according the code : `[(String,String)]`
     */
    public class InitiatedField {
        public String fieldName; //attribute we initiate value
        public String paramName; //name of the parameter given to the constructeur as an argument

        public InitiatedField(String fieldName, String paramName) {
            this.fieldName = fieldName;
            this.paramName = paramName;
        }
    }


    /*********************Lambda nominal typing***************************************/

    public class InitiatedFieldComparator implements Comparator<InitiatedField> {
        @Override
        public int compare(InitiatedField o1, InitiatedField o2) {
            return o1.fieldName.compareTo(o2.fieldName) == 0 ?
                    o1.paramName.compareTo(o2.paramName) : o1.fieldName.compareTo(o2.fieldName);
        }
    }

    /**
     * Component of Class Table :
     * according the article : "couple declaration composed of class or interface name and their associated declaration"
     * according the code : `[(Type,String)]`
     */
    public class Field {
        public Type type;
        public String nameField;

        public Field(Type type, String nameField) {
            this.type = type;
            this.nameField = nameField;
        }
    }

    /*********************String name wrappers***************************************/

    public class FieldComparator implements Comparator<Field> {
        @Override
        public int compare(Field o1, Field o2) {
            return o1.nameField.compareTo(o2.nameField);
        }
    }


    /*********************Lambda Auxilliary definitions***************************************/

    /**
     * Type as a String
     * FJ + Lambda nominal typing
     */
    public class Type {
        public String name;

        public Type(String typeName) {
            this.name = typeName;
        }

        public boolean equals(Type typeToCompare) {
            return name.contentEquals(typeToCompare.name);
        }
    }

    /**
     * Wrapper for variable names
     */
    public class Variable {
        public String name;

        public Variable(String variableName) {
            this.name = variableName;
        }
    }
}




