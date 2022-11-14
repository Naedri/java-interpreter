package Parser;

import java.util.HashMap;
import java.util.Map;

/**
 * To define object from the paper
 */
public class Definition {

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
    public abstract class T extends Object {
        public EType EType; //TODO: evaluate if we want to keep it
        public String name;
        public T[] extensions; //TODO: evaluate if we want to move it into subclasses ?
        public T[] implementations; //TODO: evaluate if we want to move it into subclasses ?
        public T(EType EType, String name, T[] extensions, T[] implementations) {
            this.name = name;
            this.EType = EType;
            this.extensions = extensions;
            this.implementations = implementations;
        }
    }

    /**
     * Class type
     */
    public class C extends T {
        public Field[] fields; public K k; public M[] ms;
        /**
         * data Class = Class String String [String] [(Type,String)] Constr [Method]
         * Name of the class, list of superclasses, list of interfaces implemented, fields, constructor, list of methods
         */
        public C(String name, C extension, I[] implementations, Field[] fields, K k, M[] ms) {
            super( EType.CLASS, name, new C[]{extension}, implementations );
            this.fields = fields;
            this.k = k;
            this.ms = ms;
        }
        public C(String name, K k) {
            super( EType.CLASS, name, new C[]{new C("Object", k)}, new I[]{} );
            this.fields =  new Field[]{};
            this.k = k;
            this.ms = new M[]{};
        }
    }

    /**
     * Interface type
     */
    public class I extends T {
        /**
         * data Interface = Interface String [String] [Sign] [Method]
         * Name of the interface, list of superinterfaces, function signatures, Default method
         */
        public I(String name, I[] extensions) {
            super(EType.INTERFACE, name, extensions, new T[]{});
        }
    }

    /**
     * Class declaration
     * 𝐿 ::= class 𝐶 extends 𝐶 implements 𝐼 {𝑇 𝑓; 𝐾 𝑀}
     */
    public class L {
        public Field[] fields;
        public K k;
        public M[] ms;
        public L(Field[] fields, K k, M[] ms) {
            this.fields = fields;
            this.k = k;
            this.ms = ms;
        }
    };

    /**
     * Interface declaration
     * 𝑃 ::= interface 𝐼 extends 𝐼 {𝑆; default 𝑀}
     */
    public class P {
        public S s;
        public M[] ms; //default methods
        public P(S s, M[] ms) {
            this.s = s;
            this.ms = ms;
        }
    }

    /**
     * Constructor declaration
     * 𝐾 ::= 𝐶(𝑇 𝑓) {super(𝑓); this.𝑓 = 𝑓; }
     */
    public class K {
        public String name;
        public Field[] params;
        public String[] superParams;
        public InitiatedField[] initiatedFields;
        public K(String name, Field[] params, String[] superParams, InitiatedField[] initiatedFields) {
            this.name = name;
            this.params = params;
            this.superParams = superParams;
            this.initiatedFields = initiatedFields;
        }
    }

    /**
     * Signature declaration (return type, method name and parameters)
     * 𝑆 ::= 𝑇 m(𝑇 𝑥)
     */
    public class S {
        public T returnType;
        public String name;
        public Field[] params;
        public S(T returnType, String name, Field[] params) {
            this.returnType = returnType;
            this.name = name;
            this.params = params;
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