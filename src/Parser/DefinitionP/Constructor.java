package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Constructor declaration
 * 𝐾 ::= 𝐶(𝑇 𝑓) {super(𝑓); this.𝑓 = 𝑓; }
 */
public class Constructor {
    public String name; //class name // should be the same as the class
    public TreeSet<Field> args; // in haskell =>  [(Type,String)] //no duplicates allowed, so Set
    public TreeSet<String> superArgs; // in haskell => [String]
    public TreeSet<InitiatedField> initiatedFields; // in haskell => [String, String]

    /**
     * @param name            of the constructor of the class
     * @param args            of the constructor (name, superParams, initiatedFields)
     * @param superArgs       : args that are redirected by the super() (if the super is present)
     * @param initiatedFields : a couple of String to indicate which Field will be initiated by which params from the constructor
     */
    public Constructor(String name, TreeSet<Field> args, TreeSet<String> superArgs, TreeSet<InitiatedField> initiatedFields) {
        this.name = name;
        this.args = args;
        this.superArgs = superArgs;
        this.initiatedFields = initiatedFields;
    }

    public Constructor(String name, TreeSet<Field> args, TreeSet<InitiatedField> initiatedFields) {
        this.name = name;
        this.args = args;
        this.superArgs = new TreeSet<String>();
        this.initiatedFields = initiatedFields;
    }

    public Constructor(String name) {
        this.name = name;
        this.args = new TreeSet<Field>(new Comparators.FieldComparator());
        this.superArgs = new TreeSet<String>();
        this.initiatedFields = new TreeSet<InitiatedField>(new Comparators.InitiatedFieldComparator());
    }
}
