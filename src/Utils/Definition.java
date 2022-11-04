package Utils;

import java.util.EnumMap;
import java.util.Map;

// objectif : définir les objets indiqués dans le Figure 1
public class Definition {

    public enum Type {
        CLASS,
        INTERFACE
    }

    public abstract class T extends Object {
        public Declaration d;
        T(){ super();};
    }

    public class C extends T {
        C(){ super();};
    }

    // TODO maybe class should be replaced by interface
    public class I extends T {
        I(){ super();};
    }

    public abstract class Declaration { }

    public class L extends Declaration { };
    public class P extends Declaration { };

    public class CT extends EnumMap<Type, Declaration> {
        CT(){};
    };
}