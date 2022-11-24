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
    private static CT instance;

    private CT() {
    }

    public static CT getInstance() {
        if (instance == null) {
            instance = new CT();
        }

        return instance;
    }
}
