package Parser.DefinitionP;

import java.util.TreeSet;

/**
 * Class type
 * 𝑇 ::= 𝐶
 */
public class C extends T {
    /**
     * data Class = Class String String [String] [(Type,String)] Constr [Method]
     * Name of the class, name of the superclass, list of interfaces implemented, list of fields, constructor, list of methods
     */
    public C(String name, C extension, TreeSet<I> implementations, ClassDeclaration declaration) {
        super(EType.CLASS, name, extension, I.fromIToT(implementations), declaration);
    }

    // we still have to ask for the declaration in order to retrieve the constructor even if there is no field
    public C(String name, ClassDeclaration declaration) {
        super(EType.CLASS, name, new C("Object", new ClassDeclaration(new Constructor("Object"))), declaration);
    }
}
