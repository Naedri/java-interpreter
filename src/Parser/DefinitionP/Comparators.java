package Parser.DefinitionP;

import java.util.Comparator;

public class Comparators {
    private static int wrappedComparator(Comparator o1, Comparator o2) {
        int res;
        if (o1 == null && o2 == null) res = 0;
        else if (o1 == null) res = -1;
        else if (o2 == null) res = 1;
        else res = o1.compare(o1, o2);
        return res;
    }

    public static class TComparator implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return wrappedComparator(o1, o2);
        }
    }

    public static class MethodComparator implements Comparator<Method> {
        @Override
        public int compare(Method o1, Method o2) {
            return wrappedComparator(o1, o2);
        }
    }

    public static class SignatureComparator implements Comparator<Signature> {
        @Override
        public int compare(Signature o1, Signature o2) {
            return wrappedComparator(o1, o2);
        }
    }

    public static class InitiatedFieldComparator implements Comparator<InitiatedField> {
        @Override
        public int compare(InitiatedField o1, InitiatedField o2) {
            return wrappedComparator(o1, o2);

        }
    }

    public static class FieldComparator implements Comparator<Field> {
        @Override
        public int compare(Field o1, Field o2) {
            return wrappedComparator(o1, o2);
        }
    }
}
