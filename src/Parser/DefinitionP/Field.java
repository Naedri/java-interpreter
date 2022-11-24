package Parser.DefinitionP;

import java.util.Comparator;

/**
 * Component of Class Table :
 * according the article : "couple declaration composed of class or interface name and their associated declaration"
 * according the code : `[(Type,String)]`
 */
public class Field implements Comparator<Field> {
    public Type type;
    public String nameField;

    public Field(Type type, String nameField) {
        this.type = type;
        this.nameField = nameField;
    }

    @Override
    public int compare(Field o1, Field o2) {
        return o1.nameField.compareTo(o2.nameField) == 0 ?
                o1.type.compare(o1.type, o2.type) : o1.nameField.compareTo(o2.nameField);
    }
}
