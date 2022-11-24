package Parser.DefinitionP;

import java.util.HashMap;

/**
 * Class Table
 * A mapping from class or interface names to class or interface declarations
 * Using a singleton as we have to have one dictionary for one app
 * Type -> Type.name (String)
 * T -> T.declaration (Declaration
 */
public class CT {

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

    public HashMap<Type, T> getHashMap() {
        return hashMap;
    }

}
