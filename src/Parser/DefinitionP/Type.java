package Parser.DefinitionP;

import java.util.Comparator;

/**
 * Type as a String
 * FJ + Lambda nominal typing
 */
public class Type implements Comparator<Type> {
    public String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int compare(Type o1, Type o2) {
        return o1.typeName.compareTo(o2.typeName);
    }
}
