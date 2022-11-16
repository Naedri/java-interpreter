package Parser;

import java.util.HashMap;

/**
 * To define object from the paper
 */
public class Definition {
    // TODO: evaluate if we want to split the class into separated files
    public Definition() {
    }

    /**
     * Type as an enum
     * TODO: evaluate if we want to keep it
     */
    public enum EType {
        CLASS, // Definition.C,
        INTERFACE // Definition.I
    }

    /**
     * Type as a String
     * FJ + Lambda nominal typing
     */
    public class Type {
        public String type;

        public Type(String type) {
            this.type = type;
        }
    }

    /**
     * Type definition
     * 𝑇 ::= 𝐶 | 𝐼
     */
    public abstract class T {
        public EType EType; //TODO: evaluate if we want to keep it
        public String name;
        public T[] extensions;
        public T[] implementations;
        public D declaration;

        public T(EType EType, String name, T[] extensions, T[] implementations, D declaration) {
            this.EType = EType;
            this.name = name;
            this.extensions = extensions;
            this.implementations = implementations;
            this.declaration = declaration;
        }

        public T(EType EType, String name, T[] extensions, D declaration) {
            this.EType = EType;
            this.name = name;
            this.extensions = extensions;
            this.implementations = new I[]{};
            this.declaration = declaration;
        }
    }

    /**
     * Class type
     * 𝑇 ::= 𝐶
     */
    public class C extends T {
        public L declaration;

        /**
         * data Class = Class String String [String] [(Type,String)] Constr [Method]
         * Name of the class, superclasses, list of interfaces implemented, list of fields, constructor, list of methods
         */
        public C(String name, C extension, I[] implementations, L declaration) {
            super(EType.CLASS, name, new C[]{extension}, implementations, declaration);
        }

        // we still have to ask for the declaration in order to retrieve the constructor even if there is no field
        public C(String name, L declaration) {
            super(EType.CLASS, name, new C[]{new C("Object", new L(new K("ObjectConstructor")))}, declaration);
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
        public I(String name, I[] extensions, P declaration) {
            super(EType.INTERFACE, name, extensions, new T[]{}, declaration);
        }
    }

    /**
     * Declaration
     */
    public class D {
        //either default methods (interface) or concrete methods (class)
        public M[] ms;

        public D(M[] ms) {
            this.ms = ms;
        }

        public D() {
            this.ms = new M[]{};
        }
    }

    /**
     * Class declaration
     * 𝐿 ::= class 𝐶 {𝑇 𝑓; 𝐾 𝑀}
     */
    public class L extends D {
        public Field[] fields;
        public K k; // constructor

        public L(Field[] fields, K k, M[] ms) {
            super(ms);
            this.fields = fields;
            this.k = k;
        }

        public L(Field[] fields, K k) {
            this.fields = fields;
            this.k = k;
        }

        public L(K k) {
            this.fields = new Field[]{};
            this.k = k;
        }
    }

    ;

    /**
     * Interface declaration
     * 𝑃 ::= interface 𝐼 {𝑆; default 𝑀}
     */
    public class P extends D {
        public S[] s; //signatures

        public P(M[] ms, S[] s) {
            super(ms);
            this.s = s;
        }

        public P(S[] s) {
            this.s = s;
        }

        public P() {
            this.s = new S[]{};
        }
    }

    /**
     * Constructor declaration
     * 𝐾 ::= 𝐶(𝑇 𝑓) {super(𝑓); this.𝑓 = 𝑓; }
     */
    public class K {
        public String name; //class name
        public Field[] params;
        public String[] superParams;
        public InitiatedField[] initiatedFields;

        public K(String name, Field[] params, String[] superParams, InitiatedField[] initiatedFields) {
            this.name = name;
            this.params = params;
            this.superParams = superParams;
            this.initiatedFields = initiatedFields;
        }

        public K(String name) {
            this.name = name;
            this.params = new Field[]{};
            this.superParams = new String[]{};
            this.initiatedFields = new InitiatedField[]{};
        }
    }

    /**
     * Signature declaration (return type, method name and parameters)
     * 𝑆 ::= 𝑇 m(𝑇 𝑥)
     */
    public class S {
        public Type returnType;
        public String name;
        public Field[] params;

        public S(Type returnType, String name, Field[] params) {
            this.returnType = returnType;
            this.name = name;
            this.params = params;
        }

        public S(Type returnType, String name) {
            this.returnType = returnType;
            this.name = name;
            this.params = new Field[]{};
            ;
        }
    }

    /**
     * Method declaration
     * 𝑀 ::= 𝑆 { return 𝑒; }
     */
    public class M {
        public S signature;
        public Expression.Expr body;

        public M(S signature, Expression.Expr body) {
            this.signature = signature;
            this.body = body;
        }
    }

    /**
     * To initiate field by constructor from one of its parameter
     * this.𝑓 = 𝑓;
     */
    public class InitiatedField {
        public String fieldName;
        public String paramName;

        public InitiatedField(String fieldName, String paramName) {
            this.fieldName = fieldName;
            this.paramName = paramName;
        }
    }

    /**
     * Component of Class Table :
     * according the article : "couple declaration composed of class or interface name and their associated declaration"
     * according the code : `[(Type,String)]`
     */
    public class Field {
        Type type;
        String declaration;

        public Field(Type type, String declaration) {
            this.type = type;
            this.declaration = declaration;
        }
    }

    /**
     * Class Table
     * TODO how can we use Field class wrapper to type the hash map ?
     * TODO singleton ? if so need a factory
     */
    public class CT extends HashMap<Type, String> {
        public CT() {
        }
    }
}