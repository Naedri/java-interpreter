package Parser.DefinitionP;

import java.util.HashMap;

/**
 * Γ to represent an environment
 * which is a finite mapping from Variable to Type
 */
public class Environment extends HashMap<Variable, T> {
    private static Environment instance;

    private Environment() {
    }

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }

        return instance;
    }
}
