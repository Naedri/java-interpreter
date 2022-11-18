package Utils;

import Parser.Definition;
import Parser.Expression;

import java.util.List;
import java.util.Optional;

public interface IUtils {
    public Boolean subtyping(Definition.CT dictionnary, String a, String b);
    public List<Definition.Field> fields(Definition.CT dictionnary, String nameT1);
    /*public  class absmethods(Definition.CT table, Class a, Class b);
    public  class methods(Definition.CT table, Class a, Class b);
    public  class mtype(Definition.CT table, String method, String);
    public  class mbody(Definition.CT table, Class a, Class b);
    public  Boolean isValue(Definition.CT table, Expression.Expr exp);
    public  Expression.Expr lambdaMark(Expression.Expr exp, Definition.EType a);
    public  Expression.Expr removeRuntimeAnnotation(Expression.Expr exp);*/
}
