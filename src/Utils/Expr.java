package Utils;

import java.util.Set;
import java.util.SortedSet;

public abstract class Expr extends Object {

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
    }

    /*
    data Expr = Var String                           -- Variable
          | FieldAccess Expr String                  -- Field Access
          | MethodInvk Expr String [Expr]            -- Method Invocation
          | CreateObject String [Expr]               -- Object Instantiation
          | Cast String Expr                         -- Cast
          | Closure [(Type,String)] Expr             -- Closure
          deriving (Show, Eq)
     */


    //TODO passer tous les paramètres en private
    //TODO eval doit tester R-Field et RC-Field

    public class Var extends Expr {

    }

    public class FieldAccess extends Expr {
        public Expr expression;
        public String f;

        public FieldAccess(Expr expr, String f) {

        }
    };

    public class MethodInvk extends Expr {
        public Expr expr;
        public String m;
        public Set<Expr> p;

        public MethodInvk(Expr expr, String m, Set<Expr> p) {
        }
    };

    public class CreateObject extends Expr {
        public CreateObject() {
        }
    };
    public class Cast extends Expr {
        public Cast(Expr expr, String f) {
        }
    };

    public class Closure extends Expr {
        public Closure() {
        }
    };
}
