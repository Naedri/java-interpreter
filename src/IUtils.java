import Utils.Definition;
import Utils.Expression;

import java.util.Optional;

public interface IUtils {
    public static Boolean subtyping(Definition.CT table, Class a, Class b);
    public static Optional<> fields(Definition.CT table, Class a, Class b);
    public static class absmethods(Definition.CT table, Class a, Class b);
    public static class methods(Definition.CT table, Class a, Class b);
    public static class mtype(Definition.CT table, String method, String);
    public static class mbody(Definition.CT table, Class a, Class b);
    public static Boolean isValue(Definition.CT table, Expression.Expr exp);
    public static Expression.Expr lambdaMark(Expression.Expr exp, Definition.Type a);
    public static Expression.Expr removeRuntimeAnnotation(Expression.Expr exp);
}
