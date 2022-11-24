package Parser.DefinitionP;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Signature declaration (return type, method name and parameters)
 * 𝑆 ::= 𝑇 m(𝑇 𝑥)
 */
public class Signature implements Comparator<Signature> {
    public Type returnType;
    public String name;
    public TreeSet<Field> params;

    public Signature(Type returnType, String name, TreeSet<Field> params) {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
    }

    public Signature(Type returnType, String name) {
        this.returnType = returnType;
        this.name = name;
        this.params = new TreeSet<Field>(new Comparators.FieldComparator());
    }

    @Override
    public int compare(Signature o1, Signature o2) {
        //TODO improve by going through all parameters
        return o1.name.compareTo(o2.name);
    }
}
