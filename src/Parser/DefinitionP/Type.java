package Parser.DefinitionP;

import java.util.Comparator;

/**
 * Type as a String
 * FJ + Lambda nominal typing
 */
public class Type implements Comparator<Type> {
    public String name;

    public Type(String typeName) {
        this.name = typeName;
    }

    @Override
    public int compare(Type o1, Type o2) {
        return o1.name.compareTo(o2.name);
    }
}
