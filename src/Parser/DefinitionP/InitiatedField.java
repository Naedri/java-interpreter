package Parser.DefinitionP;

import java.util.Comparator;

/**
 * To initiate field by constructor from one of its parameter
 * this.𝑓 = 𝑓;
 * according the code : `[(String,String)]`
 */
public class InitiatedField implements Comparator<InitiatedField> {
    public String fieldName; //attribute we initiate value
    public String paramName; //name of the parameter given to the constructor as an argument

    public InitiatedField(String fieldName, String paramName) {
        this.fieldName = fieldName;
        this.paramName = paramName;
    }

    @Override
    public int compare(InitiatedField o1, InitiatedField o2) {
        return o1.fieldName.compareTo(o2.fieldName) == 0 ?
                o1.paramName.compareTo(o2.paramName) : o1.fieldName.compareTo(o2.fieldName);
    }
}
