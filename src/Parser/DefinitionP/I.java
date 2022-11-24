package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Interface type
 * 𝑇 ::= 𝐼
 * 𝑃 ::= interface 𝐼 extends 𝐼 {𝑆; default 𝑀}
 */
public class I extends T {
    /**
     * data Interface = Interface String [String] [Sign] [Method]
     * Name of the interface, list of superinterfaces, function signatures, Default method
     */
    public I(String name, TreeSet<I> extensions, InterfaceDeclaration declaration) {
        super(EType.INTERFACE, name, I.fromIToT(extensions), new TreeSet<>(new Comparators.TComparator()), declaration);
    }

    public static TreeSet<T> fromIToT(TreeSet<I> set) {
        TreeSet<T> newSet = new TreeSet<>(new Comparators.TComparator());
        // for (I i : set) {newSet.add(i);}
        newSet.addAll(set);
        return newSet;
    }
}
