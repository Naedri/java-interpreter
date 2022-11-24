package Parser.DefinitionP;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Type definition
 * 𝑇 ::= 𝐶 | 𝐼
 */
public abstract class T implements Comparator<T> {
    public final EType eType;
    public String name;
    public TreeSet<T> extensions; // no duplicates allowed
    public TreeSet<T> implementations; // no duplicates allowed
    public TDeclaration tDeclaration;

    //classes
    public T(EType eType, String name, TreeSet<T> extensions, TreeSet<T> implementations, TDeclaration tDeclaration) {
        this.eType = eType;
        this.name = name;
        this.extensions = extensions;
        this.implementations = implementations;
        this.tDeclaration = tDeclaration;
    }

    //interfaces and classes without implementations with several extensions
    // if class, extensions size 1 element => use next constructor
    public T(EType eType, String name, TreeSet<T> extensions, TDeclaration tDeclaration) {
        this.eType = eType;
        this.name = name;
        this.extensions = extensions;
        this.implementations = new TreeSet<>(new Comparators.TComparator());
        this.tDeclaration = tDeclaration;
    }

    public T(EType eType, String name, T extension, TreeSet<T> implementations, TDeclaration tDeclaration) {
        this.eType = eType;
        this.name = name;
        this.extensions = new TreeSet<>(new Comparators.TComparator());
        this.extensions.add(extension);
        this.implementations = implementations;
        this.tDeclaration = tDeclaration;
    }

    //interface or a class without implementations with a single extension
    public T(EType eType, String name, T extension, TDeclaration tDeclaration) {
        this.eType = eType;
        this.name = name;
        this.extensions = new TreeSet<>(new Comparators.TComparator());
        this.extensions.add(extension);
        this.implementations = new TreeSet<>(new Comparators.TComparator());
        this.tDeclaration = tDeclaration;
    }

    @Override
    public int compare(T o1, T o2) {
        int res;
        if (o1 == null && o2 == null) res = 0;
        else if (o1 == null) res = -1;
        else if (o2 == null) res = 1;
        else res = o1.name.compareTo(o2.name);
        return res;
    }
}
