package Parser.DefinitionP;

import java.util.HashMap;

/**
 * Γ to represent an environment
 */
public class Environment extends HashMap<Variable, Type> {
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
