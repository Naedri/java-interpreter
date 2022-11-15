package Parser;

/**
 * data Expr = Var String                     -- Variable
 * | FieldAccess Expr String                  -- Field Access
 * | MethodInvk Expr String [Expr]            -- Method Invocation
 * | CreateObject String [Expr]               -- Object Instantiation
 * | Cast String Expr                         -- Cast
 * | Closure [(Type,String)] Expr             -- Closure
 */
public class Expression {
    /**
     * expression
     */
    public abstract class Expr {
    }

    /**
     * variable
     * 𝑒 ::= 𝑥
     */
    public class Var extends Expr {
        public String name;

        public Var(String name) {
            this.name = name;
        }
    }

    /**
     * field access
     * 𝑒 ::= 𝑒.𝑓
     */
    public class FieldAccess extends Expr {
        public Expr parent;
        public String name;

        public FieldAccess(Expr parent, String name) {
            this.parent = parent;
            this.name = name;
        }
    }

    /**
     * method invocation
     * 𝑒 ::= 𝑒.m(𝑒)
     */
    public class MethodInvk extends Expr {
        public Expr parent;
        public String name;
        public Expr[] params;

        public MethodInvk(Expr parent, String name, Expr[] params) {
            this.parent = parent;
            this.name = name;
            this.params = params;
        }
    }

    /**
     * object creation/instantiation
     * 𝑒 ::= new 𝐶(𝑒)
     */
    public class CreateObject extends Expr {
        public String name; // of the class
        public Expr[] params;

        public CreateObject(String name, Expr[] params) {
            this.name = name;
            this.params = params;
        }
    }

    /**
     * cast
     * 𝑒 ::= (𝑇) 𝑒
     */
    public class Cast extends Expr {
        public String f; // of the type
        public Expr expr;

        public Cast(String f, Expr expr) {
            this.f = f;
            this.expr = expr;
        }
    }

    /**
     * closure / 𝜆-expression
     * 𝑒 ::= (𝑇 𝑥) → 𝑒
     */
    public class Closure extends Expr {
        public Definition.Field[] params;
        public Expr body;

        public Closure(Definition.Field[] params, Expr body) {
            this.params = params;
            this.body = body;
        }
    }

    //TODO passer tous les paramètres en private
    //TODO eval doit tester R-Field et RC-Field
    /*
    public Expr(SortedSet<Object> params) {
        Expr res;

        // pattern matching
        if(params.size() == 2) {

        }
        else if (params.size() == 3)
        {
            res = new MethodInvk(params.first(), params[1], params.last());
        } else {
            throw new IllegalArgumentException("Too much params");
            res = null;
        }
        //if(o1 expr et o2 string) --> FieldAccess
    }*/
}
