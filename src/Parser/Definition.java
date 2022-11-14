package Parser;

import java.util.EnumMap;
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
        CLASS,
        INTERFACE
    }

    /**
     * Type
     */
    public abstract class T extends Object {
        public D d;
        public String name;
        public Type type;
        public T(){
            super();
            this.d = new L();
            this.name = "str";
            this.type = Type.CLASS;
        };
        public T(D d, String name, Type type) {
            super();
            this.d = d;
            this.name = name;
            this.type = type; // signature du visiter
        };
    }

    /**
     * Classe
     */
    public class C extends T {
        C(){ super();};
    }

    /**
     * Interface
     */
    public class I extends T {
        I(){ super();};
    }
    /**
     * Declaration
     */
    public abstract class D {
        public CT ct;
    }

    /**
     * Class declaration
     */
    public class L extends D {
        public L() {
        }
    };

    /**
     * Interface declaration
     */
    public class P extends D {
        public P() {
        }

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