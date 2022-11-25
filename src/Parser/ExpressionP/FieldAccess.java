package Parser.ExpressionP;

/**
 * field access = allow to access the attribute of a class (the ownerClass)
 * <p>
 * 𝑒 ::= 𝑒.𝑓
 */
public class FieldAccess extends Expr {
    public Expr ownerCLass;
    public String name;

    public FieldAccess(Expr ownerCLass, String name) {
        super(2);
        this.ownerCLass = ownerCLass;
        this.name = name;
    }
}
