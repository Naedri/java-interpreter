package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Declaration
 */
public abstract class TDeclaration {
    //either default methods (interface) or concrete methods (class)
    public TreeSet<Method> methods;

    public TDeclaration(TreeSet<Method> methods) {
        this.methods = methods;
    }

    public TDeclaration() {
        this.methods = new TreeSet<>(new Comparators.MethodComparator());
    }
}
