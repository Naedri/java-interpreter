package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Interface declaration
 * 𝑃 ::= interface 𝐼 {𝑆; default 𝑀}
 */
public class InterfaceDeclaration extends TDeclaration {
    public TreeSet<Signature> signatures;

    public InterfaceDeclaration(TreeSet<Method> methods, TreeSet<Signature> signatures) {
        super(methods);
        this.signatures = signatures;
    }

    public InterfaceDeclaration(TreeSet<Signature> signatures) {
        this.signatures = signatures;
    }

    public InterfaceDeclaration() {
        this.signatures = new TreeSet<Signature>(new Comparators.SignatureComparator());
    }
}
