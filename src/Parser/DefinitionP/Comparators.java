package Parser.DefinitionP;

import java.util.Comparator;

public class Comparators {
    public static class TComparator implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return o1.compare(o1, o2);
        }
    }

    public static class MethodComparator implements Comparator<Method> {
        @Override
        public int compare(Method o1, Method o2) {
            return o1.compare(o1, o2);
        }
    }

    public static class SignatureComparator implements Comparator<Signature> {
        @Override
        public int compare(Signature o1, Signature o2) {
            return o1.compare(o1, o2);
        }
    }

    public static class InitiatedFieldComparator implements Comparator<InitiatedField> {
        @Override
        public int compare(InitiatedField o1, InitiatedField o2) {
            return o1.compare(o1, o2);
        }
    }

    public static class FieldComparator implements Comparator<Field> {
        @Override
        public int compare(Field o1, Field o2) {
            return o1.compare(o1, o2);
        }
    }
}
