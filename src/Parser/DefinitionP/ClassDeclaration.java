package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Class declaration
 * 𝐿 ::= class 𝐶 {𝑇 𝑓; 𝐾 𝑀}
 */
public class ClassDeclaration extends TDeclaration {
    public TreeSet<Field> fields;
    public Constructor constructor; // constructor

    public ClassDeclaration(TreeSet<Field> fields, Constructor constructor, TreeSet<Method> methods) {
        super(methods);
        this.fields = fields;
        this.constructor = constructor;
    }

    public ClassDeclaration(TreeSet<Field> fields, Constructor constructor) {
        this.fields = fields;
        this.constructor = constructor;
    }

    public ClassDeclaration(Constructor constructor) {
        this.fields = new TreeSet<>(new Comparators.FieldComparator());
        this.constructor = constructor;
    }
}
