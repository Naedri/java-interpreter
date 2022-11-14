package Parser;

import java.util.HashMap;

/**
 * To define object from the paper
 */
public class Definition {

    /**
     * Type as an enum
     * TODO: evaluate if we want to keep it
     */
    public enum Type {
        CLASS, // Definition.C,
        INTERFACE // Definition.I
    }

    /**
     * Type definition
     * 𝑇 ::= 𝐶 | 𝐼
     */
    public abstract class T extends Object {
        public String name;
        public D d;
        public Type type;
        public T[] extension; //TODO move into subclasses ?
        public T[] implementation;
        public T(String name, D d, Type type, T[] extension, T[] implementation) {
            this.name = name;
            this.d = d;
            this.type = type;
            this.extension = extension;
            this.implementation = implementation;
        }
    }

    /**
     * Class type
     */
    public class C extends T {
        /**
         * data Class = Class String String [String] [(Type,String)] Constr [Method]
         * Name of the class, list of superclasses, list of interfaces implemented, fields, constructor, list of methods
         */
        public C(String name, D d,  C[] extension, I[] implementation) {
            super(name, d, Type.CLASS, extension, implementation );
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
        public I(String name, D d, I[] extension) {
            super(name, d, Type.INTERFACE, extension, null);
        }
    }
    /**
     * Declaration
     */
    public abstract class D {
        public CT ct;
    }

    /**
     * Class declaration
     * 𝐿 ::= class 𝐶 extends 𝐶 implements 𝐼 {𝑇 𝑓; 𝐾 𝑀}
     */
    public class L extends D {
        public L() {
        }
    };

    /**
     * Interface declaration
     * 𝑃 ::= interface 𝐼 extends 𝐼 {𝑆; default 𝑀}
     */
    public class P extends D {
        public P() {
        }

    }

    /**
     * Constructor declaration
     * 𝐾 ::= 𝐶(𝑇 𝑓) {super(𝑓); this.𝑓 = 𝑓; }
     */
    public class K {

    }

    /**
     * Signature declaration (return type, method name and parameters)
     * 𝑆 ::= 𝑇 m(𝑇 𝑥)
     */
    public class S {
    }

    /**
     * Method
     * 𝑀 ::= 𝑆 { return 𝑒; }
     */
    public class M {
    }

    /**
     * Component of Class Table :
     * couple declaration composed of
     * class or interface name and their associated declaration
     * TODO : maybe to delete
     */
    public class Field {
        String name;
        D d;
        Type type;
    }

    /**
     * Class Table
     * should be unique thus its type is String D, and not an enum
     * TODO singleton
     */
    public class CT extends HashMap<String, D> { }
}