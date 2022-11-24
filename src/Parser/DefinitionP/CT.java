package Parser.DefinitionP;

import java.util.HashMap;

/**
 * Class Table
 * Usin a singleton as we have to have one dictionary for one app
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
