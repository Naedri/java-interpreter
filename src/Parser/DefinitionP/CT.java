package Parser.DefinitionP;

import java.util.HashMap;

/**
 * Class Table
 * A mapping from class or interface names to class or interface declarations
 * Using a singleton as we have to have one dictionary for one app
 * Type -> Type.name
 * T -> T.declaration
 */
public class CT extends HashMap<Type, T> {

    public static final C baseObject = new C("Object", new ClassDeclaration(new Constructor("Object")));
    private static CT instance;
    private final HashMap<Type, T> hashMap;

    private CT() {
        hashMap = new HashMap<>();
        hashMap.put(new Type(baseObject.name), baseObject);
    }

    public static CT getInstance() {
        if (instance == null) {
            instance = new CT();
        }
        return instance;
    }

    public static C getBaseObject() {
        return baseObject;
    }
}
